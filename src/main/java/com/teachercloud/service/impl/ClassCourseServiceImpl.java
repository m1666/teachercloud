package com.teachercloud.service.impl;

import com.teachercloud.common.ResponseCode;
import com.teachercloud.common.ServerResponse;
import com.teachercloud.dao.ClassCourseMapper;
import com.teachercloud.pojo.ClassCourse;
import com.teachercloud.service.IClassCourseService;
import com.teachercloud.util.DateTimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import static com.teachercloud.util.StringArrayUtil.stringToInteger;

/**
 * @Author: mann
 * @Date: 2018/8/1 8:16
 */
@Service("iClassCourseService")
public class ClassCourseServiceImpl implements IClassCourseService {

    @Autowired
    private ClassCourseMapper classCourseMapper;

    @Override
    public ServerResponse<String> updateClassCourseStatus(String ids, int status) {
        if (StringUtils.isBlank(ids)) {
            return ServerResponse.createByErrorMessage("未选定课程!");
        }
        int resultCount = classCourseMapper.updateClassCourseStatusBybatch(stringToInteger(ids), status);
        if (resultCount > 0) {
            return ServerResponse.createBySuccessMessage("修改指定课程状态成功!");
        }
        return ServerResponse.createByErrorMessage("修改指定课程状态失败!");
    }



    /**
     * 添加课程到班级
     * @param courseId
     * @param classId
     * @param createTime
     * @param endTime
     * @return
     * @Author hekai
     * @Time 2018/8/1
     */
    @Override
    public ServerResponse<ClassCourse> insertClassCourse(Integer courseId, Long classId,String createTime,String endTime){
        if (courseId == null || classId == null) {
            return ServerResponse.createByErrorMessage("未选定课程或班级!");
        }
        ClassCourse classCourse = new ClassCourse();
        classCourse.setCourseId(courseId);
        classCourse.setClassId(classId);
        classCourse.setCreateTime(DateTimeUtil.strToDate(createTime));
        classCourse.setEndTime(DateTimeUtil.strToDate(endTime));
        classCourse.setStatus(0);
        int resultCount = classCourseMapper.insertSelective(classCourse);
        if (resultCount > 0) {
            return ServerResponse.createBySuccess("成功增加课程到班级！",classCourse);
        }
        return ServerResponse.createByErrorMessage("添加失败！");
    }




    /**
     * 根据id进行删除
     * @param id
     * @return
     */
    @Override
    public ServerResponse deleteClass(Integer id) {
        if(id == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        return  ServerResponse.createBySuccess("删除数据成功！",classCourseMapper.deleteByPrimaryKey(id));
    }

    @Override
    public ServerResponse<ClassCourse> selectdetail(Integer courseId, Long classId){
        //空值判断
        if (courseId == null || classId == null){
            return ServerResponse.createByErrorMessage("没有输入课程id或班级id");
        }
        ClassCourse classCourse = classCourseMapper.selectByPrimaryKey(courseId,classId);
        if (classCourse == null){
            return ServerResponse.createByErrorMessage("没有该条记录");
        }else {
            return ServerResponse.createBySuccess("查询成功",classCourse);
        }

    }

}
