package com.yl.trend.service.impl;

import com.yl.trend.entity.Prize;
import com.yl.trend.mapper.PrizeMapper;
import com.yl.trend.service.PrizeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class PrizeServiceImpl implements PrizeService {

    @Autowired
    private PrizeMapper prizeMapper;

    /**
     * 实现抽奖的逻辑
     * @param prizes
     * @return
     */
    @Override
    public int getPrizeIndex(List<Prize> prizes) {
        /**
         *
         * 根据Math.random()产生一个double型的随机数，判断每个奖品出现的概率
         * @param prizes
         * @return random：奖品列表prizes中的序列（prizes中的第random个就是抽中的奖品）
         */
            int random = -1;
            try{
                //计算总权重
                double sumWeight = 0;
                for(Prize p : prizes){
                    //sumWeight += p.getPrize_weight();
                    sumWeight+=p.getPrizeWeight();
                }

                //产生随机数
                double randomNumber;
                randomNumber = Math.random();

                //根据随机数在所有奖品分布的区域并确定所抽奖品
                double d1 = 0;
                double d2 = 0;
                for(int i=0;i<prizes.size();i++){
                    d2 += Double.parseDouble(String.valueOf(prizes.get(i).getPrizeWeight()))/sumWeight;
                    if(i==0){
                        d1 = 0;
                    }else{
                        d1 +=Double.parseDouble(String.valueOf(prizes.get(i-1).getPrizeWeight()))/sumWeight;
                    }
                    if(randomNumber >= d1 && randomNumber <= d2){
                        random = i;
                        break;
                    }
                }
            }catch(Exception e){
                System.out.println("生成抽奖随机数出错，出错原因：" +e.getMessage());
            }
            return random;
        }


}
