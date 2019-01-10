package com.yl.trend.utils;

import lombok.Data;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.URL;

/**
 * 使用多线程的形式下载文件
 */
@Data
public class DownLoadsFile {

    private String name;
    private String url;


    public  void down(String url,String name) throws Exception{

        FileUtils.copyURLToFile(new URL(url),new File(name));
        System.out.println(name);

    }

    public static void main(String[] args) throws Exception{
        DownLoadsFile file = new DownLoadsFile();
        file.down("http://img1.imgtn.bdimg.com/it/u=2778470840,3459876771&fm=26&gp=0.avi","avi.jpg");
        //System.out.println(name);

    }




}
