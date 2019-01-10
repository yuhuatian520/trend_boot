package com.yl.trend.service.impl;/*
package com.yl.trend.service.impl;

import com.yl.trend.entity.User;
import com.yl.trend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
@Service
public class ServiceImpl {

    @Autowired
    private UserMapper userMapper;
    public void selectUserByOpenId(String openid, String avatarurl, String nickname, Integer gender, String country,
                                   String province, String city) {
        //String userip = country+province+city;//用户地址
        String usersex = "";
        User user = userMapper.selectByOppenId(openid);
        if(user!=null){//如果用户不等于空
            if(user.getNickname().equals(nickname)&&user.getAvatarurl().equals(avatarurl)&&user.getGender().equals(gender)&&user.getCountry().equals(country)&&user.getProvince().equals(province)&&user.getCity().equals(city)){
                System.out.println("数据暂未修改");
                return;

            }else{
                try {
                    int i = userMapper.updateByPrimaryKeySelective(user);
                    if (i>0){
                        System.out.println("修改数据成功");
                        return;
                    }

                } catch (Exception e) {
                    System.out.println("修改数据失败");
                    e.printStackTrace();
                }

            }
        }else{//用户为空进行
            try {
               // String phone = "";
                String createtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

                int mark = userMapper.insertSelective(user);
                if (mark>0){
                    System.out.println("添加用户成功");
                }
            } catch (Exception e) {
                System.out.println("添加用户失败");
                e.printStackTrace();
            }
        }
    }



}
*/
