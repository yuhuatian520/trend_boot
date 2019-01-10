package com.yl.trend.utils;

import java.io.Serializable;
import java.util.List;

/**
 * 在视图/控制层/业务层之间进行数据传输的类
 * 包含了查询的结果集，以及分页的相关信息
 * @author Administrator
 *
 */
public class PageBean<T> implements Serializable{
	/**
	 * 要返回的某一页的记录集合
	 */
	private List<T> list;
	/**
	 * 总记录数
	 */
	private int allRow;
	/**
	 * 总页数
	 */
	private int totalPage;
	/**
	 * 当前是第几页
	 */
	private int currentPage;
	/**
	 * 每一页的记录数
	 */
	private int pageSize;
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public int getAllRow() {
		return allRow;
	}
	public void setAllRow(int allRow) {
		this.allRow = allRow;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
