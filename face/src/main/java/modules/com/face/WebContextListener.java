package com.face;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 自定义的监听器，系统启动时定义的全局变量进行加载<br> 
 */
public final class WebContextListener implements ServletContextListener{
    /** 记录日志 */
    private Logger logger = LoggerFactory.getLogger(WebContextListener.class);
    
    /** The name of the configuration hashmap stored in application scope. */
    public static final String CONFIG = "app";
    
    /**Spring管理的全�?应用上下�?*/
    private static ApplicationContext applicationContext = null;

    public void contextInitialized(ServletContextEvent sce) {
    	
    	
        logger.debug("initializing context...");
        ServletContext context = sce.getServletContext();
        @SuppressWarnings("unchecked")
		Map<String, Object> config = (HashMap<String, Object>) context.getAttribute(CONFIG);
        if (config == null) {
            config = new HashMap<String, Object>();
        } 
        context.setAttribute(CONFIG, config); 
        if(applicationContext == null){
            setApplicationContext(context);
        }
        
    }

    /**
     * 服务器关闭时的销毁方�?
     */
    public void contextDestroyed(ServletContextEvent sce) {        
    }
    
    /**
     * 取得Spring管理的bean的一个实�?
     * @param clazz bean类型
     */
    public static <T> T getBean(Class<T> clazz){       
        return (T)applicationContext.getBean(clazz);
    }
    
    /**
     * 取得Spring管理的bean的一个实�?
     * @param beanName bean名称
     */
    public static Object getBean(String beanName){       
        return applicationContext.getBean(beanName);
    }
    
    /**
     * 将Servlet上下文使用工具类赋给spring全局上下文�??
     * @param context Servlet上下�?
     */
    private static void setApplicationContext(ServletContext context){
        applicationContext = WebApplicationContextUtils.getWebApplicationContext(context);
    }

}
