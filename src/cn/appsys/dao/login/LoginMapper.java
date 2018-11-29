package cn.appsys.dao.login;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.BackendUser;
import cn.appsys.pojo.DevUser;

public interface LoginMapper {
   /**
    * 前台界面登录
    * @param devCode 开发者账号
    * @return
    */
   public DevUser DeveloperLogin(@Param("devCode")String devCode); 
   /**
    * 后台界面登录
    * @param userCode 管理员账号
    * @return
    */
   public BackendUser BackendLogin(@Param("userCode")String userCode);   
}
