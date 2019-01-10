package com.yl.trend.wxpayall;

import lombok.Data;

/**
 * 定义输出的规范
 */
@Data
public class MyResponseJson {
    public int code;
    public String tip;
    public Object data;
}
