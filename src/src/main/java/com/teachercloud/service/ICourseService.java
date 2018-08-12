package com.teachercloud.service;

import com.github.pagehelper.PageInfo;
import com.teachercloud.common.ServerResponse;
import com.teachercloud.pojo.Course;

import java.util.List;

/**
 * User: mann
 * DateTime: 2018/7/30 15:44
 */
public interface ICourseService {

    /**
     * 展示所有课程，进行模糊查询
     *
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    ServerResponse<PageInfo> selectAllCourse(String keyword, String column, int pageNum, int pageSize);

    /**
     * 学生根据关键字模糊查询本班所有课程
     *
     * @param classId
     * @param keyword
     * @return
     */
    ServerResponse<List<Course>> getCourseStudent(Long classId, String keyword);

    /**
     * 检查课程ID是否存在
     *
     * @param courseId
     * @return
     */
    ServerResponse<String> checkCoursId(Integer courseId);

    /**
     * 管理员添加课程
     *
     * @param course
     * @return
     */
    ServerResponse<String> createCourse(Course course);

    /**
     * 批量删除课程
     *
     * @param ids
     * @return
     */
    ServerResponse<String> deleteCourse(String ids);

    /**
     * 批量修改课程状态
     *
     * @param ids
     * @param status
     * @return
     */
    ServerResponse<String> updateCourseStatus(String ids, Integer status);

    /**
     * 根据课程ID查询课程详情
     *
     * @param courseId
     * @return
     */
    ServerResponse<Course> selectCourseInfo(Integer courseId);

    /**
     * 更新课程信息详情
     *
     * @param course
     * @return
     */
    ServerResponse<Course> updateCourseInfo(Course course);


    /**
     * 根据班级Id查询相关课程
     * @param classId
     * @return
     */
    ServerResponse<List<Course>> courseListByUser(List<Long> classId);

    List<Long> getClassId(Integer userId);
}
