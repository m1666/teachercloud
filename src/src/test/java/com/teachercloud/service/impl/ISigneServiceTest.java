package com.teachercloud.service.impl;

import com.teachercloud.pojo.Sign;
import com.teachercloud.pojo.SignItem;
import com.teachercloud.service.impl.SigneServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * @author 侯宇飞
 * 日期: 2018-07-21
 * 时间: 21:15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class ISigneServiceTest {

    @Autowired
    SigneServiceImpl signeService;
    //学生开始签到
    @Test
    public void signeCount() {
        Long signId = 18080219212L;
        Integer userId = 3;
        String userIp = "test3";
        Date signTime = new Date();

//Long signId,String userIp,Date signTime

        System.out.print(signeService.signeCount(signId,userId,userIp,signTime));
    }
    //开启签到
    @Test
    public void startSign() {
        //Integer userId,Integer courseId
        Integer courseId = 1004;
        Integer userId = 2;
        System.out.print(signeService.startSign(userId,courseId));
    }


    //手动关闭签到
    @Test
    public void endSign(){

        System.out.print(signeService.endSign(18080219212L));

    }
}