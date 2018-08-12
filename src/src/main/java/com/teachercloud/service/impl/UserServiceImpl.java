package com.teachercloud.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.teachercloud.common.Const;
import com.teachercloud.common.ServerResponse;
import com.teachercloud.common.TokenCache;
import com.teachercloud.dao.UserMapper;
import com.teachercloud.pojo.User;
import com.teachercloud.service.IUserService;
import com.teachercloud.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.teachercloud.util.StringArrayUtil.stringToInteger;

/**
 * User: mann
 * DateTime: 2018/7/19 10:39
 */
@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {
        int resultCount = userMapper.checkUsername(username);
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("用户名不存在!");
        }

        String md5Password = MD5Util.MD5EncodeUtf8(password);
        User user = userMapper.selectLogin(username, md5Password);
        if (user == null) {
            return ServerResponse.createByErrorMessage("密码错误");
        }

        user.setPassword(org.apache.commons.lang3.StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登录成功", user);
    }

    @Override
    public ServerResponse<String> register(User user) {
        ServerResponse validResponse = this.checkValid(user.getUsername(), Const.USERNAME);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        if (user.getRole() == Const.Role.ROLE_STUDENT || user.getRole() == Const.Role.ROLE_TEACHER) {
            //MD5加密
            user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));

            // 判断 问题、答案 是否为空
            if (StringUtils.isBlank(user.getQuestion())) {
                user.setQuestion(Const.USERDEFAULT.DEFAULT_QUESTION.getValue());
            }
            if (StringUtils.isBlank(user.getAnswer())) {
                user.setAnswer(Const.USERDEFAULT.DEFAULT_ANSWER.getValue());
            }
            int resultCount = userMapper.insert(user);
            if (resultCount > 0) {
                return ServerResponse.createBySuccessMessage("注册成功");
            }
        }
        return ServerResponse.createByErrorMessage("注册失败");
    }

    @Override
    public ServerResponse<String> checkValid(String str, String type) {
        if (StringUtils.isNotBlank(type)) {
            //开始校验
            if (Const.USERNAME.equals(type)) {
                int resultCount = userMapper.checkUsername(str);
                if (resultCount > 0) {
                    return ServerResponse.createByErrorMessage("用户名已存在");
                }
            }
        } else {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        return ServerResponse.createBySuccessMessage("校验成功");
    }

    @Override
    public ServerResponse<String> selectQuestion(String username) {
        ServerResponse validResponse = this.checkValid(username, Const.USERNAME);
        if (validResponse.isSuccess()) {
            // 用户名存在返回false，用户名不存在返回true
            return ServerResponse.createByErrorMessage("用户名不存在");
        }
        String question = userMapper.selectQuestionByUsername(username);
        // 如果问题不为空
        if (StringUtils.isNotBlank(question)) {
            return ServerResponse.createBySuccess(question);
        }
        return ServerResponse.createByErrorMessage("找回密码的问题为空，无法找回密码，请联系管理员");
    }

    @Override
    public ServerResponse<String> checkAnswer(String username, String question, String answer) {
        int resultCount = userMapper.checkAnswer(username, question, answer);
        if (resultCount > 0) {
            // 验证通过
            String forgetToken = UUID.randomUUID().toString();
            // 将 Token 设置金Cache中 key为 token_username
            TokenCache.setKey(TokenCache.TOKEN_PREFIX + username, forgetToken);
            // 将 Token 传回给前端
            return ServerResponse.createBySuccess(forgetToken);
        }
        return ServerResponse.createByErrorMessage("问题的答案有误!");
    }

    @Override
    public ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken) {
        if (StringUtils.isBlank(forgetToken)) {
            return ServerResponse.createByErrorMessage("参数错误，Token需要传递!");
        }
        ServerResponse validResponse = this.checkValid(username, Const.USERNAME);
        if (validResponse.isSuccess()) {
            // 用户名存在返回false，用户名不存在返回true
            return ServerResponse.createByErrorMessage("用户名不存在!");
        }
        String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX + username);
        if (StringUtils.isBlank(token)) {
            return ServerResponse.createByErrorMessage("Token无效或者过期!");
        }
        if (StringUtils.equals(forgetToken, token)) {
            String md5Password = MD5Util.MD5EncodeUtf8(passwordNew);
            int rowCount = userMapper.updatePasswordByUsername(username, passwordNew);

            if (rowCount > 0) {
                return ServerResponse.createBySuccessMessage("修改密码成功!");
            }
        } else {
            return ServerResponse.createByErrorMessage("Token错误，请重新获取重置密码的Token!");
        }
        return ServerResponse.createByErrorMessage("修改密码失败!");
    }

    @Override
    public ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user) {
        // 此处要注意防止横向越权，需要先验证用户旧密码是否正确
        int resultCount = userMapper.checkPassword(MD5Util.MD5EncodeUtf8(passwordOld), user.getId());
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("原始密码错误!");
        }

        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if (updateCount > 0) {
            return ServerResponse.createBySuccessMessage("更新密码成功!");
        }
        return ServerResponse.createByErrorMessage("更新密码失败!");
    }

    @Override
    public ServerResponse<User> updateInformation(User user) {
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setUsername(user.getUsername());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());
        if (user.getStatus() != null) {
            updateUser.setStatus(user.getStatus());
        }
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if (updateCount > 0) {
            return ServerResponse.createBySuccess("更新个人信息成功!", updateUser);
        }
        return ServerResponse.createByErrorMessage("更新个人信息是失败!");
    }

    @Override
    public ServerResponse<User> getInformation(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null) {
            return ServerResponse.createByErrorMessage("找不到当前用户!");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess(user);
    }

    // backend

    @Override
    public ServerResponse checkAdminRole(User user) {
        if (user != null && user.getRole().intValue() == Const.Role.ROLE_ADMIN) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

    @Override
    public ServerResponse checkTeacherRole(User user) {
        if (user != null && user.getRole().intValue() == Const.Role.ROLE_TEACHER) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

    @Override
    public ServerResponse<PageInfo> getUserByKeyWordAndColumn(int role, String keyword, String column, int pageNum, int pageSize) {
        if (keyword == null) {
            keyword = "";
        }
        keyword = new StringBuffer("%").append(keyword).append("%").toString();
        PageHelper.startPage(pageNum, pageSize);
        List<User> userList = userMapper.selectByKeyWordAndColumn(role, keyword, column);
        PageInfo pageInfo = new PageInfo(userList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse<String> insertUser(User user) {
        ServerResponse validResponse = this.checkValid(user.getUsername(), Const.USERNAME);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        //MD5加密
        user.setPassword(MD5Util.MD5EncodeUtf8(Const.USERDEFAULT.DEFAULT_PASSWD.getValue()));

        // 判断 问题、答案 是否为空
        if (StringUtils.isBlank(user.getQuestion())) {
            user.setQuestion(Const.USERDEFAULT.DEFAULT_QUESTION.getValue());
        }
        if (StringUtils.isBlank(user.getAnswer())) {
            user.setAnswer(Const.USERDEFAULT.DEFAULT_ANSWER.getValue());
        }
        int resultCount = userMapper.insertSelective(user);
        if (resultCount > 0) {
            return ServerResponse.createBySuccessMessage("增加成功!");
        }
        return ServerResponse.createByErrorMessage("增加失败!");
    }

    @Override
    public ServerResponse<User> updateInformationByManager(User user) {
            User updateUser = new User();
            //ID不能为空
            updateUser.setId(user.getId());
            updateUser.setUsername(user.getUsername());
            updateUser.setPassword(user.getPassword());
            updateUser.setName(user.getName());
            updateUser.setStatus(user.getStatus());
            updateUser.setQuestion(user.getQuestion());
            updateUser.setAnswer(user.getAnswer());
            updateUser.setRole(user.getRole());
            updateUser.setCreateTime(user.getCreateTime());
            updateUser.setUpdateTime(user.getUpdateTime());
            if (user.getId() == null || StringUtils.isEmpty(user.getUsername())) {
                return ServerResponse.createByErrorMessage("ID或用户名不能为空!");
            }
            int updateCount = userMapper.updateByPrimaryKeySelective(user);
            if (updateCount > 0) {
                return ServerResponse.createBySuccess("更新个人信息成功!", updateUser);
            }
            return ServerResponse.createByErrorMessage("更新个人信息是失败!");

    }

    @Override
    public ServerResponse<String> updateStatus(int status, String ids) {
        if (StringUtils.isBlank(ids)) {
            return ServerResponse.createByErrorMessage("未选定用户，请核实后操作!");
        }
        int resultCount = userMapper.updateStatus(status, stringToInteger(ids));
        if (resultCount > 0) {
            return ServerResponse.createBySuccessMessage("修改选定用户状态成功!");
        }
        return ServerResponse.createByErrorMessage("修改选定用户状态失败!");
    }

    @Override
    public ServerResponse<String> deleteUsers(String ids) {
        if (StringUtils.isBlank(ids)) {
            return ServerResponse.createByErrorMessage("未选定用户，请核实后操作!");
        }
        int resultCount = userMapper.deleteUsers(stringToInteger(ids));
        if (resultCount > 0) {
            return ServerResponse.createBySuccessMessage("删除选定用户成功!");
        }
        return ServerResponse.createByErrorMessage("删除选定用户失败!");
    }

    @Override
    public ServerResponse<PageInfo> getStudentInfoByUserId(List<Integer> userIds, String keyword, String column, int pageNum, int pageSize) {
        if (keyword == null) {
            keyword = "";
        }
        keyword = new StringBuffer("%").append(keyword).append("%").toString();
        PageHelper.startPage(pageNum, pageSize);
        List<User> userList = userMapper.selectStudentInfoByUserId(Const.Role.ROLE_STUDENT, userIds, keyword, column);
        PageInfo pageInfo = new PageInfo(userList);
        return ServerResponse.createBySuccess(pageInfo);
    }
}
