package com.teachercloud.dao;

import com.teachercloud.pojo.UserClass;

import java.util.List;

public interface UserClassMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserClass record);

    int insertSelective(UserClass record);

    UserClass selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserClass record);

    int updateByPrimaryKey(UserClass record);

    /**
     * 根据用户ID查询用户所在班级
     *
     * @param userId
     * @return
     */
    List<Long> getClassIdByUserId(Integer userId);

    /**
     * 根据班级ID查询该班用户ID
     *
     * @param classId
     * @return
     */
    List<Integer> getUserIdByClassId(Long classId);
}