package com.teachercloud.dao;

import com.teachercloud.pojo.Course;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseMapper {
    int deleteByPrimaryKey(Integer courseId);

    int insert(Course record);

    int insertSelective(Course record);

    Course selectByPrimaryKey(Integer courseId);

    int updateByPrimaryKeySelective(Course record);

    int updateByPrimaryKey(Course record);

    /**
     * 显示所有课程，可进行模糊查询
     *
     * @param keyword
     * @return
     */
    List<Course> selectAll(String keyword);

    /**
     * 根据班级编号查询课程，可进行模糊查询
     *
     * @param courseId
     * @param keyword
     * @return
     */
    List<Course> selectCourseByCourseId(@Param("courseId") List<Integer> courseId, @Param("keyword") String keyword);

    /**
     * 检测课程ID是否存在
     *
     * @param courseId
     * @return
     */
    int checkCourseId(Integer courseId);

    /**
     * 批量删除
     *
     * @param courseId
     * @return
     */
    int deleteCourseBybatch(@Param("courseId") int[] courseId);

    /**
     * 批量修改状态
     *
     * @param courseId
     * @param status
     * @return
     */
    int updateCourseStatusBybatch(@Param("courseId") int[] courseId, @Param("status") Integer status);

}