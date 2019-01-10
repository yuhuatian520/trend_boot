package com.yl.trend.controller;

import com.alibaba.fastjson.JSONObject;
import com.yl.trend.entity.Logger;
import com.yl.trend.entity.User;
import com.yl.trend.service.LoggerService;
import com.yl.trend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/logger/")
public class LoggerController {

    @Autowired
    private LoggerService loggerService;

    @Autowired
    private UserService userService;

    /**
     * 用户的记录数据 测试成功!
     * @param openId
     * @return
     */
    @RequestMapping(value = "userloggers",method = RequestMethod.GET)
    @ResponseBody
    public String getUserLoggers(String openId){
        List<User> loggers = userService.getUserLoggers(openId);
        String jsonLogger = JSONObject.toJSON(loggers).toString();
        return jsonLogger;
    }

    /**
     * logger/userloggersback
     * @param openId
     * @return
     */
    @RequestMapping(value = "userloggersback",method = RequestMethod.GET)
    public String getUserLoggersback(String openId, Model model){
        List<Logger> loggers = loggerService.getUserLoggers(openId);
        System.out.println("记录的数据+"+loggers);
            if (loggers!=null){
                model.addAttribute("loggers",loggers);
                return "/back/userinfo/userinfo_logger";
            }
            return "/back/userinfo/userinfo_list";
    }





}
