package com.ideabobo.util;

import java.util.List;

public class Page {
	private List list;
	private int pageSize;
	private int pageNo;
	private int total;
	private int totalPage;
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}

	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	
}
