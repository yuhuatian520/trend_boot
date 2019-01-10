package com.yl.trend.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间转换工具
 */
public final class TimeUtils {

    public static String DateFormat(){
         return  new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

}
