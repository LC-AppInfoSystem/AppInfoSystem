package cn.appsys.service.developer;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import cn.appsys.dao.developer.DeveloperMapper;
import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DataDictionary;

@Service
public class DeveloperserviceImpl implements Developerservice {
    @Resource 
 	private DeveloperMapper developerMapper;

	@Override
	public List<AppInfo> DeveloperList(String softwareName, Integer status, Integer categoryLevel1,
			Integer categoryLevel2, Integer categoryLevel3, Integer flatformId, Integer devId, Integer pageIndex,
			Integer pageSize) {
	    List<AppInfo> appInfos=null;
	     try {
			appInfos=developerMapper.DeveloperList(softwareName, status, categoryLevel1, categoryLevel2, categoryLevel3, flatformId, devId, pageIndex, pageSize);
		} catch (Exception e) {
			e.printStackTrace();		}
		return appInfos;
	}

	@Override
	public int DeveloperCount(String softwareName, Integer status, Integer categoryLevel1, Integer categoryLevel2,
			Integer categoryLevel3, Integer flatformId, Integer devId) {
		int count=0;
		 try {
			count=developerMapper.DeveloperCount(softwareName, status, categoryLevel1, categoryLevel2, categoryLevel3, flatformId, devId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public List<DataDictionary> DataList(String typeCode) {
		List<DataDictionary> dictionaries=null;
		try {
			dictionaries=developerMapper.DataList(typeCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dictionaries;
	}

	@Override
	public List<AppCategory> appCategorielist(int parentId) {
		List<AppCategory>appCategories=null;
		try {
			appCategories=developerMapper.appCategorielist(parentId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return appCategories;
	}

	@Override
	public boolean InsertAppInfo(AppInfo appInfo) {
		boolean lzw=false;
		try {
			lzw=developerMapper.InsertAppInfo(appInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lzw;
	}

	@Override
	public AppInfo getAPKName(String APKname) {
		AppInfo appInfo=null;
		try {
			appInfo=developerMapper.getAPKName(APKname);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return appInfo;
	}

	@Override
	public boolean APPInfo(String id) {
		boolean lzw=false;
		try {
			lzw=developerMapper.APPInfo(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lzw;
	}

	@Override
	public boolean DeleteVersion(String id) {
		boolean lzw=false;
		try {
			lzw=developerMapper.DeleteVersion(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lzw;
	}

	@Override
	public int getVersion(String id) {
		int count=0;
		try {
			count=developerMapper.getVersion(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public boolean UpdateStatus(AppInfo appInfo) {
		boolean lzw=false;
		try {
			lzw=developerMapper.UpdateStatus(appInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lzw;
	}

	@Override
	public AppInfo SelectAppInfoID(int id) {
		AppInfo appInfo=null;
		try {
			appInfo=developerMapper.SelectAppInfoID(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return appInfo;
	}

	@Override
	public  List<AppVersion> SelectAppVersionID(int id) {
		 List<AppVersion> appVersion=null;
		try {
			appVersion=developerMapper.SelectAppVersionID(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return appVersion;
	}

	@Override
	public boolean InsertAppVersion(AppVersion appVersion) {
		boolean lzw=false;
		try {
			lzw=developerMapper.InsertAppVersion(appVersion);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lzw;
	}

	@Override
	public boolean getinsert(Integer id, Integer versionId) {
		boolean lzw=false;
		try {
			lzw=developerMapper.getinsert(id, versionId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lzw;
	}

	@Override
	public AppVersion getsel(Integer appId) {
		AppVersion appVersion=null;
		try {
			appVersion=developerMapper.getsel(appId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return appVersion;
	}

	@Override
	public AppVersion getseleId(Integer id) {
		AppVersion appVersion=null;
		try {
			appVersion=developerMapper.getseleId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return appVersion;
	}

	@Override
	public int getupdate(AppVersion appVersion) {
		int count=0;
		try {
			count=developerMapper.getupdate(appVersion);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public boolean getDelete(int id) {
		boolean lzw=false;
		try {
			lzw=developerMapper.getDelete(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lzw;
	}

	@Override
	public boolean getDeleteAppversion(int id) {
		boolean lzw=false;
		try {
			lzw=developerMapper.getDeleteAppversion(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lzw;
	}

	@Override
	public int getappinfoupdate(AppInfo appInfo) {
		int count=0;
		try {
			count=developerMapper.getappinfoupdate(appInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
}
