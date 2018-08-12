package com.teachercloud.dao;

import com.teachercloud.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @return
     */
    User selectLogin(@Param("username") String username, @Param("password") String password);

    /**
     * 验证用户名是否合法
     *
     * @param username
     * @return
     */
    int checkUsername(String username);

    /**
     * 根据用户名查询username
     *
     * @param username
     * @return
     */
    String selectQuestionByUsername(String username);

    /**
     * 验证是否指定用户的问题与答案一致
     *
     * @param username
     * @param question
     * @param answer
     * @return
     */
    int checkAnswer(@Param("username") String username, @Param("question") String question, @Param("answer") String answer);

    /**
     * 跟新新密码，在无法登录的时候
     *
     * @param username
     * @param passwordNew
     * @return
     */
    int updatePasswordByUsername(@Param("username") String username, @Param("passwordNew") String passwordNew);

    /**
     * 验证密码是否为指定用户的密码
     *
     * @param password
     * @param userId
     * @return
     */
    int checkPassword(@Param("password") String password, @Param("userId") int userId);

    /**
     * <li>进行模糊查询操作（分页处理交给Service层处理）</li><br>
     * <li>且可进行按角色查询</li>
     *
     * @param keyword 模糊查询关键字
     * @param column  模糊查询列
     * @return
     */
    List<User> selectByKeyWordAndColumn(@Param("role") int role, @Param("keyword") String keyword, @Param("column") String column);

    /**
     * 修改批量用户状态
     *
     * @param status
     * @param userId
     * @return
     */
    int updateStatus(@Param("status") int status, @Param("userId") int[] userId);

    /**
     * 批量删除用户
     *
     * @param userId
     * @return
     */
    int deleteUsers(@Param("userId") int[] userId);

    /**
     * 查询学生用户信息
     * @param role
     * @param userId
     * @param keyword
     * @param column
     * @return
     */
    List<User> selectStudentInfoByUserId(@Param("role") int role, @Param("userId") List<Integer> userId, @Param("keyword") String keyword, @Param("column") String column);
}