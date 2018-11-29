package cn.appsys.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.appsys.pojo.BackendUser;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.login.Loginservice;

@Controller
public class LoginController {
	@Resource
    private Loginservice loginservice;
	
	/**
	 * 进入开发者平台登录页面
	 * @return
	 */
	@RequestMapping(value="/devlogin")
	public String DevLogin() {
		return "devlogin";
	}
	/**
	 * 开发者登录操作
	 * @return
	 */
	@RequestMapping("/dologin")
	public String DevdoLogin(@RequestParam String devCode,
			                 @RequestParam String devPassword,
			                 HttpServletRequest request,
			                 HttpSession session) {
		DevUser devUser=loginservice.DeveloperLogin(devCode, devPassword);
		if (null!=devUser) {
			session.setAttribute("devUserSession", devUser);
			return "redirect:/sys/main.html";
		}else {
			request.setAttribute("error", "用户名或密码不正确");
			return "devlogin";
		}
	}
	/**
	 * 注销开发者账号
	 * @param session
	 * @return
	 */
	@RequestMapping("/devlogout")
	public String DevdoLoginpRemove(HttpSession session) {
		session.removeAttribute("devUserSession");
		return "devlogin";
	}
	/**
	 * 进入前台登录首页
	 * @return
	 */
	@RequestMapping(value="/sys/main.html")
	public String Fmain() {
		return "developer/main";
	}
	/**
	 * 进入到后台管理系统 登录页面
	 * @return
	 */
	@RequestMapping(value="/backendlogin")
	public String BackendLogin() {
		return "backendlogin";
	}
	/**
	 * 进行后台管理登录操作
	 */
	@RequestMapping("slogin")
	public String BackenddoLogin(@RequestParam String userCode,
                                 @RequestParam String userPassword,
                                 HttpServletRequest request,
                                 HttpSession session) {
		BackendUser backendUser=loginservice.BackendLogin(userCode, userPassword);
		if (null!=backendUser) {
			session.setAttribute("userSession", backendUser);
			return "redirect:/sys/fmain.html";
		}else {
			request.setAttribute("error", "用户名或密码不正确");
			return "backendlogin";
		}
	}
	/**
	 * 注销后台管理员用户账号
	 * @param session
	 * @return
	 */
	@RequestMapping("/backlogout")
	public String BackenddoLoginRemove(HttpSession session) {
		session.removeAttribute("userSession");
		return "backendlogin";
	}
	/**
	 *  进入后台登录首页
	 * @return
	 */
	@RequestMapping(value="/sys/fmain.html")
	public String main() {
		return "backend/main";
	}
}
