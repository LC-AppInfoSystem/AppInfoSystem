package cn.appsys.service.backend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DataDictionary;

public interface Backendservice {
	 public List<AppInfo> BackendList(String softwareName,
                                      Integer status,
                                      Integer categoryLevel1,
                                      Integer categoryLevel2,
                                      Integer categoryLevel3,
                                      Integer flatformId,
                                      Integer devId,
                                      Integer pageIndex,
                                      Integer pageSize);   //分页查询后台的App审核列表
public int BanckendCount(String softwareName,
                         Integer status,
                         Integer categoryLevel1,
                         Integer categoryLevel2,
                         Integer categoryLevel3,
                         Integer flatformId,
                         Integer devId);   //分页查询后台的App审核列表总数
public List<DataDictionary> DataList(String typeCode);   //查询所属平台下拉框
public List<AppCategory> appCategorielist(String parentId );   //三级菜单分类
public AppInfo appInfoID(String id);      //根据id查询跳转到审核App界面
public AppVersion appversionID(String id);      //根据id查询跳转到审核App界面
public boolean getAppInfoUpdate(Integer status, Integer id);    //审核
}
