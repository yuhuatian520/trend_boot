package com.yl.trend.entity;

import com.yl.trend.utils.BaseBean;
import lombok.Data;

import java.math.BigDecimal;

/**
* Created by Mybatis Generator 2018/11/23
*/
@Data
public class Resume  extends BaseBean {
    private Integer rid;

    private Integer uid;

    private String sname;

    private String rname;

    private String raddress;

    private Integer sid;

    private String simage;

    private BigDecimal scost;

    private Species species;

    private Integer vip;



}