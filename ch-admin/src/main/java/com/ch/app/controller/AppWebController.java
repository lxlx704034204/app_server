package com.ch.app.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ch.app.model.FootballSkill;
import com.ch.app.service.AppWebServiceI;
import com.ch.base.controller.SimpleController;
import com.ch.base.util.JsonUtil;

@Controller
@RequestMapping(value="/app/web")
public class AppWebController extends SimpleController {
	
	@Autowired
	private AppWebServiceI service;
	
	
	@RequestMapping(value="/getFootballSkill")
	public void getFootballSkill(Model model,HttpServletResponse response,HttpServletRequest request,HttpSession session,PrintWriter pw){
		
		List<FootballSkill> returnList = service.getFootballSkill();
		JsonUtil.writeJson(returnList, pw); 
		
	}
	
	/**
	 * 访问外部RSS资源，解决JS跨域问题。
	 * @param model
	 * @param response
	 * @param request
	 * @param session
	 * @param pw
	 */
	@RequestMapping(value="/getFpRss")
	public void getFpRss(Model model,HttpServletResponse response,HttpServletRequest request,HttpSession session,PrintWriter pw){
		
		String xml = service.getRss(service.FP_Football_Rss);
		response.setContentType("text/xml;charset=UTF-8");
		pw.print(xml);
	}
	
	


}
