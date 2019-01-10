package com.yl.trend.wxutils;

public class WxConfig {

    public static  final String APPID ="wx5c547cd1ffab60d2";

    public static  final String APPSECRECT ="e8ce3ea35d5887ac523b5f9823927534";

    public static  final String GRANTTYPE ="authorization_code";
    //表示商户的ID
    public static final String MACH_ID="1498912702";
    //表示商户的密钥
    public static final String MACH_KEY="6mQVrh2y1Owa7j2sJLWgebRl4BBwxIic";////woshichenliangfeng33032619961129
    //支付回调的IP
    public static final String notify_url = "http://yuhuatian1.natapp1.cc/wx/notify";
    //签名方式
    public static final String SIGNTYPE = "MD5";
    //交易类型
    public static final String TRADETYPE = "JSAPI";
    //微信统一下单接口地址
    public static final String pay_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    //微信自动退款的接口
    public static final String REFUND ="https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_4";



}
