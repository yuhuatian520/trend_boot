package com.yl.trend.utils;

public class PageUtil {
	/**
	 * 计算总页数
	 * @param allRow
	 * @param pageSize
	 * @return
	 */
	public static int countTotalPage(int allRow,int pageSize){
		return allRow % pageSize == 0? allRow / pageSize : allRow / pageSize + 1;
	}
	
	/**
	 * 计算当前第几页：0~1转换、如果没有第几页，默认第一页
	 * @param currentPage
	 * @return
	 */
	public static int countCurrentPage(Integer currentPage){
		if(currentPage==null) {
			currentPage =0;
		}
		return currentPage == 0 ? 1 : currentPage;
	}
	
	/**
	 * 计算起始记录数
	 * @param pageSize
	 * @param currentPage
	 * @return
	 */
	public static int countStart(int pageSize, int currentPage){
		return pageSize * (currentPage - 1);
	}
}
