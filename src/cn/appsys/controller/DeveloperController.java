package cn.appsys.controller;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.net.ssl.SSLEngineResult.Status;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.omg.PortableServer.ID_ASSIGNMENT_POLICY_ID;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.developer.Developerservice;
import cn.appsys.tools.PageSupport;

@Controller
@RequestMapping("/developer")
public class DeveloperController {
	@Resource
	private Developerservice developerservice;
    /**
     * 进入App前台管理的App审核列表
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
	   
	   pageSize=5;   //分页数
	   if (pageIndex==null || pageIndex.equals("")) {
			  pageIndex="1";
	      }
	   //查询前台App业务
	   List<AppInfo> backendlist=developerservice.DeveloperList(softwareName, queryStatus, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, queryFlatformId, devId, (Integer.parseInt(pageIndex)-1)*pageSize, pageSize);
	   //查询总行数
	   int totalCount=developerservice.DeveloperCount(softwareName, queryStatus, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, queryFlatformId, devId);
	   //查询总页数
	   int totalPageCount=totalCount%pageSize==0?totalCount/pageSize:totalCount/pageSize+1;
	   //查询所属平台下拉框
	   List<DataDictionary> datalist=developerservice.DataList("APP_FLATFORM");
	   //查询APP状态下拉框
	   List<DataDictionary> APPlist=developerservice.DataList("APP_STATUS");
	   //查询一级分类下拉框
	   List<AppCategory> list=developerservice.appCategorielist(0);
	    //加载到分页类
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
	    model.addAttribute("statusList", APPlist);
	    return "/developer/appinfolist";                  
	    }
    /**
	 * 加载三级分类菜单
	 * @param pid
	 * @return
	 */
   @RequestMapping("/categorylevellist")
   @ResponseBody
   public Object crObject(@RequestParam Integer pid ){
   	int id=0;
   	if(pid==null){
   	   pid=id;
   	 }
	return JSONArray.toJSONString(developerservice.appCategorielist(pid));
   }
   /**
    * 加载所属平台下拉框
    * @param tcode
    * @return
    */
    @RequestMapping(value="datadictionarylist")
    @ResponseBody
    public Object PlatForm(@RequestParam String tcode){
   	List<DataDictionary> datalist=developerservice.DataList(tcode);
 	    return JSONArray.toJSONString(datalist);
    }
    /**
     * 进入新增APP基础信息
     * @param appInfo
     * @return
     */
    @RequestMapping("/appinfoadd")
    public String AppInfoInsert(@ModelAttribute("appInfo")AppInfo appInfo) {
		return "/developer/appinfoadd";
	}   
    /**
     * 在新增APP基础信息版本判断APKName
     * @param APKName APK名称
     * @return
     */
     @RequestMapping(value="apkexist")
     @ResponseBody
     public Object IFAPKName(@RequestParam String APKName){
       HashMap<String, String> reHashMap=new HashMap<String, String>();
        if(StringUtils.isNullOrEmpty(APKName)) {
    	   reHashMap.put("APKName", "empty");
        }else {
    	   AppInfo appInfo=developerservice.getAPKName(APKName);
    	   if (null!=appInfo) 
    		   reHashMap.put("APKName", "exist");
           else
        	   reHashMap.put("APKName", "noexist");
	    }	
  	    return JSONArray.toJSONString(reHashMap);
    }
    /**
     * 进行添加基础信息并跳转到App前台管理的App审核列表
     */
    @RequestMapping("/appinfoaddsave")
    public String AppInfoInsertSave(AppInfo appInfo,
    		                        HttpSession session,
    		                        HttpServletRequest request,
    		                        @RequestParam(value="a_logoPicPath",required=false)MultipartFile attach) {
	  String logoPicPath = null;
	   //判断文件是否为空
	  if(!attach.isEmpty()) {
		String path = request.getSession().getServletContext().getRealPath("statics" + java.io.File.separator + "uploadfiles");
		String oldFileName=attach.getOriginalFilename();  //原文件名
		String prefix=FilenameUtils.getExtension(oldFileName);  //原文件后缀
		int filesize = 524288000;
		if(attach.getSize() > filesize) { //上传文件大小不得超过500kB
			request.setAttribute("fileUploadError", "* 上传文件大小不得超过500MB");
			return "";
		}else if(prefix.equalsIgnoreCase("jpg")){
			String fileName = System.currentTimeMillis() + RandomUtils.nextInt(1000000) + "_Personal.apk";
			File targetFile = new File(path, fileName);
			if(!targetFile.exists()) {
				targetFile.mkdirs();
			}
			try {
				attach.transferTo(targetFile);
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("fileUploadError", "上传失败！");
				return "/developer/appinfoadd";
			}
			logoPicPath=path+File.separator+fileName;
		}else {
			request.setAttribute("fileUploadError", "*上传格式不正确");
			return "/developer/appinfoadd";
		}
	}
	appInfo.setCreatedBy(((DevUser)session.getAttribute("devUserSession")).getId());
	appInfo.setCreationDate(new Date());
	appInfo.setLogoPicPath(logoPicPath);
	if (developerservice.InsertAppInfo(appInfo)) {
		return "redirect:/developer/applist";
	}
	return "/developer/appinfoadd";
    }
    /**
     * 删除APP列表
     * @param id
     * @return
     */
    @RequestMapping("/delapp")
    @ResponseBody
    public Object DeleteAPPInfoSave(@RequestParam String id) {
     HashMap<String, String> reHashMap=new HashMap<String, String>();
     int count=developerservice.getVersion(id);
     if (StringUtils.isNullOrEmpty(id)) {
  	   reHashMap.put("delResult", "notexist");
     }else {
		if (count>0) {
			if (developerservice.DeleteVersion(id)) {
		      if (developerservice.APPInfo(id)) {
		    	  reHashMap.put("delResult", "true");
			     }else {
			      reHashMap.put("delResult", "false");
				}
			}
		}else {
			 if (developerservice.APPInfo(id)) {
		    	  reHashMap.put("delResult", "true");
			 }else {
			      reHashMap.put("delResult", "false");
				}
		}	
	}
	   return JSONArray.toJSONString(reHashMap);
	}
    /**
     * APP列表的上架操作和下架操作
     */
    @RequestMapping("/sale")
    @ResponseBody
    public Object getdevupdate(@RequestParam(value="appId",required=false)Integer id,
			@RequestParam(value="saleSwitch",required=false)String saleSwitch) {
		System.out.println("进行删除操作=================");
		HashMap<String, String> resultMap = new HashMap<String, String>();
		AppInfo appInfo = new AppInfo();
		appInfo.setId(id);
		resultMap.put("errorCode", "0");
		if(saleSwitch.equals("open")) {
			appInfo.setStatus(4);
			boolean lzw = developerservice.UpdateStatus(appInfo);
			if(lzw) {
				resultMap.put("resultMsg", "success");
			}else {
				resultMap.put("resultMsg", "failed");
			}
		}
		if(saleSwitch.equals("close")){
			appInfo.setStatus(5);
			boolean lzw = developerservice.UpdateStatus(appInfo);
			if(lzw) {
				resultMap.put("resultMsg", "success");
			}else {
				resultMap.put("resultMsg", "failed");
			}
		}
		return JSONArray.toJSONString(resultMap);
	}
    /**
     * APP列表的查看操作
     * @param appinfoid
     * @param model
     * @return
     */
    @RequestMapping("/appview/{appinfoid}")
    public String views(@PathVariable int appinfoid,Model model) {
    	AppInfo appInfo=developerservice.SelectAppInfoID(appinfoid);
    	List<AppVersion> appVersion=developerservice.SelectAppVersionID(appinfoid);
    	model.addAttribute("appInfo", appInfo);
    	model.addAttribute("appVersionList", appVersion);
		return "/developer/appinfoview";
	}
    @RequestMapping("/apdsd")
    public String l(){
    	System.out.println("查看基础信息返回到列表=========");
    	return "redirect:/developer/applist";
    }
    @RequestMapping("/lzw")
    public String z(){
    	return "redirect:/developer/applist";
    }
    @RequestMapping("/list")
    public String w(){
    	return "redirect:/developer/applist";
    }
    @RequestMapping("/lxl")
    public String Good(){
    	System.out.println("查看基础信息返回到列表=========");
    	return "redirect:/developer/applist";
    }
    @RequestMapping("/apdss")
    public String ssl(){
    	System.out.println("新增版本信息返回到列表=========");
    	return "redirect:/developer/applist";
    }
    /**
     * 进入到新增APP版本界面
     */
    @RequestMapping("/appversionadd/{appinfoid}")
    public String InsertAppVersion(@PathVariable int appinfoid,Model model) {
		List<AppVersion> appVersionList = developerservice.SelectAppVersionID(appinfoid);
		AppVersion appVersion = new AppVersion();
		appVersion.setAppId(appinfoid);
		model.addAttribute("appVersion",appVersion);
 		model.addAttribute("appVersionList",appVersionList);
 		return "/developer/appversionadd";
    }
    /**
     * 新增APP基础信息并跳转到APP后台列表
     */
    @RequestMapping("/addversionsave")
    public String  InsertAppVersionSave(AppVersion appversion,HttpSession session,HttpServletRequest request,
			@RequestParam(value="a_downloadLink",required=false) MultipartFile attact) {
		String apkFileName=null;
		String apkLocPath=null;
			String path=request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
			String oldFileName=attact.getOriginalFilename();//原文件名
			String prefix=FilenameUtils.getExtension(oldFileName);//原文件后缀
			if (prefix.equalsIgnoreCase("jpg")) {
				appversion.setApkFileName(oldFileName);
				String fileName = appversion.getApkFileName() + ".jpg";
				File targetFile=new File(path,fileName);
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}
				try {
					attact.transferTo(targetFile);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("第二个错误");
					return "developer/appversionadd";
				}
				apkFileName =fileName;
				apkLocPath = path+File.separator+fileName;
			}
		appversion.setCreationDate(new Date());
		appversion.setModifyDate(new Date());
		appversion.setApkFileName(apkFileName);
		appversion.setApkLocPath(apkLocPath);
		appversion.setDownloadLink(request.getContextPath()+"/statics/uploadfiles/"+appversion.getApkFileName());
		if (developerservice.InsertAppVersion(appversion)) {
			AppVersion appversion2=developerservice.getsel(appversion.getAppId());
			developerservice.getinsert(appversion.getAppId(), appversion2.getId());
			return "redirect:/developer/applist";
		}
		return "/developer/appversionadd";
	}
    @RequestMapping("/appversionmodify")
	public String getupdateAPPversion(@RequestParam Integer vid,@RequestParam Integer aid,Model model) {
		List<AppVersion> appVersionList = developerservice.SelectAppVersionID(aid);
		AppVersion appVersion1 = new AppVersion();
		appVersion1.setAppId(aid);
		AppVersion appVersion = developerservice.getseleId(vid);
		model.addAttribute("appVersion",appVersion);		//查询最新版本传到页面上去
 		model.addAttribute("appVersionList",appVersionList);	//查询历史版本传到页面上面
 		return "/developer/appversionmodify";
	}
    @RequestMapping("/appversionmodifysave")
	public String getupdates(@RequestParam(value="id",required=false) Integer id,
							@RequestParam(value="versionSize",required=false) String versionSize,
							@RequestParam(value="versionInfo",required=false) String versionInfo) {
    	System.out.println("进行修改APP最新版本信息==============");
		BigDecimal bigDecimal = new BigDecimal(versionSize);
		AppVersion appVersion = new AppVersion();
		appVersion.setId(id);
		appVersion.setVersionSize(bigDecimal);
		appVersion.setModifyDate(new Date());
		appVersion.setVersionInfo(versionInfo);
		int count = developerservice.getupdate(appVersion);
		if(count > 0) {
			return "redirect:/developer/applist";
		}else {
			return "redirect:/developer/appversionmodify";
		}
	}
    @RequestMapping("/appinfomodify/{appinfoid}")
	public String getUpdateAppinfo(@PathVariable Integer appinfoid,Model model) {
		AppInfo appInfo = developerservice.SelectAppInfoID(appinfoid);
		model.addAttribute("appInfo",appInfo);
		return "/developer/appinfomodify";
	}
    @RequestMapping("/delfile")
    @ResponseBody
    public Object DeleteLOGO(@RequestParam int id,@RequestParam String flag) {
    	HashMap<String, String>reHashMap=new HashMap<String, String>();
    	if (flag.equals("apk")) {
    		if (developerservice.getDeleteAppversion(id)) {
    			reHashMap.put("result", "success");
    		}else {
    			reHashMap.put("result", "failed");
    		}
		}
    	if (flag.equals("logo")) {
    		if (developerservice.getDelete(id)) {
    			reHashMap.put("result", "success");
    		}else {
    			reHashMap.put("result", "failed");
    		}
		}
    	return JSONArray.toJSONString(reHashMap);
	}
    @RequestMapping(value="/appinfomodifysave")
	public String getappinfoupdate(AppInfo appInfo) {
		int count = developerservice.getappinfoupdate(appInfo);
		if(count > 0) {
			return "redirect:/developer/applist";
		}
		return "redirect:/developer/appinfomodify";
	}
}




