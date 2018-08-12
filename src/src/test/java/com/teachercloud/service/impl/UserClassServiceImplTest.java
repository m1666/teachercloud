package com.teachercloud.service.impl;

import com.google.common.collect.Lists;
import com.teachercloud.dao.UserClassMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * User: mann
 * DateTime: 2018/7/30 11:18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-*.xml")
public class UserClassServiceImplTest {

    @Autowired
    private UserClassMapper userClassMapper;

    @Test
    public void getClassId() {
        List<Long> classIds;
        classIds = userClassMapper.getClassIdByUserId(2);
        if (classIds.size() <= 0) {
            System.out.println("该用户无班级对应!");
        }
        System.out.println(classIds.size());
    }
}