package com.yl.trend.controller;

import com.yl.trend.entity.Prize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@Controller
@RequestMapping("/prize/")
public class PrizeController {



    @RequestMapping(value = "controllerprize",method = RequestMethod.GET)
    public  String  simplePrize(Prize prize){

        return null;
    }




}
