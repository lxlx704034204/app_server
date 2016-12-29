/**
 * 
 */
package com.ch.sys.controller;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ch.base.controller.BaseController;
import com.ch.base.model.easyui.Json;
import com.ch.base.util.HqlFilter;
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
public class AppBaseController extends BaseController<User>{
	
	@Resource
	public void setService(UserServiceI service) {
		this.service = service;
	}
	
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 注册一个用户
	 */
	@Override
	@RequestMapping(value="/regist_doNotNeedSessionAndSecurity", method=RequestMethod.POST)
	synchronized public void save(User data, HttpServletRequest request,HttpServletResponse response, HttpSession session,PrintWriter pw) {
		Json json = new Json();
		if (data != null && data.getLoginname()!=null && !"".equals(data.getLoginname())) {
			HqlFilter hqlFilter = new HqlFilter();
			hqlFilter.addFilter("QUERY_t#loginname_S_EQ", data.getLoginname());
			User user = service.getByFilter(hqlFilter);
			if (user != null) {
				json.setMsg("注册用户失败，用户名已存在！");
				json.setSuccess(false);
			} else {
				
				String sql = "from Role where name='app普通注册用户'";
				List<Role>roleList = service.findByHql(sql);
				//Role role = roleList.get(0);
				data.setRoles(new HashSet(roleList));
				
				String pwd = data.getPwd();
				data.setPwd(MD5Util.md5(pwd));
				
				service.save(data);
				json.setMsg("注册用户成功！");
				json.setSuccess(true);
			}
		}
		json.setMsg("注册失败！");
		json.setSuccess(false);
		JsonUtil.writeJson(json,pw);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
