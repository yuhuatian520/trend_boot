package com.yl.trend.wxcontroller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yl.trend.entity.User;
import com.yl.trend.service.UserService;
import com.yl.trend.wxpayall.XMLUtil;
import com.yl.trend.wxpayall.wxpay2.IpUtils;
import com.yl.trend.wxpayall.wxpay2.Json;
import com.yl.trend.wxpayall.wxpay2.PayUtil;
import com.yl.trend.wxpayall.wxpay2.StringUtils;
import com.yl.trend.wxutils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/wx/")
public class WXLoginController {
	
	@Autowired
	private RedisOperator redis;
	@Autowired
	private UserService userService;





	/**
	 * 未解决的bug就是不能在redis中设置key
	 * @param code
	 * @param encryptedData
	 * @param iv
	 * @param user
	 * @return
	 */
	@PostMapping("wxLogin")
	public IMoocJSONResult wxLogin(String code, String encryptedData, String iv,User user) {

		System.out.println("wxlogin - code: " + code);

//		https://api.weixin.qq.com/sns/jscode2session?
//				appid=APPID&
//				secret=SECRET&
//				js_code=JSCODE&
//				grant_type=authorization_code

		String url = "https://api.weixin.qq.com/sns/jscode2session";
		Map<String, String> param = new HashMap<>();
		param.put("appid", WxConfig.APPID);
		param.put("secret", WxConfig.APPSECRECT);
		param.put("js_code", code);
		param.put("grant_type", WxConfig.GRANTTYPE);

		String wxResult = HttpClientUtil.doGet(url, param);
		System.out.println("不知道嫩狗得到什么____" + wxResult);
		JSONObject jsonObject = JSON.parseObject(wxResult);
		System.out.println("jsonobject================" + jsonObject);
		redis.set("session_key",jsonObject.getString("session_key"));//差不多两小时

		JSONObject jso = new WechatGetUserInfoUtil().getUserInfo(encryptedData, iv, redis.get("session_key"));
		System.out.println("反正不知道是啥:"+jso);
		if(StringUtils.isNotBlank(jsonObject.getString("openid"))&&StringUtils.isNotBlank(redis.get("session_key"))){
			//解密获取用户信息
			JSONObject userInfoJSON=new WechatGetUserInfoUtil().getUserInfo(encryptedData,redis.get("session_key"),iv);
				//拿到里面openId

			if(userInfoJSON!=null){
				//这步应该set进实体类
				Map userInfo = new HashMap();
				userInfo.put("openId", userInfoJSON.get("openId"));
				userInfo.put("nickName", userInfoJSON.get("nickName"));
				userInfo.put("gender", userInfoJSON.get("gender"));
				userInfo.put("city", userInfoJSON.get("city"));
				userInfo.put("province", userInfoJSON.get("province"));
				userInfo.put("country", userInfoJSON.get("country"));
				userInfo.put("avatarUrl", userInfoJSON.get("avatarUrl"));
				// 解密unionId & openId;
				if (userInfoJSON.get("unionId")!=null) {
					userInfo.put("unionId", userInfoJSON.get("unionId"));
				}

				//然后根据openid去数据库判断有没有该用户信息，若没有则存入数据库，有则返回用户数据
				Map<String,Object> dataMap = new HashMap<>();
				dataMap.put("userInfo", userInfo);
				String uuid= UUID.randomUUID().toString();
				dataMap.put("WXTOKEN", uuid);

				//redisTemplate.opsForValue().set(uuid,userInfo);
				//redisTemplate.expire(uuid,appTimeOut, TimeUnit.SECONDS);
				redis.set("WXTOKEN",uuid,1000*60*60*59);
				try {
					BeanUtils.populate(user,userInfo);
					System.out.println("对象的格式看看======:"+user);

					User userOpenid = userService.findByOpenId(userInfo.get("openId").toString());
						if (userOpenid!=null){
							log.info("数据库中存在");
							//return null;
						}else {
							Boolean mark = userService.addUserInfos(user);
							if (mark) {
								log.info("成功执行");
							}

						}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}

				System.out.println("用户信息"+userInfo);
				System.out.println("用户信息的数据"+dataMap);

				return IMoocJSONResult.build(200,"成功",userInfo);
			}else{
				return IMoocJSONResult.errorMsg("解密失败");
			}
		}

		return IMoocJSONResult.build(200,"成功",jsonObject);
	}

	/**
	 * 支付接口地址
	 * @param openId
	 * @param request
	 * @return
	 */
	@RequestMapping("pay")
	public Json pay(String openId, HttpServletRequest request){
		//开始请求的时间
		long startTime = (int)System.currentTimeMillis();
		log.info("先看参数里面都是有啥的  反正也不主导",request);
		Json json = new Json();
		try{
			//生成的随机字符串
			String nonce_str = StringUtils.getRandomStringByLength(32);
			//商品名称
			String body = "毅连科技";
			//获取本机的ip地址
			String spbill_create_ip = IpUtils.getIpAddr(request);
			log.info("先看看这个IP地址是哪个的:++++++哒哒哒",spbill_create_ip);

			String orderNo = StringUtils.getRandomStringByLength(32);
			String total=request.getParameter("total");//支付金额，单位：分，这边需要转成字符串类型，否则后面的签名会失败

			//Integer mo = request.getParameter("total");
			/*Integer mo=1;
			String money=String.valueOf(mo);*/

			Map<String, String> packageParams = new HashMap<String, String>();
			packageParams.put("appid", WxConfig.APPID);
			packageParams.put("mch_id", WxConfig.MACH_ID);
			packageParams.put("nonce_str", nonce_str);
			packageParams.put("body", body);
			packageParams.put("out_trade_no", orderNo);//商户订单号
			packageParams.put("total_fee", total);//支付金额，这边需要转成字符串类型，否则后面的签名会失败
			packageParams.put("spbill_create_ip", spbill_create_ip);
			packageParams.put("notify_url", WxConfig.notify_url);

			packageParams.put("sign_type",WxConfig.SIGNTYPE);
			packageParams.put("trade_type", WxConfig.TRADETYPE);
			packageParams.put("openid", openId);

			// 除去数组中的空值和签名参数
			packageParams = PayUtil.paraFilter(packageParams);
			String prestr = PayUtil.createLinkString(packageParams); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
			log.info("=======================签名：" + prestr + "=====================");
			//MD5运算生成签名，这里是第一次签名，用于调用统一下单接口
			String mysign = PayUtil.sign(prestr, WxConfig.MACH_KEY, "utf-8").toUpperCase();
			log.info("=======================第一次签名：" + mysign + "=====================");

			//packageParams = PayUtil.paraFilter(packageParams);
			//String prestr = PayUtil.createLinkString(packageParams);
			packageParams.put("sign",mysign);
			//拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去
			String xml = PayUtil.mapToXml(packageParams);
//			String xml = "<xml>" + "<appid>" + WxConfig.APPID + "</appid>"
//					+ "<body><![CDATA[" + body + "]]></body>"
//					+ "<mch_id>" + WxConfig.MACH_ID + "</mch_id>"
//					+ "<nonce_str>" + nonce_str + "</nonce_str>"
//					+ "<notify_url>" + WxConfig.notify_url + "</notify_url>"
//					+ "<openid>" + openId + "</openid>"
//					+ "<out_trade_no>" + orderNo + "</out_trade_no>"
//					+ "<sign>" + mysign + "</sign>"
//					+ "<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>"
//					+ "<total_fee>" + total + "</total_fee>"
//					+ "<trade_type>" + WxConfig.TRADETYPE + "</trade_type>"
//					+ "</xml>";

			System.out.println("调试模式_统一下单接口 请求XML数据：" + xml);
			//上面的xml格式 + "<sign>" + mysign + "</sign>"
			//调用统一下单接口，并接受返回的结果
			String result = PayUtil.httpRequest(WxConfig.pay_url, "POST", xml);
			//String result = HttpClientUtil.doPostJson(WxConfig.pay_url, xml);//先测试测试下签名

			System.out.println("调试模式_统一下单接口 返回XML数据：" + result);

			// 将解析结果存储在HashMap中
			Map map = PayUtil.doXMLParse(result);

			String return_code = (String) map.get("return_code");//返回状态码

			//返回给移动端需要的参数
			Map<String, Object> response = new HashMap<String, Object>();
			if(return_code == "SUCCESS" || return_code.equals(return_code)){
				// 业务结果
				String prepay_id = (String) map.get("prepay_id");//返回的预付单信息
				response.put("nonceStr", nonce_str);
				response.put("package", "prepay_id=" + prepay_id);
				Long timeStamp = System.currentTimeMillis() / 1000;
				response.put("timeStamp", timeStamp + "");//这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误

				String stringSignTemp = "appId=" + WxConfig.APPID + "&nonceStr=" + nonce_str + "&package=prepay_id=" + prepay_id+ "&signType=" + WxConfig.SIGNTYPE + "&timeStamp=" + timeStamp;
				//再次签名，这个签名用于小程序端调用wx.requesetPayment方法
				String paySign = PayUtil.sign(stringSignTemp, WxConfig.MACH_KEY, "utf-8").toUpperCase();
				log.info("=======================第二次签名：" + paySign + "=====================");

				response.put("paySign", paySign);

				//更新订单信息
				//业务逻辑代码

			}
			/*Object order = map.get("out_trade_no");
			String jsonOrder = JSONObject.toJSON(order).toString();*/

			response.put("appid", WxConfig.APPID);

			json.setSuccess(true);
			json.setData(orderNo);
			json.setData(response);
		}catch(Exception e){
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("发起失败");
		}
		return json;
	}

	/**
	 * @Description:微信支付回调
	 */
	@RequestMapping(value="notify")
	public String notify(HttpServletRequest request, HttpServletResponse response) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream()));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while((line = br.readLine())!=null){
			sb.append(line);
		}
		br.close();
		//sb为微信返回的xml
		String notityXml = sb.toString();
		String resXml = "";
		System.out.println("接收到的报文：" + notityXml);

		Map map = PayUtil.doXMLParse(notityXml);


		String orderNo = map.get("out_trade_no").toString();//订单编号

		String returnCode = (String) map.get("return_code");
		if("SUCCESS".equals(returnCode)){ //验证签名是否正确
			Map<String, String> validParams = PayUtil.paraFilter(map);//回调验签时需要去除sign和空值参数
			String prestr = PayUtil.createLinkString(validParams);
			String mysign = PayUtil.sign(prestr, WxConfig.MACH_KEY, "utf-8").toUpperCase();
			log.info("=======================mysign返回值：" + mysign + "=====================");


			if(mysign.equals((String)map.get("sign"))){
				//**此处添加自己的业务逻辑代码start**//*

				//注意要判断微信支付重复回调，支付成功后微信会重复的进行回调

				//**此处添加自己的业务逻辑代码end**//*
				//通知微信服务器已经支付成功
				resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
						+ "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
				log.info("=======================结果为：" + "success" + "=====================");
			}
		}else{
			resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
					+ "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";

				return "";
		}
		System.out.println(resXml);
		System.out.println("微信支付回调数据结束");
		Map<String,String> m = XMLUtil.doXMLParse(resXml);
		log.info("xml中的数据:"+m);
		BufferedOutputStream out = new BufferedOutputStream(
				response.getOutputStream());
		out.write(resXml.getBytes());
		out.flush();
		out.close();
		log.info("来到这里的方法");
		log.info("看看xml格式",resXml);

		return orderNo;
	}

	/**
	 * 获取用户的信息
	 * @param user openID
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "userOpenid",method = RequestMethod.GET)
	public String findUserOpenId(User user, HttpSession session){
		List<Object> list = new ArrayList<>();
		User users = userService.findByOpenId(user.getOpenId());
		session.setAttribute("userOpenId",users);
		list.add(users);
		String listjson = JSONObject.toJSON(list).toString();
		return listjson;
	}


    /**
     * 微信退工能
     * @return
     */
    @RequestMapping()
	@ResponseBody
    public  String resume(){


        return null;

    }





















}
