package com.teachercloud.controller.backend;

import com.teachercloud.common.Const;
import com.teachercloud.common.ServerResponse;
import com.teachercloud.dao.FileMapper;
import com.teachercloud.pojo.File;
import com.teachercloud.pojo.User;
import com.teachercloud.service.IFileService;
import com.teachercloud.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * @author 郑爱民
 * 日期: 2018-07-29
 * 时间: 9:13
 */
@RequestMapping("/manage/file")
@Controller
public class FileManageController {

    @Autowired
    private IFileService iFileService;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private FileMapper fileMapper ;

    /**
     * 上传文件 （必须在class_course表中必须有数据）
     * @param session
     * @param file
     * @param filePath
     * @return
     */
    @RequestMapping(value = "/upload.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> fileUpload(HttpSession session, File file,
                                             @RequestParam("filePath") MultipartFile[] filePath) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户未登陆,请登录后再尝试");
        }
        if (iUserService.checkAdminRole(user).isSuccess() || iUserService.checkTeacherRole(user).isSuccess()) {
            //判断权限,进行上传
            ServerResponse<String> response = iFileService.upload(file ,filePath);
            return response;
        }
        return ServerResponse.createByErrorMessage("用户没有权限");
    }

    /**
     * 下载文件
     * @param session
     * @param response
     * @param file
     * @return
     */
    @RequestMapping(value = "/download_file.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> downloadFile(HttpSession session, HttpServletResponse response, File file) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户未登陆,请登录后再尝试");
        }
        //用户登陆即可下载
        return iFileService.downFile(file, response);
    }

    /**
     * 删除文件
     * @param session
     * @param flag
     * @param file
     * @return
     */
    @RequestMapping(value = "/delete_file.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> deleteFile(HttpSession session,int flag, File file) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户未登陆,请登录后再尝试");
        }
        //flag = 1 真删      flag = 0 假删
        if (flag == 1) {
            //状态即不为1,也不为0,则需要从数据库和本地删除
            if (iUserService.checkAdminRole(user).isSuccess() || iUserService.checkTeacherRole(user).isSuccess()) {
                //如果是老师或者管理员可以从数据库和磁盘中删除
                return iFileService.deleteLocalAndSql(file);
            } else {
                return ServerResponse.createByErrorMessage("权限不足");
            }
        }
        if (flag == 0){
            //放入回收站
            if (fileMapper.updateStatus(1) >0){
                return ServerResponse.createBySuccessMessage("放入回收站") ;
            }
            return ServerResponse.createBySuccessMessage("放入回收站失败") ;
        }
        return ServerResponse.createByErrorMessage("文件删除失败");
    }


    /**
     * 更新文件信息
     * @param session
     * @param file
     * @param oldFileName
     * @param oldImageName
     * @return
     */
    @RequestMapping(value = "/update_file.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updateFileDetail(HttpSession session, File file,
                                           @RequestParam(value = "oldFileName",required = false) String oldFileName,
                                           @RequestParam(value = "oldImageName",required = false) String oldImageName) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户未登陆,请登录后再尝试");
        }
        if (iUserService.checkAdminRole(user).isSuccess() || iUserService.checkTeacherRole(user).isSuccess()) {
            //更新文件信息
            return iFileService.updateDetail(file,oldFileName,oldImageName);
        }
        return ServerResponse.createByErrorMessage("用户没有权限");
    }
}
