package com.teachercloud.dao;

import com.teachercloud.pojo.SignItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SignItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SignItem record);

    int insertSelective(SignItem record);

    List<SignItem> select(@Param("signId") Long signId);

    int updateByPrimaryKeySelective(SignItem record);

    int updateByPrimaryKey(SignItem record);

    int checkUserIp(@Param("signId") Long signId, @Param("userIp") String userIp);

    SignItem selectSignItems(@Param("signId") Long signId, @Param("userId") Integer userId);
}