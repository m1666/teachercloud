package com.teachercloud.service;

import com.github.pagehelper.PageInfo;
import com.teachercloud.common.ServerResponse;
import com.teachercloud.pojo.User;

import java.util.List;

/**
 * User: mann
 * DateTime: 2018/7/19 10:39
 */
public interface IUserService {

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @return
     */
    ServerResponse<User> login(String username, String password);

    /**
     * 注册用户
     *
     * @param user
     * @return
     */
    ServerResponse<String> register(User user);

    /**
     * 数据校验
     *
     * @param str  字段内容  如：username的内容
     * @param type 字段类型  如：username字段
     * @return
     */
    ServerResponse<String> checkValid(String str, String type);

    /**
     * 通过用户名查询问题
     *
     * @param username
     * @return 将查找到的问题返回给用户
     */
    ServerResponse<String> selectQuestion(String username);

    /**
     * 通过用户名、问题、答案进行检测该用户答案与问题是否一致
     *
     * @param username
     * @param question
     * @param answer
     * @return 返回一个forgetToken
     */
    ServerResponse<String> checkAnswer(String username, String question, String answer);

    /**
     * 在用户忘记密码时，修改密码操作
     *
     * @param username
     * @param passwordNew
     * @param forgetToken
     * @return
     */
    ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken);

    /**
     * 修改密码
     *
     * @param passwordOld
     * @param passwordNew
     * @param user
     * @return
     */
    ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user);

    /**
     * 更新用户个人信息，用户ID与用户名不可修改
     *
     * @param user
     * @return
     */
    ServerResponse<User> updateInformation(User user);

    /**
     * 获取用户信息
     *
     * @param userId
     * @return 将user封装后返回
     */
    ServerResponse<User> getInformation(Integer userId);

    // backend

    /**
     * 校验是否是管理员
     *
     * @param user
     * @return 返回成功或者失败
     */
    ServerResponse checkAdminRole(User user);

    /**
     * 校验是否是教师用户
     *
     * @param user
     * @return 返回成功或者失败
     */
    ServerResponse checkTeacherRole(User user);

    /**
     * 管理员修改用户信息
     *
     * @param user
     * @return
     */
    ServerResponse updateInformationByManager(User user);

    /**
     * 管理员查询用户信息<br>
     * <li>1、实现模糊查询操作，keyword = keyword ，column = column</li>
     * <li>2、实现分页功能，使用 PageInfo 中的 PageHelper.startPage(pageNum,PageSize) 方法</li>
     * <li>3、实现了按角色查询且分页的功能</li>
     *
     * @param keyword  模糊查询关键字
     * @param column   模糊查询关键列
     * @param pageNum  当前显示页
     * @param pageSize 每页显示的数据量
     * @return 返回 PageInfo
     */
    ServerResponse<PageInfo> getUserByKeyWordAndColumn(int role, String keyword, String column, int pageNum, int pageSize);

    /**
     * 管理员增加用户
     *
     * @param user
     * @return
     */
    ServerResponse<String> insertUser(User user);

    /**
     * 批量修改用户状态
     *
     * @param status
     * @param ids
     * @return
     */
    ServerResponse<String> updateStatus(int status, String ids);


    /**
     * 批量删除用户
     *
     * @param ids
     * @return
     */
    ServerResponse<String> deleteUsers(String ids);

    /**
     * 根据用户ID获取用户信息
     * @param userId
     * @param keyword
     * @param column
     * @param pageNum
     * @param pageSize
     * @return
     */
    ServerResponse<PageInfo> getStudentInfoByUserId(List<Integer> userId, String keyword, String column, int pageNum, int pageSize);
}
