package com.yl.trend;

import com.yl.trend.redis.RedisHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrendBoot5ApplicationTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisHelper redisHelper;



    @Test
    public void contextLoads() throws Exception{


//        stringRedisTemplate.opsForValue().set("aaa","111");
//        Assert.assertEquals("111",stringRedisTemplate.opsForValue().get("aaa"));
//        System.out.println(stringRedisTemplate.opsForValue().get("aaa"));
       /* User user = new User();
        user.setNickName("猪八戒");
        user.setCity("山东");

        redisHelper.valuePut("user",user.getNickName());
        System.out.println("第一次获取到的值是这个 看看哦=========================="+redisHelper.getValue("user"));
        redisHelper.expirse("user",10, TimeUnit.SECONDS);

        Thread.sleep(1000*10+1);
        //redisHelper.remove("user");
        if (!"".equals(redisHelper.getValue("user"))){
            System.out.println("我要获取里面的值是多少==================="+redisHelper.getValue("user"));
        }else{
            System.out.println("你查询的数据不存在!");
        }*/

    }

}

