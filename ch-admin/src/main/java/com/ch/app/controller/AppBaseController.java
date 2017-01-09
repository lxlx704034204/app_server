/**
 * 
 */
package com.ch.app.controller;

import java.io.PrintWriter;
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

import com.ch.app.model.Team;
import com.ch.app.model.TeamMate;
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
						
						json.setMsg("登录成功！");
						json.setSuccess(true);
						json.setObj(user);
						
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
	public void teamList(User data, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, PrintWriter pw) {
		SessionInfo sessionInfo = (SessionInfo) request.getSession()
				.getAttribute(ConfigUtil.getSessionInfoName());

		User currentUser = sessionInfo.getUser();
		
		Map<String, Object> params = new HashMap();
		params.put("userId", currentUser.getId());
		List<Team> returnList = service.findByHql("from Team t where "
				+ "t.id in(select m.team.id from TeamMate m where m.user.id=:userId)",params);

		JsonUtil.writeJson(returnList, pw);
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
		
		Map<String, Object> params = new HashMap();
		params.put("id", teamId);

		
		List<Team> returnList = service
				.findByHql("from Team t where t.id=:id",params);
		if(returnList.size()>0){
			try {
				Team team = returnList.get(0);
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
			
		}else{
			json.setMsg("获取队伍失败！");
			json.setSuccess(false);
		}
		
		

		JsonUtil.writeJson(json, pw);
	}

}
