package com.yl.trend.entity;

import com.yl.trend.utils.BaseBean;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
* Created by Mybatis Generator 2018/12/04
*/
@Data
public class User extends BaseBean implements Serializable {
    private Integer uid;

    private String openId;

    private String nickName;
    //1表示男的
    private Integer gender;

    private String city;

    private String province;

    private String country;
    //微信头像
    private String avatarUrl;

    private BigDecimal uwallet;

    private Integer uintegral;

    private Long invitecode;

    private Integer isvip;
    //关联的模板名称
    private List<Resume> resumes;


   private List<Logger> loggers;



}