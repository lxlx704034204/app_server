/**
 * 
 */
package com.ch.sys.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ch.base.controller.BaseController;
import com.ch.base.dao.BaseDaoI;
import com.ch.base.util.HqlFilter;
import com.ch.base.util.SecurityUtil;
import com.ch.sys.model.User;
import com.ch.sys.service.UserServiceI;

/**
 * @author mozhiqiang
 *
 */
@Controller
@RequestMapping(value="/test")
public class TestController  extends BaseController<User>{
	
	@Autowired
	private BaseDaoI dao;
	
	@Resource
	public void setService(UserServiceI service) {
		this.service = service;
	}
	
	private static SecurityUtil securityUtil;
	
	
	@RequestMapping(value="")
	public String page(HttpServletResponse response,HttpServletRequest request,HttpSession session,Model model)
	{
		
		model.addAttribute("str", "hello world!");
		return "/test_page";
	}
	

	@RequestMapping(value="test")
	public String page2(Model model,HttpServletResponse response,HttpServletRequest request,HttpSession session)
	{
		
		model.addAttribute("str", "hello world!2222222");
		return "/test_page";
	}
	
	

	@RequestMapping("/list")
	public String grid(HttpServletRequest request, HttpServletResponse response,Model model){
		HqlFilter hqlFilter = new HqlFilter(request);
		response.addHeader("Access-Control-Allow-Origin", "*");
		List returnList =  dao.findBySql("select * from User", new HashMap());
		model.addAttribute("userList" , returnList);
		model.addAttribute("response", response);
		return "/test/list";
	}

}
