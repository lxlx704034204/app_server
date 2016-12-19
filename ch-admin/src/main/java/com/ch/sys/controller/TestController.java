/**
 * 
 */
package com.ch.sys.controller;

import java.io.PrintWriter;
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
import com.ch.base.model.easyui.Json;
import com.ch.base.service.BaseServiceI;
import com.ch.base.util.HqlFilter;
import com.ch.base.util.JsonUtil;
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
	private BaseServiceI<User> baseService;
	
//	@Resource(name="baseServiceImpl")
//	public void setService(BaseServiceI service) {
//		this.service = service;
//	}
	
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
	public String list(HttpServletRequest request, HttpServletResponse response,Model model){
		HqlFilter hqlFilter = new HqlFilter(request);
		//response.addHeader("Access-Control-Allow-Origin", "http://localhost:8080/");
		List<User> returnList = baseService.find("from User");
		
		
		
	
		

		//transaction.begin();
		for(User user :returnList){
			if("0".equals(user.getSex())){
				user.setSex("1");
			}else{
				user.setSex("0");
			}
			baseService.update(user);
			//dao.update(user);
		}
		
		
		
		
		
		model.addAttribute("userList" , returnList);
		return "/test/list";
	}
	
	@RequestMapping("/session")
	public void session(User data,HttpServletRequest request,HttpServletResponse response,HttpSession session,PrintWriter pw) {
		
		Json json = new Json();
		
		json.setSuccess(true);
		
		
		JsonUtil.writeJson(json,pw);
	}

}
