package com.teachercloud.service.impl;

import com.teachercloud.common.ServerResponse;
import com.teachercloud.dao.ClassCourseMapper;
import com.teachercloud.pojo.Course;
import com.teachercloud.service.IClassCourseService;
import com.teachercloud.service.ICourseService;
import com.teachercloud.service.IUserClassService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author hekai
 * @date 2018/8/2 0002 上午 10:59
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-*.xml")
public class ClassCourseServiceImplTest {

    @Autowired
    private IClassCourseService iClassCourseService;
    @Autowired
    private ClassCourseMapper classCourseMapper;
    @Autowired
    private ICourseService iCourseService;
    @Autowired
    private IUserClassService iUserClassService;
    @Test
    public void updateClassCourseStatus() {
//        String ids = "4,5";
//        iClassCourseService.updateClassCourseStatus(ids,1);
//        List<Long> classIds = new ArrayList<Long>();
////        classIds.add(5163002641L);
//        List<Integer> ids = classCourseMapper.selectCourseIdByAllClassId(classId);
//        List<Course> courseList= iCourseService.courseListByUser(classId).getData();
        List<Long>classIds  = iCourseService.getClassId(2);
        //Long classId = classIds.get(0);
        //有错
       List<Integer> ids =classCourseMapper.selectCourseIdByAllClassId(classIds);

       List<Course> courseList = iCourseService.courseListByUser(classIds).getData();
        System.out.println("====courseList======="+courseList.get(0).getName());
    }
}