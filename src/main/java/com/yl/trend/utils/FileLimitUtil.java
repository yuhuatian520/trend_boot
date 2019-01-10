package com.yl.trend.utils;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileLimitUtil extends HttpServlet {

    public  void fileUploads(HttpServletRequest request,HttpServletResponse response)throws Exception{
        /*SmartUpload su = new SmartUpload();
        //初始化对象
        su.initialize(getServletConfig(), request,response);
        //设置上传文件大小
        su.setMaxFileSize(1024*1024*10);
        //设置所有文件的大小
        su.setTotalMaxFileSize(1024*1024*100);
        //设置允许上传文件类型
        su.setAllowedFilesList("txt,jpg,gif");
        String result = "上传成功！";
        //设置禁止上传的文件类型
        try {
            su.setDeniedFilesList("rar,jsp,js");
            //上传文件
            su.upload();
            int count = su.save(filePath);
            System.out.println("上传成功" +  count + "个文件！");
        } catch (Exception e) {
            result = "上传失败！";
            e.printStackTrace();*/
    }






}
