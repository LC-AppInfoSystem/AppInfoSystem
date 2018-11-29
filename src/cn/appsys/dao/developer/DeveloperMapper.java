package cn.appsys.dao.developer;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DataDictionary;

public interface DeveloperMapper {
public List<AppInfo> DeveloperList(@Param("softwareName")String softwareName,
             @Param("status")Integer status,
             @Param("categoryLevel1")Integer categoryLevel1,
             @Param("categoryLevel2")Integer categoryLevel2,
             @Param("categoryLevel3")Integer categoryLevel3,
             @Param("flatformId")Integer flatformId,	
             @Param("devId")Integer devId,
             @Param("pageIndex")Integer pageIndex,
             @Param("pageSize")Integer pageSize);   //分页查询前台的App审核列表
public int  DeveloperCount(@Param("softwareName")String softwareName,
     @Param("status")Integer status,
     @Param("categoryLevel1")Integer categoryLevel1,
     @Param("categoryLevel2")Integer categoryLevel2,
     @Param("categoryLevel3")Integer categoryLevel3,
     @Param("flatformId")Integer flatformId,
     @Param("devId")Integer devId);   //查询前台的App审核列表总数
public List<DataDictionary> DataList(@Param("typeCode")String typeCode);   //查询所属平台下拉框
public List<AppCategory> appCategorielist(@Param("parentId")int parentId );   //三级菜单分类
public boolean InsertAppInfo(AppInfo appInfo);   //新增APP基础信息界面
public AppInfo getAPKName(@Param("APKName")String APKname);   //判断APKName在新增基本信息是否存在
public boolean APPInfo(@Param("id")String id);    //删除APPInfo
public boolean DeleteVersion(@Param("id")String id);    //删除APPVersion
public int getVersion(@Param("id")String id);    //查询APPVersion
public boolean UpdateStatus(AppInfo appInfo);   //上下架
public AppInfo SelectAppInfoID(@Param("id")int id);  //根据ID查询APP基础信息
public List<AppVersion> SelectAppVersionID(@Param("id")int id);  //根据ID查询历史版本信息
public boolean InsertAppVersion(AppVersion appVersion);   //新增版本信息
public boolean getinsert(@Param("id")Integer id,@Param("versionId")Integer versionId);		//修改最新版本
public AppVersion getsel(@Param("appId")Integer appId);	  //根据APPID查询最新版本
public AppVersion getseleId(@Param("id")Integer id);		//根据id查询版本
public int getupdate(AppVersion appVersion);			//修改版本
public boolean getDelete(@Param("id")int id);    //删除LOGO图片
public boolean getDeleteAppversion(@Param("id")int id);    //删除APK
public int getappinfoupdate(AppInfo appInfo);				//修改基本信息的方法
}
