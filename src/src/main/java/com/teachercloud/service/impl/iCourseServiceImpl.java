package com.teachercloud.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.teachercloud.common.ResponseCode;
import com.teachercloud.common.ServerResponse;
import com.teachercloud.dao.ClassCourseMapper;
import com.teachercloud.dao.CourseMapper;
import com.teachercloud.dao.UserClassMapper;
import com.teachercloud.pojo.Course;
import com.teachercloud.service.ICourseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.teachercloud.util.StringArrayUtil.stringToInteger;

/**
 * User: mann
 * DateTime: 2018/7/30 15:44
 */
@Service("iCourseService")
public class iCourseServiceImpl implements ICourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private ClassCourseMapper classCourseMapper;

    @Autowired
    private UserClassMapper userClassMapper;

    @Override
    public ServerResponse<PageInfo> selectAllCourse(String keyword, String column, int pageNum, int pageSize) {
        if (keyword == null) {
            keyword = "";
        }
        keyword = new StringBuffer().append("%").append(keyword).append("%").toString();
        PageHelper.startPage(pageNum, pageSize);
        List<Course> courseList = courseMapper.selectAll(keyword);
        PageInfo pageInfo = new PageInfo(courseList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse<List<Course>> getCourseStudent(Long classId, String keyword) {
        if (classId <= 0) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        if (keyword == null) {
            keyword = "";
        }
        keyword = new StringBuffer().append("%").append(keyword).append("%").toString();
        List<Integer> ids = classCourseMapper.selectCourseIdByClassId(classId);
        List<Course> courseList = courseMapper.selectCourseByCourseId(ids, keyword);
        return ServerResponse.createBySuccess(courseList);
    }

    // backend

    @Override
    public ServerResponse<String> createCourse(Course course) {
        int resultCount = courseMapper.insert(course);
        if (resultCount <= 0) {
            return ServerResponse.createByErrorMessage("课程添加失败!");
        }
        return ServerResponse.createBySuccessMessage("课程添加成功!");
    }

    @Override
    public ServerResponse checkCoursId(Integer courseId) {
        int resultCount = courseMapper.checkCourseId(courseId);
        if (resultCount > 0) {
            return ServerResponse.createByError();
        }
        return ServerResponse.createBySuccess();
    }

    @Override
    public ServerResponse<String> deleteCourse(String ids) {
        if (StringUtils.isBlank(ids)) {
            return ServerResponse.createByErrorMessage("未选定课程!");
        }
        int resultCount = courseMapper.deleteCourseBybatch(stringToInteger(ids));
        if (resultCount > 0) {
            return ServerResponse.createBySuccessMessage("删除选定课程成功!");
        }
        return ServerResponse.createByErrorMessage("删除选定课程失败!");
    }

    @Override
    public ServerResponse<String> updateCourseStatus(String ids, Integer status) {
        if (StringUtils.isBlank(ids)) {
            return ServerResponse.createByErrorMessage("未选定课程!");
        }
        int resultCount = courseMapper.updateCourseStatusBybatch(stringToInteger(ids), status);
        if (resultCount > 0) {
            return ServerResponse.createBySuccessMessage("修改指定课程状态成功!");
        }
        return ServerResponse.createByErrorMessage("修改指定课程状态失败!");
    }

    @Override
    public ServerResponse<Course> selectCourseInfo(Integer courseId) {
        Course course = courseMapper.selectByPrimaryKey(courseId);
        if (course == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        return ServerResponse.createBySuccess(course);
    }

    @Override
    public ServerResponse<Course> updateCourseInfo(Course course) {
        Course updateCourse = new Course();

        updateCourse.setCourseId(course.getCourseId());
        updateCourse.setName(course.getName());
        updateCourse.setCourseInfo(course.getCourseInfo());
        updateCourse.setCreateTime(course.getCreateTime());
        updateCourse.setUpdateTime(course.getUpdateTime());
        updateCourse.setStatus(course.getStatus());

        int resultCount = courseMapper.updateByPrimaryKeySelective(updateCourse);
        if (resultCount > 0) {
            return ServerResponse.createBySuccess("修改课程成功!", updateCourse);
        }
        return ServerResponse.createByErrorMessage("课程信息修改失败!");
    }

    @Override
    public ServerResponse<List<Course>> courseListByUser(List<Long> classId) {
        if (classId.size() < 0) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        List<Integer> ids = classCourseMapper.selectCourseIdByAllClassId(classId);
        List<Course> courseList = courseMapper.selectCourseByCourseId(ids,"%%");
        return ServerResponse.createBySuccess(courseList);
    }

    @Override
    public List<Long> getClassId(Integer userId) {
        List<Long> classIds = userClassMapper.getClassIdByUserId(userId);
        return classIds;
    }
}
