package com.yl.trend.wxpayall;

/**
 * 自定义的异常类
 */
public class MyExceptionOutLog {

    public static String outLog(Exception e) {
        String error = "";
        StackTraceElement[] st = e.getStackTrace();
        for (StackTraceElement stackTraceElement : st) {
            String exclass = stackTraceElement.getClassName();
            String method = stackTraceElement.getMethodName();
            /*new Date() + ":" + */
            error += "\t[类:" + exclass + "]调用"
                    + method + "时在第" + stackTraceElement.getLineNumber()
                    + "行代码处发生异常!\n\t\t\t异常类型:" + e.getClass().getName()
                    + "\n\t\t异常信息为:"+ e.toString()+"\n";
        }
        return error;
    }
}
