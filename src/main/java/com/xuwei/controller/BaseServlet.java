package com.xuwei.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class BaseServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BaseServlet(){
		super();
	}
	
	public String areaName="/area02";
	
	public String getAreaName() {
		return areaName;
	}
	
	public String areaPath;

	public String getAreaPath() {
		return areaPath;
	}
	
	public String currAreaPath(HttpServletRequest request){
		
		if(areaPath==null || areaPath.isEmpty()){
			areaPath=request.getServletContext().getContextPath()+getAreaName();
			request.setAttribute("areaName", getAreaName());
			request.setAttribute("areaPath", getAreaPath());
			System.out.println("areaPath="+areaPath);
		}
		
		return areaPath;
	}
	
	//重写service
	protected void service(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		currAreaPath(request);
		super.service(request, response);
	}
	
	public String Page_Prefix="/WEB-INF/view02/"; //WEB前加上‘/’，表示当前应用上下文，即映射地址会在WEB前加上“/area02/”
	public String Page_Suffix=".jsp";
	
	public String Url_Format=Page_Prefix+"%s"+Page_Suffix;
	
	public String getPagePath(String pFileName){
		return String.format(Url_Format, pFileName);
	}
	
	public String getPagePath(String pBeanName,String pOperName){
		return String.format(Url_Format, pBeanName+"_"+pOperName);
	}
	
	protected boolean checkLogin(HttpServletRequest request,HttpServletResponse response) throws IOException{
		boolean vIsOk=false;
		if(request.getSession().getAttribute("loginuser")==null){
			response.sendRedirect(request.getContextPath()+"/login");
			vIsOk=false;
		}else{
			vIsOk=true;
		}
		return vIsOk;
	}
	
	protected boolean checkAdmin(HttpServletRequest request,HttpServletResponse response) throws IOException{
		boolean vIsOk=false;
		if(request.getSession().getAttribute("isadmin")==null){
			vIsOk=false;
		}else{
			vIsOk=true;
		}
		return vIsOk;
	}
	
	protected void forward(String url,HttpServletRequest request,HttpServletResponse response) 
			throws ServletException, IOException{
		
		request.getRequestDispatcher(url).forward(request, response);
		
	}
	
	protected void redirect(String url,HttpServletRequest request,HttpServletResponse response) 
			throws ServletException, IOException{
		
		//重定向到页面
		response.sendRedirect(request.getContextPath()+url);
		
	}
	
	protected void redirectAreaPath(HttpServletResponse response,String url) 
			throws ServletException, IOException{
		
		//重定向到页面
		response.sendRedirect(getAreaPath()+url);
		
	}
	
	
	
}
