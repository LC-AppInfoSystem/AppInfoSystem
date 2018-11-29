package cn.appsys.tools;

import java.util.List;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.DataDictionary;

/**
 * 分页类
 * @author LZW
 *
 */
public class PageSupport {
	// 总页数
    private int totalPageCount = 0;
    // 页面大小，即每页显示记录数
    private int pageSize = 5;
    // 记录总数
    private int totalCount;
    // 当前页码
    private int currentPageNo = 1;
    // 每页订单集合
   private List<AppInfo> list;
   private List<DataDictionary> datalist;
   private List<AppCategory> categoryLevel1;

    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        if (pageSize > 0)
            this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        if (totalCount > 0) {
            this.totalCount = totalCount;
            // 计算总页数
            this.totalPageCount = this.totalCount % pageSize == 0 ? (this.totalCount / pageSize)
                    : (this.totalCount / pageSize + 1);
            if(currentPageNo>totalPageCount)
                this.currentPageNo = totalPageCount;
        }
    }

    public int getTotalPageCount() {

        return totalPageCount;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

	public List<AppInfo> getList() {
		return list;
	}

	public void setList(List<AppInfo> list) {
		this.list = list;
	}

	public int getCurrentPageNo() {
		return currentPageNo;
	}

	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}
	public List<DataDictionary> getDatalist() {
		return datalist;
	}
	public void setDatalist(List<DataDictionary> datalist) {
		this.datalist = datalist;
	}
	public List<AppCategory> getCategoryLevel1() {
		return categoryLevel1;
	}
	public void setCategoryLevel1(List<AppCategory> categoryLevel1) {
		this.categoryLevel1 = categoryLevel1;
	}
}
