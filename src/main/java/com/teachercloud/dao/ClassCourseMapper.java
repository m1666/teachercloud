package com.teachercloud.dao;

import com.teachercloud.pojo.ClassCourse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClassCourseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ClassCourse record);

    int insertSelective(ClassCourse record);

    ClassCourse selectByPrimaryKey(@Param("courseId")Integer courseId,@Param("classId")Long classId);

    int updateByPrimaryKeySelective(ClassCourse record);

    int updateByPrimaryKey(ClassCourse record);

    /**
     * 根据班级ID查询课程ID
     *
     * @param classId
     * @return
     */
    List<Integer> selectCourseIdByClassId(Long classId);

    /**
     * 批量更新班级开课状态
     *
     * @param ids
     * @param status
     * @return
     */
    int updateClassCourseStatusBybatch(@Param("ids") int[] ids, @Param("status") int status);


}