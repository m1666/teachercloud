package com.teachercloud.controller.backend;

import com.github.pagehelper.PageInfo;
import com.teachercloud.common.Const;
import com.teachercloud.common.ServerResponse;
import com.teachercloud.pojo.User;
import com.teachercloud.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * User: mann
 * DateTime: 2018/7/20 23:39
 */

@Controller
@RequestMapping("/manage/user")
public class UserManageController {

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

    /**
     * 查看用户信息，可进行模糊查询，按角色来查询
     *
     * @param session
     * @param role
     * @param keyword
     * @param column   可以是：用户ID，用户名，姓名
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/list.do")
    @ResponseBody
    public ServerResponse<PageInfo> userList(HttpSession session,
                                             @RequestParam(value = "role", defaultValue = "-1") int role,
                                             @RequestParam(value = "keyword", required = false) String keyword,
                                             @RequestParam(value = "column ", defaultValue = "userId") String column,
                                             @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                             @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("未登录，请登录管理员账号后操作!");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iUserService.getUserByKeyWordAndColumn(role, keyword, column, pageNum, pageSize);
        }
        return ServerResponse.createByErrorMessage("无操作权限，请登录管理员账户进行操作!");
    }

    /**
     * 管理员增加用户<br>
     * <li>管理员只需要增加用户名，用户类型，以及用户状态</li>
     *
     * @param session
     * @param user
     * @return
     */
    @RequestMapping(value = "/insert.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> insertUser(HttpSession session, User user) {
        User currentuser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentuser == null) {
            return ServerResponse.createByErrorMessage("未登录，请登录管理员账号后操作!");
        }
        if (iUserService.checkAdminRole(currentuser).isSuccess()) {
            return iUserService.insertUser(user);
        }
        return ServerResponse.createByErrorMessage("无操作权限，请登录管理员账户进行操作!");
    }

    /**
     * 获取指定用户当前信息
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/get_user_info.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession session,Integer userId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iUserService.getInformation(userId);
        }
        return ServerResponse.createByErrorMessage("用户未登录，无法获取当前用户的信息！");

    }

    /**
     * 修改指定用户信息
     *
     * @param session
     * @param user
     * @return
     */
    @RequestMapping(value = "/update_information.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> updateInfomation(HttpSession session, User user) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (iUserService.checkAdminRole(currentUser).isSuccess()) {
            ServerResponse<User> response = iUserService.updateInformationByManager(user);
            if (response.isSuccess()) {
                return ServerResponse.createBySuccess("用户信息更新成功!", response.getData());
            }
        }
        return ServerResponse.createByErrorMessage("无权限操作!");
    }

    /**
     * 批量修改用户状态
     *
     * @param session
     * @param status
     * @param ids
     * @return
     */
    @RequestMapping(value = "/update_status.do")
    @ResponseBody
    public ServerResponse updateStatus(HttpSession session, int status, String ids) {
        User currentuser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentuser == null) {
            return ServerResponse.createByErrorMessage("未登录，请登录管理员账号后操作!");
        }
        if (iUserService.checkAdminRole(currentuser).isSuccess()) {
            return iUserService.updateStatus(status, ids);
        }
        return ServerResponse.createByErrorMessage("无操作权限，请登录管理员账户进行操作!");
    }

    /**
     * 批量删除用户
     *
     * @param session
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete_users.do")
    @ResponseBody
    public ServerResponse<String> deleteUsers(HttpSession session, String ids) {
        User currentuser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentuser == null) {
            return ServerResponse.createByErrorMessage("未登录，请登录管理员账号后操作!");
        }
        if (iUserService.checkAdminRole(currentuser).isSuccess()) {
            return iUserService.deleteUsers(ids);
        }
        return ServerResponse.createByErrorMessage("无操作权限，请登录管理员账户进行操作!");
    }

}
