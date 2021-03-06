package cn.appsys.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.appsys.pojo.BackendUser;
import cn.appsys.pojo.DevUser;

/**
 * 自定义拦截器
 * @author LZW
 *
 */
public class SysInterceptor extends HandlerInterceptorAdapter {
   private Logger logger=Logger.getLogger(SysInterceptor.class);
   public boolean preHandle(HttpServletRequest request,
		                    HttpServletResponse response,
		                    Object handler)throws Exception {
	   logger.debug("SysInterceptor preHandle!");
	   HttpSession session=request.getSession();
	   //前台
	   DevUser user=(DevUser)session.getAttribute("devUserSession");
	   //后台
	   BackendUser backendUser=(BackendUser)session.getAttribute("userSession");
	   if (null!=user) {
		   return true;
	   }
	   else if (null!=backendUser) {
		   return true;
	   }
	   response.sendRedirect(request.getContextPath()+"/403.jsp");
	   return false;
	
}
}
