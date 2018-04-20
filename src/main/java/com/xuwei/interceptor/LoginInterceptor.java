package com.xuwei.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.xuwei.bean.CoreUser;


public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		String url=request.getRequestURI();
		if(url.equals("/myCrm/login/login.jhtml") || url.equals("/myCrm/login/login.jhtml")){
			return true;
		}
		HttpSession session = request.getSession();
		CoreUser user=(CoreUser) session.getAttribute("user");
		if(user==null){
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
			return false;
		}
		return true;
	}

   

}
