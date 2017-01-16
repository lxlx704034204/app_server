/**
 * 
 */
package com.ch.app.controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ch.app.model.MatchPlayer;
import com.ch.app.model.Team;
import com.ch.app.model.TeamMatch;
import com.ch.app.model.TeamMate;
import com.ch.app.model.app.AppUser;
import com.ch.base.controller.BaseController;
import com.ch.base.model.SessionInfo;
import com.ch.base.model.easyui.Json;
import com.ch.base.util.ConfigUtil;
import com.ch.base.util.HqlFilter;
import com.ch.base.util.IpUtil;
import com.ch.base.util.JsonUtil;
import com.ch.base.util.MD5Util;
import com.ch.sys.model.Role;
import com.ch.sys.model.User;
import com.ch.sys.service.UserServiceI;

/**
 * @author lijunjie
 *
 */
@Controller
@RequestMapping("/app/base")
public class AppBaseController extends BaseController<User> {

	@Resource
	public void setService(UserServiceI service) {
		this.service = service;
	}

	@Resource
	private JdbcTemplate jdbcTemplate;
	
	private SimpleDateFormat sdf = new SimpleDateFormat();

	/**
	 * 注册一个用户
	 */
	@RequestMapping(value = "/regist_doNotNeedSessionAndSecurity", method = RequestMethod.POST)
	synchronized public void regist(User data, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, PrintWriter pw) {
		Json json = new Json();
		if (data != null && !"".equals(data.getLoginname())) {
			HqlFilter hqlFilter = new HqlFilter();
			hqlFilter.addFilter("QUERY_t#loginname_S_EQ", data.getLoginname());
			User user = service.getByFilter(hqlFilter);
			if (user != null) {
				json.setMsg("注册用户失败，用户名已存在！");
				json.setSuccess(false);
			} else {

				String sql = "from Role where name='app普通注册用户'";
				List<Role> roleList = service.findByHql(sql);
				// Role role = roleList.get(0);
				data.setRoles(new HashSet(roleList));

				String pwd = data.getPwd();
				data.setPwd(MD5Util.md5(pwd));

				service.save(data);
				json.setMsg("注册用户成功！");
				json.setSuccess(true);
			}
		}

		// response.setContentType("application/json;charset=utf-8");
		JsonUtil.writeJson(json, pw);

	}

	@RequestMapping(value = "/login_doNotNeedSessionAndSecurity", method = RequestMethod.POST)
	synchronized public void login(User data, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, PrintWriter pw) {
		Json json = new Json();
		if (data != null && !"".equals(data.getLoginname())
				&& !"".equals(data.getPwd())) {
			HqlFilter hqlFilter = new HqlFilter();
			hqlFilter.addFilter("QUERY_t#loginname_S_EQ", data.getLoginname());
			User user = service.getByFilter(hqlFilter);
			
			
			if (user != null) {
				if (MD5Util.md5(data.getPwd()).equals(user.getPwd())) {

					SessionInfo sessionInfo = new SessionInfo();
					Hibernate.initialize(user.getRoles());

					Map<String, Boolean> resourceMap = new HashMap();
					for (Role role : user.getRoles()) {
						Hibernate.initialize(role.getResources());

						for (com.ch.sys.model.Resource resource : role
								.getResources()) {
							resourceMap.put(resource.getUrl(), true);
						}
					}

					if (resourceMap.get("/app/base/login")) {
						
						//当前hibernate session
						Session hbnSession = service.getCurrentSession();
						//把user对象从session缓存中清除,这样close后就不会更新到数据库
						hbnSession.evict(user);
						
						AppUser appUser = new AppUser(user);
						
						Team currentTeam = getCurrentTeamByUserId(user.getId());
						if(currentTeam!=null){
							appUser.setCurrentTeam(currentTeam);
						}else{
							currentTeam = setCurrentTeamForUserId(user.getId());
						}
						
						
						json.setMsg("登录成功！");
						json.setSuccess(true);
						json.setObj(appUser);
						
						user.setPwd("");
						user.setResourceMap(resourceMap);
						user.setIp(IpUtil.getIpAddr(request));
						
						
						sessionInfo.setUser(user);
						session.setAttribute(ConfigUtil.getSessionInfoName(),
								sessionInfo);
						

					} else {
						json.setMsg("当前帐号没有登录权限！");
						json.setSuccess(false);
					}

				} else {
					json.setMsg("密码不正确！");
					json.setSuccess(false);
				}

			} else {

				json.setMsg("用户名不存在！");
				json.setSuccess(false);
			}
		}
		// response.setContentType("application/json;charset=utf-8");
		JsonUtil.writeJson(json, pw);

	}

	@RequestMapping("/teamList")
	public void teamList(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, PrintWriter pw) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession()
				.getAttribute(ConfigUtil.getSessionInfoName());

		User currentUser = sessionInfo.getUser();
		
		String getType = request.getParameter("getType");
		
		
		if("my".equals(getType)){
			
			setCurrentTeamForUserId(currentUser.getId());
			
			Map<String, Object> params = new HashMap();
			params.put("userId", currentUser.getId());
			List returnList = service.findByHql("select new Map(m.team.name as name,m.team.id as id,"
					+ " m.team.city as city,m.team.logoPic as logoPic,m.team.description as description,"
					+ " m.name as myName,m.isTeamManager as isTeamManager,m.isCurrentTeam as isCurrentTeam)"
					+ " from TeamMate m where m.user.id=:userId order by m.createdatetime desc",params);
			JsonUtil.writeJson(returnList, pw); 
		}else if("other".equals(getType)){
			
			Map<String, Object> params = new HashMap();
			params.put("userId", currentUser.getId());
			List returnList = service.findByHql("from Team t where t.id not in "
					+ "(select m.team.id from TeamMate m where m.user.id=:userId)",params);
			JsonUtil.writeJson(returnList, pw); 
		}
		
		
	}
	
	@RequestMapping("/matchList")
	public void matchList(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, PrintWriter pw) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession()
				.getAttribute(ConfigUtil.getSessionInfoName());

		User currentUser = sessionInfo.getUser();
		
		String getType = request.getParameter("getType");
		
		
		if("myTeam".equals(getType)){
//			
//			Map<String, Object> params = new HashMap();
//			params.put("userId", currentUser.getId());
//			List returnList = service.findByHql("select new Map(m.id as matchId,m.homeTeam.name as homeTeamName,m.hostTeam.name as hostTeamName,"
//					+ " m.place as place,m.matchTime as matchTime,m.homeTeam.logoPic as homeTeamLogo,m.hostTeam.logoPic as hostTeamLogo)"
//					+ " from TeamMatch m join m.homeTeam.teamMates teamMate"
//					+ " where teamMate.user.id=:userId order by m.createdatetime desc",params);
//
//			JsonUtil.writeJson(returnList, pw); 
			
			Map<String, Object> params = new HashMap();
			params.put("userId", currentUser.getId());
			List returnList = service.findByHql("select new Map(v.id as matchId,v.homeTeam.name as homeTeamName,v.hostTeam.name as hostTeamName,"
					+ " v.place as place,v.matchTime as matchTime,v.homeTeam.logoPic as homeTeamLogo,v.hostTeam.logoPic as hostTeamLogo,"
					+ "v.joinType as joinType,v.playerId as playerId)"
					+ " from ViewMatchPlayerMate v where v.mateUserId=:userId order by v.createdatetime desc",params);

			JsonUtil.writeJson(returnList, pw); 
			
			
			
		}else if("my".equals(getType)){
			
			Map<String, Object> params = new HashMap();
			params.put("userId", currentUser.getId());
			List returnList = service.findByHql("select new Map(m.homeTeam.name as homeTeamName,m.hostTeam.name as hostTeamName,"
					+ " m.place as place,m.matchTime as matchTime,m.homeTeam.logoPic as homeTeamLogo,m.hostTeam.logoPic as hostTeamLogo)"
					+ " from TeamMatch m join m.matchPlayers player where player.teamMate.user.id=:userId order by m.createdatetime desc",params);

			JsonUtil.writeJson(returnList, pw); 
		}else if("other".equals(getType)){
			
			Map<String, Object> params = new HashMap();
			params.put("userId", currentUser.getId());
			List returnList = service.findByHql("from Team t where t.id not in "
					+ "(select m.team.id from TeamMate m where m.user.id=:userId)",params);
			JsonUtil.writeJson(returnList, pw); 
		}
		
		
	}
	
	
	

	@RequestMapping("/searchTeamForJoin")
	public void searchTeamForJoin(Team data, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, PrintWriter pw) {
		
		SessionInfo sessionInfo = (SessionInfo) request.getSession()
				.getAttribute(ConfigUtil.getSessionInfoName());

		User currentUser = sessionInfo.getUser();

		Map<String, Object> params = new HashMap();
		params.put("userId", currentUser.getId());
		
		params.put("city", (data.getCity() != null)?"%"+data.getCity()+"%":"%%");
		params.put("name", (data.getName() != null)?"%"+data.getName()+"%":"%%");

		
		List<Team> returnList = service
				.findByHql(
						"from Team t where t.city like :city and t.name like :name"
						+ " and t.id not in(select m.team.id from TeamMate m where m.user.id=:userId)"
						+ " order by t.updatedatetime desc",
						params);
		for(Team team:returnList){
			team.setIsJoin(false);
		}

		JsonUtil.writeJson(returnList, pw);
	}
	
	
	@RequestMapping("/joinTeam")
	public void joinTeam( HttpServletRequest request,
			HttpServletResponse response, HttpSession session, PrintWriter pw) {
		
		SessionInfo sessionInfo = (SessionInfo) request.getSession()
				.getAttribute(ConfigUtil.getSessionInfoName());
		
		//Map paramMap = request.getParameterMap();
		String teamId = request.getParameter("teamId");
		String age = request.getParameter("age");
		String height = request.getParameter("height");
		String weight = request.getParameter("weight");
		String position = request.getParameter("position");
		String name = request.getParameter("name");
		
		User currentUser = sessionInfo.getUser();

		Json json = new Json();
		

		
		try {
			Team team = getTeamById(teamId);
			TeamMate teamMate = new TeamMate();
			teamMate.setTeam(team);
			teamMate.setName(name);
			teamMate.setAge(Integer.valueOf(age));
			teamMate.setWeight(Integer.valueOf(weight));
			teamMate.setHeight(Integer.valueOf(height));
			teamMate.setPosition(position);
			teamMate.setUser(currentUser);
		
			service.saveObj(teamMate);
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg(e.toString());
			json.setSuccess(false);
		}
		json.setMsg("加入成功!");
		json.setSuccess(true);
			

		JsonUtil.writeJson(json, pw);
	}
	
	
	
	@RequestMapping("/joinMatch")
	public void joinMatch( HttpServletRequest request,
			HttpServletResponse response, HttpSession session, PrintWriter pw) {
		
		SessionInfo sessionInfo = (SessionInfo) request.getSession()
				.getAttribute(ConfigUtil.getSessionInfoName());
		

		String matchId = request.getParameter("matchId");
		String joinType = request.getParameter("joinType");
		String playerId = request.getParameter("playerId");
		
		
		User currentUser = sessionInfo.getUser();

		Json json = new Json();
		
		
		
		try {
			
			MatchPlayer matchPlayer = getMatchPlayerById(playerId);
			
			if(matchPlayer==null){
				matchPlayer = new MatchPlayer();
			}
			
			TeamMatch match = getMatchById(matchId);
			
			TeamMate teamMate = getTeamMate(match.getHomeTeam().getId(),currentUser.getId());
			
			
			matchPlayer.setTeamMate(teamMate);
			matchPlayer.setTeamMatch(match);
			matchPlayer.setJoinType(joinType);

			service.saveObj(matchPlayer);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg(e.toString());
			json.setSuccess(false);
		}
		json.setMsg("加入比赛成功!");
		json.setSuccess(true);
			

		JsonUtil.writeJson(json, pw);
	}
	
	
	
	
	private MatchPlayer getMatchPlayerById(String playerId) {
		Map<String, Object> params = new HashMap();
		params.put("id", playerId);

		List<MatchPlayer> returnList = service
				.findByHql("from MatchPlayer t where t.id=:id",params);
		if(returnList.size()>0){
			return returnList.get(0);
		}
		return null;
	}

	private TeamMate getTeamMate(String teamId, String userId) {
		Map<String, Object> params = new HashMap();
		params.put("teamId", teamId);
		params.put("userId", userId);

		List<TeamMate> returnList = service
				.findByHql("from TeamMate t where t.team.id=:teamId and t.user.id=:userId",params);
		if(returnList.size()>0){
			return returnList.get(0);
		}
		return null;
	}

	private TeamMatch getMatchById(String matchId) {
		Map<String, Object> params = new HashMap();
		params.put("id", matchId);

		List<TeamMatch> returnList = service
				.findByHql("from TeamMatch t where t.id=:id",params);
		if(returnList.size()>0){
			return returnList.get(0);
		}
		return null;
	}

	@RequestMapping("/createTeam")
	public void createTeam(Team data, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, PrintWriter pw) {
		
		SessionInfo sessionInfo = (SessionInfo) request.getSession()
				.getAttribute(ConfigUtil.getSessionInfoName());
		
		Json json = new Json();
			
		try {
			
			data.setIsJoin(true);
			data.setDescription("新球队成立于"+(new Date()).toLocaleString());
			service.saveObj(data);
			
			User currentUser = sessionInfo.getUser();
			
			TeamMate teamMate = new TeamMate();
			teamMate.setTeam(data);
			teamMate.setName(currentUser.getName());
			teamMate.setUser(currentUser);
			teamMate.setIsTeamLeader(true);
			teamMate.setIsTeamManager(true);
			service.saveObj(teamMate);
			
			json.setMsg("创建成功!");
			json.setSuccess(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg(e.toString());
			json.setSuccess(false);
		}
			
		

		JsonUtil.writeJson(json, pw);
	}
	
	
	
	@RequestMapping("/createMatch")
	public void createMatch(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, PrintWriter pw) {
		
		SessionInfo sessionInfo = (SessionInfo) request.getSession()
				.getAttribute(ConfigUtil.getSessionInfoName());
		
		sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
		Json json = new Json();
		
		try {
			String matchTimeStr = request.getParameter("matchTimeTemp");
			String endJoinTimeStr = request.getParameter("endJoinTimeTemp");
			String place = request.getParameter("place");
			String memberCountStr = request.getParameter("memberCount");
			String clothColor = request.getParameter("clothColor");
			String feeStr = request.getParameter("fee");
			String homeTeamId = request.getParameter("homeTeamId");
			String hostTeamId = request.getParameter("hostTeamId");
	
			Date matchTime = sdf.parse(matchTimeStr);
			Date endJoinTime = sdf.parse(endJoinTimeStr);
			
			TeamMatch match = new TeamMatch();
			match.setMatchTime(matchTime);
			match.setEndJoinTime(endJoinTime);
			match.setPlace(place);
			match.setMemberCount(Integer.valueOf(memberCountStr));
			match.setClothColor(clothColor);
			match.setFee(Double.valueOf(feeStr));
			

			Team homeTeam = getTeamById(homeTeamId);
			Team hostTeam = getTeamById(hostTeamId);

			
			match.setHomeTeam(homeTeam);
			match.setHostTeam(hostTeam);
			service.saveObj(match);
			
			
			json.setMsg("发起比赛成功!");
			json.setSuccess(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg(e.toString());
			json.setSuccess(false);
		}
			
		

		JsonUtil.writeJson(json, pw);
	}

	private Team getTeamById(String teamId) {
		
		Map<String, Object> params = new HashMap();
		params.put("id", teamId);

		List<Team> returnList = service
				.findByHql("from Team t where t.id=:id",params);
		if(returnList.size()>0){
			return returnList.get(0);
		}
		return null;
	}
	
	private Team getCurrentTeamByUserId(String userId) {
		
		Map<String, Object> params = new HashMap();
		params.put("userId", userId);

		List<Team> returnList = service
				.findByHql("from Team t where t.id in"
						+ "(select m.team.id from TeamMate m where m.user.id=:userId and m.isCurrentTeam=true)",params);
		if(returnList.size()>0){
			return returnList.get(0);
		}else{
			returnList = service
					.findByHql("from Team t where t.id in"
							+ "(select m.team.id from TeamMate m where m.user.id=:userId "
							+ "and (m.isTeamManager=true or m.isTeamLeader=true))",params);
			if(returnList.size()>0){
				return returnList.get(0);
			}
		}
		return null;
	}
	
	private Team setCurrentTeamForUserId(String userId) {
		
		Team team = getCurrentTeamByUserId(userId);
		if(team==null){
			
			Map<String, Object> params = new HashMap();
			params.put("userId", userId);
			
			List<TeamMate> returnList = service
					.findByHql("from TeamMate m where m.user.id=:userId",params);
			if(returnList.size()>0){
				TeamMate teamMate = returnList.get(0);
				teamMate.setIsCurrentTeam(true);
				service.saveObj(teamMate);
				return teamMate.getTeam();
			}
		}
		return team;
	}
	
	
	@RequestMapping("/testSession")
	public void testSession( HttpServletRequest request,
			HttpServletResponse response, HttpSession session, PrintWriter pw) {
		
		SessionInfo sessionInfo = (SessionInfo) request.getSession()
				.getAttribute(ConfigUtil.getSessionInfoName());
		
		Json json = new Json();
		json.setMsg("session成功!");
		json.setSuccess(true);

		JsonUtil.writeJson(json, pw);
	}
	

}
