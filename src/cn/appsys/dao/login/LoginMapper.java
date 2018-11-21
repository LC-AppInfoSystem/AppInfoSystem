package cn.appsys.dao.login;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.BackendUser;

public interface LoginMapper {
    public BackendUser BackendLogin(@Param("userCode")String userCode);       //后台的登录
}
