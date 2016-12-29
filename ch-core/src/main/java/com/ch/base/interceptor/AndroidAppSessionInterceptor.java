package com.ch.base.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AndroidAppSessionInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = Logger
			.getLogger(SessionInterceptor.class);
	
	@Value("#{T(java.lang.Integer).parseInt('${android.cookies.maxAge}')}")
	private int cookiesMaxAge;
	
	@Value("${android.cookies.Path}")
	private String cookiesPath;
	
	@Value("${android.allow.origin}")
	private String allowOrigin;


	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		//Log.debug("getCookies00000"+ request.getCookies()[0].getName() +"====="+request.getCookies()[0].getValue()+";;;;;getCookies11111"+ request.getCookies()[1].getName() +"====="+request.getCookies()[1].getValue());
		
		//设置浏览器cookies过期时间
		String sessionid = request.getSession().getId();
		Cookie cookie = new Cookie("JSESSIONID",sessionid);  
		cookie.setPath(cookiesPath);  // "/" web服务路径,不写说明对整个域名生效 
		cookie.setMaxAge(cookiesMaxAge);  //"Integer.MAX_VALUE"最大值 
		response.addCookie(cookie);
		response.setContentType("application/json;charset=utf-8");
		
		//设置浏览器无视跨域
		//浏览器是否提交cookies
		response.addHeader("Access-Control-Allow-Credentials", "true");
		//如果需要浏览器请求付带cookies,需要设置具体的网站域名,不能用*号代替,
		response.addHeader("Access-Control-Allow-Origin", allowOrigin);//测试"http://localhost:8080"
		
		
		
		return true;

	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		String errMsg = "===========AndroidApp拦截器postHandle()方法================";
		logger.info(errMsg);
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

		
		String errMsg = "===========AndroidApp拦截器afterCompletion()方法================";
		logger.info(errMsg);
		
	}


	
}
