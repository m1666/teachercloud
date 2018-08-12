package com.teachercloud.service;

import com.teachercloud.common.ServerResponse;

import java.util.List;

/**
 * User: mann
 * DateTime: 2018/7/28 9:56
 */
public interface IUserClassService {

    /**
     * 通过用户ID获取班级ID
     *
     * @param userId
     * @return
     */
    ServerResponse<List<Long>> getClassId(Integer userId);

    /**
     * 通过班级ID获取用户ID
     *
     * @param classId
     * @return
     */
    ServerResponse<List<Integer>> getUserId(Long classId);
}
