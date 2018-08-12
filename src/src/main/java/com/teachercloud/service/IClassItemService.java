package com.teachercloud.service;

import com.github.pagehelper.PageInfo;
import com.teachercloud.common.ServerResponse;
import com.teachercloud.pojo.ClassItem;

import java.util.List;

/**
 * User: mann
 * DateTime: 2018/7/29 21:18
 */
public interface IClassItemService {

    /**
     * 根据班号查询班级详情
     *
     * @param classIds
     * @return
     */
    ServerResponse<List<ClassItem>> getClassItems(List<Long> classIds);

    /**
     * 修改班级信息
     * @param classItem
     * @return
     */
    ServerResponse<ClassItem> updateClassItem(ClassItem classItem);

    /**
     * 根据班级号删除班级
     * @param classIds
     * @return
     */
    ServerResponse<String> deleteClassItem(String classIds);

    ServerResponse<PageInfo> getAllClassItems(Integer pagesize, Integer startrow);

    /**
     * 批量更新班级开课状态
     *
     * @param classId
     * @param status
     * @return
     */
    ServerResponse<String> updateClassItemCourseStatus(String classId, int status);
}
