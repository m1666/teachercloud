package com.teachercloud.controller.backend;

import com.github.pagehelper.PageInfo;
import com.teachercloud.common.Const;
import com.teachercloud.common.ResponseCode;
import com.teachercloud.common.ServerResponse;
import com.teachercloud.pojo.ClassItem;
import com.teachercloud.pojo.Course;
import com.teachercloud.pojo.User;
import com.teachercloud.service.IClassItemService;
import com.teachercloud.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * User: mann
 * DateTime: 2018/7/29 10:46
 */
@Controller
@RequestMapping("/manage/class")
public class ClassManageController {


    @Autowired
    private IUserService iUserService;

    @Autowired
    private IClassItemService iClassItemService;

    /**
     * 修改班级信息
     * @param session
     * @param classItem
     * @Author 阿凯
     * @return
     */
    @RequestMapping(value = "/update_Class_Info.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<ClassItem> updateClassItem(HttpSession session, ClassItem classItem) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("未登录，请登录管理员账号后操作!");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iClassItemService.updateClassItem(classItem);
        }
        return ServerResponse.createByErrorMessage("无操作权限，请登录管理员账户进行操作!");
    }


    /**
     * 根据班级号删除班级
     * @param session
     * @param classIds
     * @return
     */
    @RequestMapping(value = "/delete_Class_Info.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> deleteClassItem(HttpSession session,String classIds) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("未登录，请登录管理员账号后操作!");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iClassItemService.deleteClassItem(classIds);
        }
        return ServerResponse.createByErrorMessage("无操作权限，请登录管理员账户进行操作!");
    }


    @RequestMapping(value = "/getall.do",method = RequestMethod.POST)
    @ResponseBody
    private  ServerResponse<PageInfo> getAllClass(HttpSession httpSession, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                  @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        User user = (User)httpSession.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage(ResponseCode.NEED_LOGIN.getDesc());
        }
        if (user.getRole() != 0) {
            return ServerResponse.createByErrorMessage("没有该权限");
        }
        return iClassItemService.getAllClassItems(pageNum,pageSize);
    }

    @RequestMapping(value = "/update_class_status.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> updateClassCourseStatus(HttpSession httpSession, String classIds, int status) {
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("未登录，请登录管理员账号后操作!");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iClassItemService.updateClassItemCourseStatus(classIds,status);
        }
        return ServerResponse.createByErrorMessage("无操作权限，请登录管理员账户进行操作!");
    }

}