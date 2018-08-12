package com.teachercloud.dao;

import com.teachercloud.pojo.Sign;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface SignMapper {
    int deleteByPrimaryKey(Long signId);

    int insert(Sign record);

    int insertSelective(Sign record);

    Sign selectByPrimaryKey(Long signId);

    int updateByPrimaryKeySelective(Sign record);

    int updateByPrimaryKey(Long signId);

    //手动关闭签到结束

    int setEndTime(@Param("signId") Long signId, @Param("endTime") Date endTime);


    /**
     * 获取最新签到表
     * @return
     */
    Sign selectByMaxTime(Integer userId);



    /**
     * 查询是否有签到记录签到
     * @param userId
     * @param courseId
     * @return
     */
    List<Sign> querySign(@Param("userId") Integer userId, @Param("courseId") Long courseId);
}

