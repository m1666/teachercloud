package com.teachercloud.dao;

import com.teachercloud.util.StringArrayUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @author hekai
 * @date 2018/8/4 0004 下午 19:58
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-*.xml")
public class ClassItemMapperTest {

    @Autowired
    private ClassItemMapper classItemMapper;

    @Test
    public void deleteClass() {
        String a = "5163002641,5163002642";
        int rowCount = classItemMapper.deleteClass(StringArrayUtil.stringToLong(a));
        System.out.println(rowCount);
    }
}