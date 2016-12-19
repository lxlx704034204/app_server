/**
 * 
 */
package web.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerMapping;

import com.ch.base.model.SessionInfo;
import com.ch.base.util.ConfigUtil;
import com.ch.base.util.SecurityUtil;

import freemarker.ext.beans.BeansWrapper;

/**
 * @author mozhiqiang
 *
 */
@Controller
@RequestMapping(value="/test")
public class PageController {
	
	private static SecurityUtil securityUtil;
	
	@RequestMapping(value="")
	public String page(HttpServletResponse response,HttpServletRequest request,HttpSession session,Model model)
	{
		
		model.addAttribute("str", "hello world!");
		return "/test_page";
	}
	

	

}
