package com.face;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;


/**
 * Servlet Filter implementation class LoginFilter
 */
public class FaceFilter implements Filter {
    /** 记录日志的变�? */
    private static Logger logger = LoggerFactory.getLogger(FaceFilter.class);
  
    /**
     * Default constructor. 
     */
    public FaceFilter() {
        //Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		//Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse rep = (HttpServletResponse) response;
        
        //请求全路�?
        String requestURL = req.getRequestURL().toString();
        logger.info("请求路径：requestUrl=" + requestURL); 
        
        //请求相对路径
        String requestURI = req.getRequestURI();
        logger.info("请求路径：requestURI=" + requestURI); 
		
        chain.doFilter(req, rep);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
 	}
	
	/**
     * 响应信息
     */
    private void writer(HttpServletResponse rep, int statusCode,String msg) {
        rep.setCharacterEncoding("UTF-8");
        rep.setContentType("application/json");
        rep.setDateHeader("Expires", 0);
        PrintWriter out = null;
        try {
        	
			
			JSONObject json = JSONObject.fromObject("ok");
			
            out = rep.getWriter();
            out.println(json);
            out.flush();
            out.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}
