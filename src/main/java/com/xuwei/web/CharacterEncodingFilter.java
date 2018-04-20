package com.xuwei.web;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

/* web.xml 配置方式	

	<filter>
		<filter-name>CharacterEncodingFilter</filter-name>
		<filter-class>edu.web.CharacterEncodingFilter</filter-class>
		<init-param>
			<param-name>charset</param-name>
			<param-value>UTF-8</param-value>
		</init-param>
		<init-param>
			<param-name>isUseWrapper</param-name>
			<param-value>false</param-value>
		</init-param>			
	</filter>
	<filter-mapping>
		<filter-name>CharacterEncodingFilter</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>
  
  
 自从Tomcat5.x开始，GET和POST方法提交的信息，Tomcat采用了不同的方式来处理编码.
 对于POST请求，Tomcat会仍然使用request.setCharacterEncoding方法所设置的编码来处理，
 如果未设置，则使用默认的iso-8859-1编码。
 而GET请求则不同，Tomcat对于GET请求并不会考虑使用request.setCharacterEncoding方法设置的编码，
 而会永远使用iso-8859-1编码。
 
解决办法如下：
1.配置tomcat的配置文件server.xml里这句：
             <Connector URIEncoding="GB2312" 
                 port="8080"   maxHttpHeaderSize="8192"
               maxThreads="150" minSpareThreads="25" maxSpareThreads="75"
               enableLookups="false" redirectPort="8443" acceptCount="100"
               connectionTimeout="20000" disableUploadTimeout="true" />

                 加上这句：URIEncoding="GB2312"
2.使用String name=new String(request.getParameter("name").getBytes("ISO-8859-1"),"GB2312");转化编码
推荐使用第二种方式。 

Tomcat8默认 URIEncoding="UTF-8".


 */

/**
* @ClassName: CharacterEncodingFilter
* @Description: 此过滤器用来解决全站中文乱码问题
* @author: exjor
* @date: 2016-8-31
*
*/ 
public class CharacterEncodingFilter implements Filter {

    private FilterConfig filterConfig = null;
    //设置默认的字符编码
    private String defaultCharset = "UTF-8";
    
    //Tomcat7含7之前需要配置
    private String defaultIsUseWrapper = "false";

    public void doFilter(ServletRequest req, ServletResponse resp,
            FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String charset = filterConfig.getInitParameter("charset");
        if(charset==null){
            charset = defaultCharset;
        }
        
        
		// 设置请求对象的编码方式
		request.setCharacterEncoding(charset);
		// 设置响应对象的编码方式
		response.setCharacterEncoding(charset);
		// 设置响应的内容类型为text/html
		response.setContentType("text/html; charset="+charset);
		
		
        String isUseWrapper = filterConfig.getInitParameter("isUseWrapper");
        if(isUseWrapper==null){
        	isUseWrapper = defaultIsUseWrapper;
        }
        ServletRequest resultReq = req;
        if(isUseWrapper!=null && isUseWrapper.equalsIgnoreCase("true")) {
        	resultReq = new MyCharacterEncodingRequest(request);
        }
        
        chain.doFilter(resultReq, response);
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        //得到过滤器的初始化配置信息
        this.filterConfig = filterConfig;
    }
    
    public void destroy() {

    }
}

/*
1.实现与被增强对象相同的接口 
2、定义一个变量记住被增强对象
3、定义一个构造器，接收被增强对象
4、覆盖需要增强的方法
5、对于不想增强的方法，直接调用被增强对象（目标对象）的方法
 */
 
class MyCharacterEncodingRequest extends HttpServletRequestWrapper{
    
    private HttpServletRequest request;
    public MyCharacterEncodingRequest(HttpServletRequest request) {
        super(request);
        this.request = request;
    }
    /* 重写getParameter方法
     * @see javax.servlet.ServletRequestWrapper#getParameter(java.lang.String)
     */
    @Override
    public String getParameter(String name) {
        
        try{
            //获取参数的值
            String value= this.request.getParameter(name);
            if(value==null){
                return null;
            }
            //如果不是以get方式提交数据的，就直接返回获取到的值
            if(!this.request.getMethod().equalsIgnoreCase("get")) {
                return value;
            }else{
                //如果是以get方式提交数据的，就对获取到的值进行转码处理
                value = new String(value.getBytes("ISO8859-1"),this.request.getCharacterEncoding());
                return value;
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}