
package com.teachercloud.controller.portal;

import com.github.pagehelper.PageInfo;
import com.teachercloud.common.Const;
import com.teachercloud.common.ServerResponse;
import com.teachercloud.pojo.User;
import com.teachercloud.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @Author: 郑爱民
 * @Date: 2018/7/31 19:46
 */
@Controller
@RequestMapping("/file")
public class FileController {

    @Autowired
    private IFileService iFileService;


    @RequestMapping(value = "/check_file_info.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<PageInfo> userList(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                             @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
                                             @RequestParam(value = "keyword",required = false) String keyword,
                                             @RequestParam(value = "column",defaultValue = "course_id") String column) {

        return iFileService.getFileByKeyWordAndColumn(keyword, column, pageNum, pageSize);

    }
}