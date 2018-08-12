package com.teachercloud.service.impl;

import com.teachercloud.dao.CourseMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * User: mann
 * DateTime: 2018/7/30 16:06
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-*.xml")
public class iCourseServiceImplTest {

    @Autowired
    private CourseMapper courseMapper;

    @Test
    public void selectAllCourse() {
        System.out.println(courseMapper.selectAll("%%").size());
    }

    @Test
    public void getCourseStudent() {
    }
}