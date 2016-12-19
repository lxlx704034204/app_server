package com.ch.base.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ch.base.model.SessionInfo;
import com.ch.base.util.ConfigUtil;

/**
 * session拦截器
 * 
 * 
 */
public class SessionInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = Logger
			.getLogger(SessionInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		SessionInfo sessionInfo = (SessionInfo) request.getSession()
				.getAttribute(ConfigUtil.getSessionInfoName());
		logger.info("进入session拦截器->访问路径为[" + request.getServletPath() + "]");
		if (sessionInfo == null) {
			String errMsg = "您还没有登录或登录已超时，请重新登录，然后再刷新本功能！";
			logger.info(errMsg);
			request.setAttribute("msg", errMsg);
			request.setAttribute("url", "/");
			request.getRequestDispatcher("/error/noSession").forward(request,
					response);
			return false;
		}
		return true;

	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		
		String errMsg = "===========Session拦截器postHandle()方法================";
		logger.info(errMsg);
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		String errMsg = "===========Session拦截器afterCompletion()方法================";
		logger.info(errMsg);
		
	}

}
