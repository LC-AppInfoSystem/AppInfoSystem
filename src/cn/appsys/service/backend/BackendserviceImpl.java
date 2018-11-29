package cn.appsys.service.backend;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import cn.appsys.dao.backend.BackendMapper;
import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DataDictionary;

@Service
public class BackendserviceImpl implements Backendservice {
    @Resource 
	private BackendMapper backendMapper;

	@Override
	public List<AppInfo> BackendList(String softwareName, Integer status, Integer categoryLevel1,
			Integer categoryLevel2, Integer categoryLevel3, Integer flatformId, Integer devId, Integer pageIndex,
			Integer pageSize) {
		List<AppInfo> list=null;
		try {
			list=backendMapper.BackendList(softwareName, status, categoryLevel1, categoryLevel2, categoryLevel3, flatformId, devId, pageIndex, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int BanckendCount(String softwareName, Integer status, Integer categoryLevel1, Integer categoryLevel2,
			Integer categoryLevel3, Integer flatformId, Integer devId) {
	    int result=0;
	    try {
			result=backendMapper.BanckendCount(softwareName, status, categoryLevel1, categoryLevel2, categoryLevel3, flatformId, devId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<DataDictionary> DataList(String typeCode) {
		List<DataDictionary> dataDictionary=null;
		try {
			dataDictionary=backendMapper.DataList(typeCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataDictionary;
	}

	@Override
	public List<AppCategory> appCategorielist(String parentId) {
		List<AppCategory> list=null;
		try {
			list=backendMapper.appCategorielist(parentId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public AppInfo appInfoID(String id) {
		AppInfo appInfo=null;
		try {
			appInfo=backendMapper.appInfoID(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return appInfo;
	}

	@Override
	public boolean getAppInfoUpdate(Integer status, Integer id) {
		boolean lzw=false;
		try {
			lzw=backendMapper.getAppInfoUpdate(status,id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lzw;
	}


	@Override
	public AppVersion appversionID(String id) {
		AppVersion appVersion=null;
		 try {
			appVersion=backendMapper.appversionID(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return appVersion;
	}
}
