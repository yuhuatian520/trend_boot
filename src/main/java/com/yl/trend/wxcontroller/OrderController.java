package com.yl.trend.wxcontroller;


import com.google.gson.Gson;
import com.yl.trend.wxpayall.HttpUtil;
import com.yl.trend.wxpayall.PayCommonUtil;
import com.yl.trend.wxpayall.XMLUtil;
import com.yl.trend.wxutils.WxConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 表示支付的控制层
  */


@Slf4j
@Controller
@RequestMapping("/wx/payfull/")
public class OrderController {

    @ResponseBody
    @RequestMapping("returnparam")
    public void doOrder(HttpServletRequest request, HttpServletResponse response) throws Exception{
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");


        //得到openid（微信用户唯一的openid）
        String openid = request.getParameter("openid");
        System.out.println("这个数据看下-------"+openid);
        log.info("这里获的是这个对象的数据 先看看再说吧 反正不知道啥"+openid);
        //得到价钱（自定义）
        int fee = 0;
        if (null != request.getParameter("price")) {
            fee = Integer.parseInt(request.getParameter("price").toString());
        }
        log.info("价格不知道什么东西 先看看再说吧",fee);
        //得到商品的ID（自定义）
        String goodsid=request.getParameter("goodsid");
        //订单标题（自定义）
        String title = request.getParameter("title");
        //时间戳
        String times = System.currentTimeMillis() + "";

        //订单编号（自定义 这里以时间戳+随机数）
        Random random = new Random();
        String did = times+random.nextInt(1000);

        SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
        packageParams.put("appid", WxConfig.APPID);//微信小程序ID
        packageParams.put("mch_id", WxConfig.MACH_ID);//商户ID
        packageParams.put("nonce_str", times);//随机字符串（32位以内） 这里使用时间戳
        packageParams.put("body", title);//支付主体名称 自定义
        packageParams.put("out_trade_no", did+goodsid);//编号 自定义以时间戳+随机数+商品ID
        packageParams.put("total_fee", fee);//价格 自定义
        //packageParams.put("spbill_create_ip", remoteAddr);
        //微信的回调地址
        packageParams.put("notify_url", "https://api.mch.weixin.qq.com/pay/unifiedorder/wx/payfull/buy");//支付返回地址要外网访问的到（订单存入数据库）

        packageParams.put("trade_type", "JSAPI");//这个api有，固定的
        //packageParams.put("openid", openid);//用户的openid 可以要 可以不要
        //获取sign
        String sign = PayCommonUtil.createSign("UTF-8", packageParams, WxConfig.MACH_KEY);//最后这个是自己在微信商户设置的32位密钥
        packageParams.put("sign", sign);
        System.out.println("这里不知道是什么参数 先看看再说:"+sign);
        //转成XML
        String requestXML = PayCommonUtil.getRequestXml(packageParams);
        System.out.println("这里不知道是什么参数 先看看再说:"+requestXML);
        //得到含有prepay_id的XML
        String resXml = HttpUtil.postData("https://api.mch.weixin.qq.com/pay/unifiedorder", requestXML);
        System.out.println(resXml);
        //解析XML存入Map
        Map map = XMLUtil.doXMLParse(resXml);
        System.out.println("这里不知道是什么参数 先看看再说:"+map);
        // String return_code = (String) map.get("return_code");
        //得到prepay_id
        String prepay_id = (String) map.get("prepay_id");
        SortedMap<Object, Object> packageP = new TreeMap<Object, Object>();
        packageP.put("appId", WxConfig.APPID);//！！！注意，这里是appId,上面是appid
        packageP.put("nonceStr", times);//时间戳
        packageP.put("package", "prepay_id=" + prepay_id);//必须把package写成 "prepay_id="+prepay_id这种形式
        packageP.put("signType", "MD5");//paySign加密
        packageP.put("timeStamp", (System.currentTimeMillis() / 1000) + "");
        //得到paySign
        String paySign = PayCommonUtil.createSign("UTF-8", packageP, WxConfig.MACH_KEY);
        packageP.put("paySign", paySign);
        //将packageP数据返回给小程序
        Gson gson = new Gson();
        String json = gson.toJson(packageP);
        PrintWriter pw = response.getWriter();
        System.out.println(json);
        pw.write(json);
        pw.close();
    }
    //订单存入数据库  上面参数 packageParams.put("notify_url", "http://你的IP地址/order/buy.action");回调的就是这个方法
    @RequestMapping(value="buy")
    @ResponseBody
    public void Buy(HttpServletRequest request,HttpServletResponse response) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while((line = br.readLine()) != null){
            sb.append(line);
        }
        br.close();
        //sb为微信返回的xml
        String notityXml = sb.toString();
        String resXml = "";
        Map map = XMLUtil.doXMLParse(notityXml);
        String returnCode = (String) map.get("return_code");

        if("SUCCESS".equals(returnCode)){
            String out_trade_no=(String) map.get("out_trade_no");
            String timestamp=(String) map.get("nonce_str");
            String goodsid=out_trade_no.substring(out_trade_no.length()-3, out_trade_no.length());
            String openid=(String) map.get("openid");
            System.out.println("不知道这里是什么值 先打印出来再说吧:***"+map);

             //这里是存入数据库的逻辑 代写


            resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                    + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
        }else {
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                    + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
        }
        BufferedOutputStream out = new BufferedOutputStream(
                response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();

    }

}
