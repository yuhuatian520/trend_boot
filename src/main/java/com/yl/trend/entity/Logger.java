package com.yl.trend.entity;

import lombok.Data;

/**
* Created by Mybatis Generator 2018/12/26
*/
@Data
public class Logger {
    /**
	* 交易编号
	*/
    private Integer transactionId;

    /**
	* 账户充值
	*/
    private String accountRecharge;

    /**
	* 充值的类型
	*/
    private Integer rechargeType;

    /**
	* 账户支出
	*/
    private String accountExpend;

    /**
	* 交易结束时间
	*/
    private String transactionEndtime;

    /**
	* 交易是否成功
	*/
    private String transactionMark;

    /**
	* 关联用户主键
	*/
    private Integer uid;


}