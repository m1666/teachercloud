package com.teachercloud.controller.backend;

import com.teachercloud.common.Const;
import com.teachercloud.common.ServerResponse;
import com.teachercloud.pojo.User;
import com.teachercloud.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @Author: mann
 * @Date: 2018/8/2 18:32
 */
@Controller
@RequestMapping("/manage/admin")
public class AdminManageController {

    @Autowired
    private IUserService iUserService;

    /**
     * 管理员用户登录
     *
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping(value = "/login.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session) {
        ServerResponse<User> response = iUserService.login(username, password);
        if (response.isSuccess()) {
            User user = response.getData();
            if (user.getRole() == Const.Role.ROLE_ADMIN) {
                // 说明登录的是管理员
                session.setAttribute(Const.CURRENT_USER, user);
                return response;
            } else {
                return ServerResponse.createByErrorMessage("不是管理员，无法登录！");
            }
        }
        return response;
    }

}
