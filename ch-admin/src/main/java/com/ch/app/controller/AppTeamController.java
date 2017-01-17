package com.ch.app.controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ch.app.model.FootballSkill;
import com.ch.base.controller.BaseController;
import com.ch.base.util.JsonUtil;
import com.ch.sys.model.User;
import com.ch.sys.service.UserServiceI;

@Controller
@RequestMapping(value="/app/team")
public class AppTeamController extends BaseController<User> {

	@Resource
	public void setService(UserServiceI service) {
		this.service = service;
	}

	@Resource
	private JdbcTemplate jdbcTemplate;
	
	private SimpleDateFormat sdf = new SimpleDateFormat();
	
	@RequestMapping(value="/getTeamMateList")
	public void getTeamMateList(Model model,HttpServletResponse response,HttpServletRequest request,HttpSession session,PrintWriter pw){
		
		String teamId = request.getParameter("teamId");
		
		Map<String, Object> params = new HashMap();
		params.put("teamId", teamId);
		
		List<FootballSkill> returnList = service
				.findByHql("from TeamMate m where m.team.id=:teamId",params);
		
		JsonUtil.writeJson(returnList, pw); 
		
	}
	
	
}
