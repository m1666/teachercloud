package com.teachercloud.service;

import com.teachercloud.common.ServerResponse;
import com.teachercloud.pojo.ClassCourse;
import com.teachercloud.pojo.ClassItem;

/**
 * @Author: mann
 * @Date: 2018/8/1 8:16
 */
public interface IClassCourseService {

    /**
     * 批量更新班级开课状态
     *
     * @param ids
     * @param status
     * @return
     */
    ServerResponse<String> updateClassCourseStatus(String ids, int status);


    /**
     * 增加课程到班级
     * @param courseId
     * @param classId
     * @param createTime
     * @param endTime
     * @return
     */
    ServerResponse<ClassCourse> insertClassCourse(Integer courseId, Long classId, String createTime, String endTime);

    /**
     * 删除班级和课程的关联
     * @param id
     * @return
     */
    ServerResponse deleteClass(Integer id);


    /**
     * 根据课程号和班级号查询信息
     * @param courseId
     * @param classId
     * @return
     */
    ServerResponse<ClassCourse> selectdetail(Integer courseId,Long classId);

}
