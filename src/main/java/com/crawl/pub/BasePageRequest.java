package com.crawl.pub;

/**
 * 页面分仓请求
 * @author 王帆
 * @date  2018年12月23日 下午10:12:11
 */
public class BasePageRequest {
	private int current;
	private int pageSize;
	public int getCurrent() {
		return current;
	}
	public void setCurrent(int current) {
		this.current = current;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public void initPage() {
		if(current<1) {
			current=0;
		}
		if(pageSize<0) {
			pageSize=10;
		}
		if(current>0) {
			current=(current-1)*pageSize;
		}
	}
}
