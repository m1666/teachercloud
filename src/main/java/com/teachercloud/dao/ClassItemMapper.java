package com.teachercloud.dao;

import com.teachercloud.pojo.ClassItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClassItemMapper {
    int deleteByPrimaryKey(Long classId);

    int insert(ClassItem record);

    int insertSelective(ClassItem record);

    ClassItem selectByPrimaryKey(Long classId);

    int updateByPrimaryKeySelective(ClassItem record);

    int updateByPrimaryKey(ClassItem record);

    /**
     * 根据班号查询班级详情
     * @param classId
     * @return
     */
    List<ClassItem> getClassItemByClassId(List<Long> classId);


    /**
     * 批量删除
     * @param classId
     * @return
     */
    int deleteClass(@Param("classId") Long[] classId);

    /**
     * 查询所有班级
     * @return
     */
    List<ClassItem> queryAll();

    /**
     * 批量更新班级开课状态
     *
     * @param calssIds
     * @param status
     * @return
     */
    int updateClassItemCourseStatusBybatch(@Param("calssIds") Long[] calssIds, @Param("status") int status);

}