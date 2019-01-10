package com.yl.trend.entity;

import lombok.Data;

@Data
public class Prize {
    /**
	* 积分商品的编号
	*/
    private Integer prizeId;

    /**
	* 积分商品的名称
	*/
    private String prizeName;

    /**
	* 中奖的时间
	*/
    private String prizeTime;

    /**
	* 是否已经发放
	*/
    private Integer grantMark;

    /**
	* 奖品等级
	*/
    private Integer prizeWeight;

    /**
	* 发放数量
	*/
    private Integer grantAmount;

    /**
	* 发放的时间
	*/
    private String grantTime;

    /**
	* 关联的用户的奖品
	*/
    private Integer uid;


}