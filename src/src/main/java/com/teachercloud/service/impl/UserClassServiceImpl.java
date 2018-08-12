package com.teachercloud.service.impl;

import com.teachercloud.common.ServerResponse;
import com.teachercloud.dao.UserClassMapper;
import com.teachercloud.service.IUserClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: mann
 * DateTime: 2018/7/28 9:57
 */
@Service("iUserClassService")
public class UserClassServiceImpl implements IUserClassService {

    @Autowired
    private UserClassMapper userClassMapper;

    @Override
    public ServerResponse<List<Long>> getClassId(Integer userId) {
        List<Long> classIds = userClassMapper.getClassIdByUserId(userId);
        if (classIds.size() <= 0) {
            return ServerResponse.createByErrorMessage("该用户无班级对应!");
        }
        return ServerResponse.createBySuccess(classIds);
    }

    @Override
    public ServerResponse<List<Integer>> getUserId(Long classId) {
        List<Integer> userIds = userClassMapper.getUserIdByClassId(classId);
        if (userIds.size() <= 0) {
            return ServerResponse.createByErrorMessage("该班级没有对应的学生信息");
        }
        return ServerResponse.createBySuccess(userIds);
    }
}
