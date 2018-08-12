package com.teachercloud.controller.backend;

import com.github.pagehelper.PageInfo;
import com.teachercloud.common.Const;
import com.teachercloud.common.ServerResponse;
import com.teachercloud.pojo.ClassItem;
import com.teachercloud.pojo.Course;
import com.teachercloud.pojo.User;
import com.teachercloud.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * User: mann
 * DateTime: 2018/7/29 20:40
 */
@Controller
@RequestMapping("/manage/teacher")
public class TeacherManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IUserClassService iUserClassService;

    @Autowired
    private ICourseService iCourseService;

    @Autowired
    private IClassItemService iClassItemService;

    /**
     * 老师查看班级详情
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/get_classItem_info.do")
    @ResponseBody
    public ServerResponse<List<ClassItem>> getClassItemInfo(HttpSession session) {
        //判断老师是否登陆
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("未登录，请登录老师账户!");
        }
        if (iUserService.checkTeacherRole(user).isSuccess()) {
            ServerResponse response = iUserClassService.getClassId(user.getId());
            return iClassItemService.getClassItems((List<Long>) response.getData());
        }
        return ServerResponse.createByErrorMessage("无操作权限，请登录老师账户!");
    }


    /**
     * 根据老师ID获取老师所教课程
     * @param session
     * @return
     */
    @RequestMapping(value = "/get_course_info.do")
    @ResponseBody
    public ServerResponse<List<Course>> courseListByUser(HttpSession session) {
        //判断老师是否登陆
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("未登录，请登录老师账户!");
        }
        if (iUserService.checkTeacherRole(user).isSuccess()) {
            ServerResponse response = iUserClassService.getClassId(user.getId());
            return iCourseService.courseListByUser((List<Long>) response.getData());
        }
        return ServerResponse.createByErrorMessage("无操作权限，请登录老师账户!");
    }

    /**
     * 查看某班学生信息
     *
     * @param classId
     * @param session
     * @param keyword
     * @param column   可以是：用户ID，用户名，姓名
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/get_student_info.do")
    @ResponseBody
    public ServerResponse<PageInfo> getStudentInfo(Long classId, HttpSession session,
                                                   @RequestParam(value = "keyword", required = false) String keyword,
                                                   @RequestParam(value = "column ", defaultValue = "userId") String column,
                                                   @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                   @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        //判断老师是否登陆
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("未登录，请登录老师账户!");
        }
        if (iUserService.checkTeacherRole(user).isSuccess()) {
            ServerResponse response = iUserClassService.getUserId(classId);
            List<Integer> ids = (List<Integer>) response.getData();
            return iUserService.getStudentInfoByUserId(ids, keyword, column, pageNum, pageSize);
        }
        return ServerResponse.createByErrorMessage("无操作权限，请登录老师账户!");
    }

}
