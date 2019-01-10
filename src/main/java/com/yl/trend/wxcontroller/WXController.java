package com.yl.trend.wxcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wechat")
public class WXController {


   // @Autowired
   /* private WxMpService wxMpService;
    @Autowired
    private WxMpService wxOpenService;
    @Autowired
    private ProjectUrlConfig projectUrlConfig;
    @Autowired
    private UserService userService;



    *//* *
     * 微信网页授权流程:
     * 1. 用户同意授权,获取 code
     * 2. 通过 code 换取网页授权 access_token
     * 3. 使用获取到的 access_token 和 openid 拉取用户信息
     *
     * 访问http://sqmax.natapp1.cc/sell/wechat/authorize?returnUrl=http://www.imooc.com，
     * 最终将会跳转到这个链接：http://www.imooc.com?openid={openid}*//*


    @GetMapping("/authorize")
    public String getAccessToken(@RequestParam("returnUrl") String returnUrl) {

        String url = projectUrlConfig.getWechatMpAuthorize() + "/wechat/userInfo";
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, null);
        //log.info("【微信网页授权】获取code,redirectUrl={}",redirectUrl);
        //发出获取全部信息的授权链接
        return "redirect:" + redirectUrl; //重定向到下面一个方法
    }

    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code,
                           @RequestParam("state") String returnUrl) {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            //log.error("【微信网页授权】,{}",e);
            //throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(),e.getError().getErrorMsg());
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();

        User user = userService.findByOpenId(openId);

        if (user.getOpenid() == null) {
            //说明数据库中没有该角色,先获取令牌和用户的openID,发送给微信获取用户信息
            String userMessageUrl = UserInfoUtils.getUserMessage(wxMpOAuth2AccessToken.getAccessToken(), openId);
            String userMessageResponse = HttpsUtils.httpsRequestToString(userMessageUrl, "GET", null);

            User userBean = null;

            JSONObject jsonObject = JSON.parseObject(userMessageResponse);
            userBean = jsonObject.toJavaObject(userBean.getClass());
            if (userBean == null) {
                //获取用户信息失败抛出异常
            }

            userService.addUserInfos(userBean);
            return "redirect:" + returnUrl + "?openid=" + userBean.getOpenid();
        }
            String s = "";

            //log.info("【微信网页授权】获取openid,returnUrl={}",returnUrl);
            return "redirect:" + returnUrl + "?openid=" + openId;
    }


    //微信登陆
    @GetMapping("/qrAuthorize")
    public String qrAuthorize(@RequestParam("returnUrl") String returnUrl){
        String url=projectUrlConfig.getWechatOpenAuthorize()+"/wechat/userInfo";
        String redirectUrl=wxOpenService.buildQrConnectUrl(url,WxConsts.OAuth2Scope.SNSAPI_USERINFO,null);
        return "redirect:"+redirectUrl;
    }



    @GetMapping("/qrUserInfo")
    public String qrUserInfo(@RequestParam("code") String code,
                             @RequestParam("state") String returnUrl){
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken=new WxMpOAuth2AccessToken();
        try{
            wxMpOAuth2AccessToken=wxOpenService.oauth2getAccessToken(code);
        }catch (WxErrorException e){
            //log.error("【微信网页】{}",e);
            //throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(),e.getError().getErrorMsg());
        }
        //log.info("wxMpOAuth2AccessToken={}", JsonUtil.toJson(wxMpOAuth2AccessToken));
        String openId=wxMpOAuth2AccessToken.getOpenId();
        return "redirect:"+returnUrl+"?openid="+openId;
    }

        @Autowired
        private WeChatMessageService service;

        @RequestMapping(value="/getMessage", method= RequestMethod.GET)
        @ResponseBody
        public String getMessage(WeChatMessage message) {

            return service.checkSignature(message);
        }
*/


    }



































