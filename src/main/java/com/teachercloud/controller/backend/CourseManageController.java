package com.teachercloud.controller.backend;

import com.github.pagehelper.PageInfo;
import com.teachercloud.common.Const;
import com.teachercloud.common.ServerResponse;
import com.teachercloud.pojo.Course;
import com.teachercloud.pojo.User;
import com.teachercloud.service.ICourseService;
import com.teachercloud.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * User: mann
 * DateTime: 2018/7/31 9:37
 */
@Controller
@RequestMapping("/manage/course")
public class CourseManageController {

    @Autowired
    private ICourseService iCourseService;
    @Autowired
    private IUserService iUserService;

    /**
     * 展示所有课程，可进行模糊查询
     *
     * @param session
     * @param keyword
     * @param column   模糊字段可用course_id，name, course_info, status
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/list.do")
    @ResponseBody
    public ServerResponse<PageInfo> courseList(HttpSession session,
                                               @RequestParam(value = "keyword", required = false) String keyword,
                                               @RequestParam(value = "column", defaultValue = "course_id") String column,
                                               @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iCourseService.selectAllCourse(keyword, column, pageNum, pageSize);
        }
        return ServerResponse.createByErrorMessage("无权限浏览，请登录管理员账号!");
    }

    /**
     * 增加课程
     *
     * @param session
     * @param course
     * @return
     */
    @RequestMapping(value = "/insert.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> createCourse(HttpSession session, Course course) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("未登录，请登录管理员账号!");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            if (!iCourseService.checkCoursId(course.getCourseId()).isSuccess()) {
                return ServerResponse.createByErrorMessage("课程ID已存在，请重新输入!");
            }
            return iCourseService.createCourse(course);
        }
        return ServerResponse.createByErrorMessage("无权限操作，请切换管理员账号!");
    }

    /**
     * 批量删除课程
     *
     * @param session   用于判断是否是管理员用户
     * @param courseIds 以英文逗号','隔开，例如"1001,1002,1003"
     * @return
     */
    @RequestMapping(value = "/delete_Course.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> deleteCourses(HttpSession session, String courseIds) {
        User currentuser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentuser == null) {
            return ServerResponse.createByErrorMessage("未登录，请登录管理员账号后操作!");
        }
        if (iUserService.checkAdminRole(currentuser).isSuccess()) {
            return iCourseService.deleteCourse(courseIds);
        }
        return ServerResponse.createByErrorMessage("无操作权限，请登录管理员账户进行操作!");
    }


    /**
     * 批量修改课程状态
     *
     * @param session   用于判断是否是管理员用户
     * @param courseIds 以英文逗号','隔开，例如"1001,1002,1003"
     * @param status    课程状态，0：开课、1：停课
     * @return
     */
    @RequestMapping(value = "/update_Course_Status.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> updateCourseStatus(HttpSession session, String courseIds, Integer status) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("未登录，请登录管理员账号后操作!");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iCourseService.updateCourseStatus(courseIds, status);
        }
        return ServerResponse.createByErrorMessage("无操作权限，请登录管理员账户进行操作!");
    }

    @RequestMapping("/get_course_info.do")
    @ResponseBody
    public ServerResponse<Course> getCourseInfo(HttpSession session, Integer courseId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("未登录，请登录管理员账号后操作!");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iCourseService.selectCourseInfo(courseId);
        }
        return ServerResponse.createByErrorMessage("无操作权限，请登录管理员账户进行操作!");
    }

    @RequestMapping(value = "/update_Course_Info.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<Course> updateCourse(HttpSession session, Course course) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("未登录，请登录管理员账号后操作!");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iCourseService.updateCourseInfo(course);
        }
        return ServerResponse.createByErrorMessage("无操作权限，请登录管理员账户进行操作!");
    }

}
