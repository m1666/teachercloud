package com.teachercloud.dao;

import com.teachercloud.pojo.FileItem;

public interface FileItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FileItem record);

    int insertSelective(FileItem record);

    FileItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FileItem record);

    int updateByPrimaryKey(FileItem record);
}