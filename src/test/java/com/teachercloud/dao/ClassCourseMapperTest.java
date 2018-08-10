package com.teachercloud.dao;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * User: mann
 * DateTime: 2018/7/30 17:07
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-*.xml")
public class ClassCourseMapperTest {

    @Autowired
    private ClassCourseMapper classCourseMapper;

    @Test
    public void selectCourseIdByClassId() {
        System.out.println(classCourseMapper.selectCourseIdByClassId(5163002641L).size());
    }

}