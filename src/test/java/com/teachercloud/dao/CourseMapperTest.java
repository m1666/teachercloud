package com.teachercloud.dao;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * User: mann
 * DateTime: 2018/7/31 9:10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-*.xml")
public class CourseMapperTest {

    @Autowired
    private CourseMapper courseMapper;

    @Test
    public void selectCourseByCourseId() {
        List<Integer> a = Lists.newArrayList();
        a.add(1001);
        a.add(1002);
        System.out.println(courseMapper.selectCourseByCourseId(a, "%%"));
    }
}