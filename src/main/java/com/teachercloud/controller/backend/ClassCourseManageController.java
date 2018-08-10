package com.teachercloud.controller.backend;

import com.teachercloud.common.Const;
import com.teachercloud.common.ServerResponse;
import com.teachercloud.pojo.ClassCourse;
import com.teachercloud.pojo.User;
import com.teachercloud.service.IClassCourseService;
import com.teachercloud.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @Author: mann
 * @Date: 2018/8/1 8:33
 */
@Controller
@RequestMapping("/manage/classcourse")
public class ClassCourseManageController {

    @Autowired
    private IClassCourseService iClassCourseService;

    @Autowired
    private IUserService iUserService;

    /**
     * 批量修改课程状态
     *
     * @param httpSession
     * @param ids
     * @param status
     * @return
     */
    @RequestMapping(value = "/update_classcourse_status.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> updateClassCourseStatus(HttpSession httpSession, String ids, int status) {
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("未登录，请登录管理员账号后操作!");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iClassCourseService.updateClassCourseStatus(ids, status);
        }
        return ServerResponse.createByErrorMessage("无操作权限，请登录管理员账户进行操作!");
    }

    //Byhekai

    /**
     * 绑定一个课程到班级
     * @param httpSession
     * @param courseId
     * @param classId
     * @return
     */
    @RequestMapping(value = "/insert_classcourse.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<ClassCourse> insertClassCourseStatus(HttpSession httpSession, Integer courseId, Long classId, String createTime, String endTime) {
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("未登录，请登录管理员账号后操作!");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iClassCourseService.insertClassCourse(courseId,classId,createTime,endTime);
        }
        return ServerResponse.createByErrorMessage("无操作权限，请登录管理员账户进行操作!");
    }


    /**
     * 删除一个课程和班级的关联
     * @param session
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete_classCourse.do")
    @ResponseBody
    public ServerResponse deleteCalssCourse(HttpSession session, Integer id) {
        User currentuser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentuser == null) {
            return ServerResponse.createByErrorMessage("未登录，请登录管理员账号后操作!");
        }
        if (currentuser.getRole() == Const.Role.ROLE_ADMIN) {
            return iClassCourseService.deleteClass(id);
        }
        return ServerResponse.createByErrorMessage("不是管理员，无法登录！");
    }


    /**
     * 查询详情
     * @param httpSession
     * @param courseId
     * @param classId
     * @return
     */
    @RequestMapping(value = "/selectdetail.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<ClassCourse> selectDetail(HttpSession httpSession,@RequestParam("courseId")Integer courseId,
                                                    @RequestParam("classId") Long classId){
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("未登录，请登录管理员账号后操作!");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iClassCourseService.selectdetail(courseId,classId);
        }
        return ServerResponse.createByErrorMessage("无操作权限，请登录管理员账户进行操作!");
    }




}
