package com.yl.trend.controller;

import com.alibaba.fastjson.JSONObject;
import com.yl.trend.entity.Logger;
import com.yl.trend.entity.User;
import com.yl.trend.service.LoggerService;
import com.yl.trend.service.ResumeService;
import com.yl.trend.service.UserService;
import com.yl.trend.utils.LoggerUtils;
import com.yl.trend.utils.PageBean;
import com.yl.trend.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
@Controller
@RequestMapping("/wechat/user/")
@Slf4j
public class UserContoller {

    @Autowired
    private UserService userService;

    @Autowired
    private ResumeService resumeService;
    @Autowired
    private LoggerService loggerService;

    /**
     * 把获取到的用户全部查询出来
     * @param user 准备迷糊查
     * @param page 分页查
     * @return
     */
    @RequestMapping("userlist")
    public String userListView(User user, Model model,Integer page){
        PageBean<User> pageBean = userService.getUserList(user, page);
        model.addAttribute("pageBean", pageBean);
        model.addAttribute("user",user);
        System.out.println("**************************"+user);
        return "/back/userinfo/userinfo_list";
    }

    /**
     * 加载修改容易复制用户信息
     * @return
     */
    @RequestMapping("loadupdate")
    public String loaddUpdate(User user,Model model){
        User users = userService.findByOpenId(user.getOpenId());
            log.info("查询出来的信息:"+users);
        if (user!=null){
            model.addAttribute("users",users);
        }
        return "/back/userinfo/userinfo_update";
    }
    @RequestMapping("queryover")
    public String queryOver(Model model){
        model.addAttribute("info","正在为你呈现数据 稍等~~~~~");
        return "/back/userinfo/userinfo_info";
    }

    /**
     * 判断用户是否是VIP会员
     * @return 1是会员  0 不是会员
     */
    @RequestMapping("isUserVip")
    @ResponseBody
    public String isVipUser(String openId){
        if (!"".equals(openId)){
            User user = userService.findByOpenId(openId);
            if (user.getIsvip()==1){
                return "1";
            }
        }
        return "0";
    }



    /**
     * 用户充值金钱
     * @param openId
     * @return
     */
    @RequestMapping(value = "recharge",method = RequestMethod.GET)
    @ResponseBody
    public String revhargeMoney(String openId,BigDecimal uwallet){
        if (!"".equals(openId)){
            User user = userService.findByOpenId(openId);
            //将用户充值的钱放到自己的钱包里面 !

              user.setUwallet(uwallet);


            Boolean mark = userService.updateUserSelectTive(user);
            log.info("表示充值完成!");
                if (mark){
                    Logger logger = new Logger();
                        logger.setAccountRecharge(""+uwallet);
                        logger.setTransactionEndtime(TimeUtils.DateFormat());
                        logger.setUid(user.getUid());
                        logger.setTransactionMark(LoggerUtils.TRANSACTIONMARK_YES);
                        loggerService.addLoggerInfo(logger);
                        return JSONObject.toJSON(user).toString();
                }else{
                    log.info("充值未完成!");
                }
        }
        return null;
    }

    /**
     * 开通会员
     * @return
     */
    @RequestMapping(value = "vipout",method = RequestMethod.GET)
    @ResponseBody
    public String vipOut( String openId){

            User user = userService.findByOpenId(openId);
                if(user!=null){
                    user.setIsvip(1);
                }
            user.setUid(user.getUid());
        Boolean mark = userService.updateUserSelectTive(user);

            if (mark){
                log.info("开通会员成功");
                return "ok";
            }
        return "no";
    }

    /**
     * 用户的消费记录
     * @return
     */

    @RequestMapping(value = "usertransaction",method = RequestMethod.GET)
    public String transactionLogger(String openId,Model model){
        if (!"".equals(openId)){
            User user = userService.findByOpenId(openId);
            model.addAttribute("user",user);
            return "/back/userinfo/userinfo_logger";
        }
        return "/back/userinfo/userinfo_list";
    }

}
