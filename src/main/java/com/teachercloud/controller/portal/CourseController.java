package com.teachercloud.controller.portal;

import com.github.pagehelper.PageInfo;
import com.teachercloud.common.Const;
import com.teachercloud.common.ResponseCode;
import com.teachercloud.common.ServerResponse;
import com.teachercloud.pojo.Course;
import com.teachercloud.pojo.User;
import com.teachercloud.service.ICourseService;
import com.teachercloud.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * User: mann
 * DateTime: 2018/7/30 15:34
 */
@Controller
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private ICourseService iCourseService;

    @Autowired
    private IUserService iUserService;

    /**
     * 查询所有课程
     *
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/list.do")
    @ResponseBody
    public ServerResponse<PageInfo> courseList(@RequestParam(value = "keyword", required = false) String keyword,
                                               @RequestParam(value = "column", defaultValue = "course_id") String column,
                                               @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return iCourseService.selectAllCourse(keyword,column,pageNum, pageSize);
    }

    /**
     * 学生根据关键字模糊查询本班所有课程
     *
     * @param classId
     * @param session
     * @return
     */
    @RequestMapping("/get_student_course.do")
    @ResponseBody
    public ServerResponse<List<Course>> getStudentCourse(HttpSession session, Long classId, String keyword) {
        //获取session中的user对象
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("未登录，请登录后操作!");
        }
        return iCourseService.getCourseStudent(classId, keyword);
    }

}
