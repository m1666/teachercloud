package com.teachercloud.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.teachercloud.common.Const;
import com.teachercloud.common.ResponseCode;
import com.teachercloud.common.ServerResponse;
import com.teachercloud.dao.ClassItemMapper;
import com.teachercloud.pojo.ClassItem;
import com.teachercloud.pojo.Course;
import com.teachercloud.service.IClassItemService;
import com.teachercloud.util.StringArrayUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.teachercloud.util.StringArrayUtil.stringToInteger;
import static com.teachercloud.util.StringArrayUtil.stringToLong;

/**
 * User: mann
 * DateTime: 2018/7/29 21:18
 */
@Service("iClassItemService")
public class ClassItemServiceImpl implements IClassItemService {

    @Autowired
    private ClassItemMapper classItemMapper;

    @Override
    public ServerResponse<List<ClassItem>> getClassItems(List<Long> classIds) {
        if (classIds.size() <= 0) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        return ServerResponse.createBySuccess(classItemMapper.getClassItemByClassId(classIds));
    }


    /**
     * 更新班级信息
     * @param classItem
     * @return
     */
    @Override
    public ServerResponse<ClassItem> updateClassItem(ClassItem classItem){
        ClassItem updateclass = new ClassItem();
        updateclass.setClassId(classItem.getClassId());
        updateclass.setClassName(classItem.getClassName());
        updateclass.setStatus(classItem.getStatus());
        if(updateclass.getClassId() == null){
            return ServerResponse.createByErrorMessage("班级ID不能为空");
        }
        int updateCount = classItemMapper.updateByPrimaryKeySelective(classItem);
        if(updateCount > 0){
            return ServerResponse.createBySuccess("更新班级信息成功！",updateclass);
        }
        return ServerResponse.createByErrorMessage("更新班级信息失败！");
    }

    @Override
    public ServerResponse<String> deleteClassItem(String classIds){
        if(classIds == null){
            return ServerResponse.createByErrorMessage("未输入班级号！");
        }
        int rowCount = classItemMapper.deleteClass(stringToLong(classIds));
        if(rowCount > 0){
            return ServerResponse.createBySuccess("删除班级成功！");
        }
        return ServerResponse.createByErrorMessage("删除班级失败！");
    }


    @Override
    public ServerResponse<PageInfo> getAllClassItems(Integer pageNum, Integer pageSize) {
        List<ClassItem> list = classItemMapper.queryAll();
        if (list == null){
            return ServerResponse.createByErrorMessage("未知错误");
        }
        PageHelper.startPage(pageNum, pageSize);
        PageInfo pageInfo = new PageInfo(list);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse<String> updateClassItemCourseStatus(String classId, int status) {
        if (StringUtils.isBlank(classId)) {
            return ServerResponse.createByErrorMessage("未选定课程!");
        }
        int resultCount = classItemMapper.updateClassItemCourseStatusBybatch(StringArrayUtil.stringToLong(classId), status);
        if (resultCount > 0) {
            return ServerResponse.createBySuccessMessage("修改指定课程状态成功!");
        }
        return ServerResponse.createByErrorMessage("修改指定课程状态失败!");
    }

}
