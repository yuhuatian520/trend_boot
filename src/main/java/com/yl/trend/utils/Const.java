package com.yl.trend.utils;

/**
 * 常量类
 * @author Administrator
 *
 */
public class Const {

	/**
	 * 每页记录数
	 */
	public static final int PAGE_SIZE=6;
	
	//表示有效 //表示是否已经下载
	public static final String MARK_YES="1";

	//表示无效 //表示没有下载
	public static final String MARK_NO="0";
	
	//上传图片存放的地址(包括其他的文件)
	public static final String FILE_SERVER_URL="http://localhost:80/fileserver/upload/";

	//public static final String FILE_SERVER_URL="http://http://yuhuatian1.natapp1.cc/fileserver/upload/";
	//是会员
	public static final Integer VIP_YES =1;
	//不是会员
	public static final Integer VIP_NO =0;



}
