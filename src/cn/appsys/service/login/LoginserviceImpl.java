package cn.appsys.service.login;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.login.LoginMapper;
import cn.appsys.pojo.BackendUser;
import cn.appsys.pojo.DevUser;

@Service
public class LoginserviceImpl implements Loginservice {
    @Resource
	private LoginMapper loginMapper;

	@Override
	public DevUser DeveloperLogin(String devCode, String devPassword) {
		DevUser devUser=null;
		devUser=loginMapper.DeveloperLogin(devCode);
		if(null!=devUser) {
			if (!devUser.getDevPassword().equals(devPassword)) {
				devUser=null;
			}
		}
		return devUser;
	}
	@Override
	public BackendUser BackendLogin(String userCode, String userPassword) {
		BackendUser backendUser=null;
		backendUser=loginMapper.BackendLogin(userCode);
		if (null!=backendUser) {
			if (!backendUser.getUserPassword().equals(userPassword)) {
				backendUser=null;
			}
		}
		return backendUser;
	}
}
