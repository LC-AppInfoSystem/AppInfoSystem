package cn.appsys.service.login;

import cn.appsys.pojo.BackendUser;
import cn.appsys.pojo.DevUser;

public interface Loginservice {
    /**
	 * 前台界面登录
     * @param devCode 开发者账号
	 * @return
	 */
    public DevUser DeveloperLogin(String devCode,String devPassword); 
	/**
	  * 后台界面登录
	  * @param userCode 管理员账号
	  * @return
	  */
    public BackendUser BackendLogin(String userCode,String userPassword);  

}
