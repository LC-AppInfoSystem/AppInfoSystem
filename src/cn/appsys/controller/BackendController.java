package cn.appsys.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.service.backend.Backendservice;
import cn.appsys.tools.PageSupport;

@Controller
@RequestMapping("/backend")
public class BackendController {
	
    @Resource
    private Backendservice backendservice; 
    /**
     * 加载分类菜单下拉框
     * @param pid
     * @return
     */
    @RequestMapping(value="categorylevellist")
    @ResponseBody
    public Object Reclassify (@RequestParam String pid ){
    	List<AppCategory> list=new ArrayList<AppCategory>();
    	if (pid==null) {
			Integer.parseInt(pid);
		}
    	list=backendservice.appCategorielist(pid);
 	   return JSONArray.toJSONString(list);
    }
    /**
     * 进入到APP审核修改界面
     */
    @RequestMapping("/view")
    public String APPInfoview(@RequestParam String aid, @RequestParam String vid,Model model){
    	AppInfo appInfo=backendservice.appInfoID(aid);
    	AppVersion appVersion=backendservice.appversionID(vid);
    	model.addAttribute("appVersion", appVersion);
    	model.addAttribute("appInfo", appInfo);
 	    return "/backend/appcheck";
    }
    /**
     * 进行修改跳转到App审核列表界面
     */
    @RequestMapping("/checksave")
    public String boo(@RequestParam Integer status,@RequestParam Integer id ,HttpSession httpSession,Model model){
       if (backendservice.getAppInfoUpdate(status,id)) {
    	   return "redirect:/backend/applist";
	}
 	   return "/backend/appcheck";
    }
    /**
     * 进入到后台APP审核列表
    */
   @RequestMapping("/applist")
   public String BackendList(
		                  @RequestParam(value="softwareName",required=false) String softwareName,
		       			  @RequestParam(value="queryStatus",required=false) Integer queryStatus,
		    			  @RequestParam(value="queryFlatformId",required=false) Integer queryFlatformId,
		    			  @RequestParam(value="queryCategoryLevel1",required=false) Integer queryCategoryLevel1,
		    			  @RequestParam(value="queryCategoryLevel2",required=false) Integer queryCategoryLevel2,
		    			  @RequestParam(value="queryCategoryLevel3",required=false) Integer queryCategoryLevel3,
		    			  @RequestParam(value="pageIndex",required=false) String pageIndex,
		    			  @RequestParam(value="devId",required=false) Integer devId,
		                  @RequestParam(value="pageSize",required=false)Integer pageSize,
		                   Model model)  {                                                                             
	   System.out.println("进入App管理的App审核列表=============");
	   //分页数
	   pageSize=5;
	   //待审核
	   queryStatus=1;
	      if (pageIndex==null || pageIndex.equals("")) {
				pageIndex="1";
	      }
	   //查询App业务
	    List<AppInfo> backendlist=backendservice.BackendList(softwareName, queryStatus, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, queryFlatformId, devId, (Integer.parseInt(pageIndex)-1)*pageSize, pageSize);
	    //查询总行数
	    int totalCount=backendservice.BanckendCount(softwareName, queryStatus, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, queryFlatformId, devId);
	    //查询总页数
	    int totalPageCount=totalCount%pageSize==0?totalCount/pageSize:totalCount/pageSize+1;
	    //查询所属平台下拉框
	    List<DataDictionary> datalist=backendservice.DataList("APP_FLATFORM");
	    //查询一级CAID下拉框
	    List<AppCategory> list=backendservice.appCategorielist(null);
	    PageSupport pages=new PageSupport();
	    pages.setList(backendlist);
	    pages.setCurrentPageNo(Integer.parseInt(pageIndex));
	    pages.setTotalCount(totalCount);
	    pages.setTotalPageCount(totalPageCount);
	    pages.setDatalist(datalist);
	    model.addAttribute("pages", pages);
	    model.addAttribute("appInfoList", backendlist);
	    model.addAttribute("currentPageNo",pageIndex );
	    model.addAttribute("totalCount", totalCount);
	    model.addAttribute("totalPageCount", totalPageCount);
	    model.addAttribute("flatFormList", datalist);
	    model.addAttribute("categoryLevel1List", list);
	    return "/backend/applist";                  
	    }
   }
  

   
