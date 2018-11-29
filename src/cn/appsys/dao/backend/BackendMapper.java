package cn.appsys.dao.backend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DataDictionary;

public interface BackendMapper {
  public List<AppInfo> BackendList(@Param("softwareName")String softwareName,
		                           @Param("status")Integer status,
		                           @Param("categoryLevel1")Integer categoryLevel1,
		                           @Param("categoryLevel2")Integer categoryLevel2,
		                           @Param("categoryLevel3")Integer categoryLevel3,
		                           @Param("flatformId")Integer flatformId,	
		                           @Param("devId")Integer devId,
		                           @Param("pageIndex")Integer pageIndex,
		                           @Param("pageSize")Integer pageSize);   //分页查询后台的App审核列表
  public int BanckendCount(@Param("softwareName")String softwareName,
                           @Param("status")Integer status,
                           @Param("categoryLevel1")Integer categoryLevel1,
                           @Param("categoryLevel2")Integer categoryLevel2,
                           @Param("categoryLevel3")Integer categoryLevel3,
                           @Param("flatformId")Integer flatformId,
                           @Param("devId")Integer devId);   //查询后台的App审核列表总数
  public List<DataDictionary> DataList(@Param("typeCode")String typeCode);   //查询所属平台下拉框
  public List<AppCategory> appCategorielist(@Param("parentId")String parentId );   //三级菜单分类
  public AppInfo appInfoID(@Param("id")String id);      //根据id查询跳转到审核App界面
  public AppVersion appversionID(@Param("id")String id);      //根据id查询跳转到审核App界面
  public boolean getAppInfoUpdate(@Param("status")Integer status,@Param("id") Integer id);    //审核
  
  
}
