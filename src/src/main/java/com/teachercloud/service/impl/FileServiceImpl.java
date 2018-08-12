package com.teachercloud.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.teachercloud.common.Const;
import com.teachercloud.common.ServerResponse;
import com.teachercloud.dao.CourseMapper;
import com.teachercloud.dao.FileMapper;
import com.teachercloud.pojo.Course;
import com.teachercloud.pojo.File;
import com.teachercloud.service.IFileService;
import com.teachercloud.util.FileUtil;
import com.teachercloud.vo.FileInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Lenovo
 * 日期: 2018-07-29
 * 时间: 9:22
 */
@Service
public class FileServiceImpl implements IFileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private CourseMapper courseMapper;


    /**
     * 文件上传
     * @param file      文件实体类
     * @param filePath  文件 filePath[0]为文件 filePath[1]为图片
     * @return
     */
    @Override
    public ServerResponse<String> upload(File file, MultipartFile[] filePath) {
        //获取文件路径
        String path =FileUtil.judgmentPath(file.getType());
        if (path == null) {
            return ServerResponse.createByErrorMessage("没有选择文件类型");
        }
        //上传文件
        boolean flag = FileUtil.uploadFile(path, filePath[0]);
        if (flag) {
            //文件上传成功, 再上传主图
            flag = FileUtil.uploadFile(Const.FILEPATH.FILEIMAGE.getValue(), filePath[1]);
            if (flag) {
                //文件和主图都上传成功, 将session数据放入File实体类
                file.setType(file.getType());
                file.setName(filePath[0].getOriginalFilename());
                file.setMainImage(filePath[1].getOriginalFilename());
                //将文件信息存到数据库
                if (fileMapper.insert(file) > 0) {
                    return ServerResponse.createBySuccessMessage("文件上传成功");
                }
                return ServerResponse.createBySuccessMessage("文件上传失败");
            } else {
                //文件上传成功,而主图没上传成功 删除文件
                java.io.File fileDelete = new java.io.File(path, filePath[0].getOriginalFilename());
                fileDelete.delete();
                return ServerResponse.createBySuccessMessage("文件上传失败");
            }
        }
        return ServerResponse.createBySuccessMessage("文件上传失败");

    }


    /**
     * 文件下载
     * @param file          文件实体类
     * @param response
     * @return
     */
    @Override
    public ServerResponse<String> downFile(File file, HttpServletResponse response) {
        //获取文件路径
        String path =FileUtil.judgmentPath(file.getType());
        String fileName = file.getName();
        //文件名不为空
        if (fileName != null) {
            //拼接文件路径
            java.io.File filePath = new java.io.File(path, fileName);
            //在对应路径下找到文件
            if (filePath.exists()) {
                // 设置强制下载不打开
                response.setContentType("application/force-download");
                // 设置文件名
                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
                //设置下载字节大小
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(filePath);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        //修改数据库文件下载量
        int rowCount = fileMapper.updateDownloads(fileName);
        if (rowCount > 0) {
            return ServerResponse.createBySuccessMessage("文件下载成功");
        }
        return ServerResponse.createByErrorMessage("文件下载失败");
    }

    /**
     * 删除文件从数据库和本地中
     * @param file  文件实体类
     * @return
     */
    @Override
    public ServerResponse<String> deleteLocalAndSql(File file) {
        //获取文件路径
        String path =FileUtil.judgmentPath(file.getType());
        //定位文件的文件
        java.io.File filePath = new java.io.File(path,file.getName());
        //filePath是文件且文件路径存在
        if (filePath.isFile() && filePath.exists()) {
            //删除文件
            filePath.delete();
            //删除文件并且删除主图
            filePath = new java.io.File(Const.FILEPATH.FILEIMAGE.getValue(),file.getMainImage()) ;
            filePath.delete() ;

            //从数据库中通过文件Id删除文件
            int rowCount = fileMapper.deleteByPrimaryKey(file.getFileId());

            if (rowCount > 0) {
                return ServerResponse.createBySuccessMessage("文件删除成功");
            }
        }
        return ServerResponse.createByErrorMessage("文件删除失败");
    }


    /**
     * 修改文件信息, 文件type不能修改
     * @param file
     * @param oldFileName
     * @param oldImageName
     * @return
     */
    @Override
    public ServerResponse<File> updateDetail(File file, String oldFileName,String oldImageName) {
        //获取文件路径
        String path =FileUtil.judgmentPath(file.getType());

        //修改数据库文件信息
        int rowCount = fileMapper.updateFileDetail(file);
        //得到文件和主图的名称
        String newName = file.getName();
        String newImageName = file.getMainImage() ;

        if (rowCount > 0) {
            //修改数据库 和 本地文件 名称
            FileUtil.updateFile(oldFileName,newName,path) ;
            FileUtil.updateFile(oldImageName,newImageName,Const.FILEPATH.FILEIMAGE.getValue()) ;

            return ServerResponse.createBySuccess("修改成功", file);
        }

        return ServerResponse.createByErrorMessage("修改失败");
    }

    /**
     * 通过关键字查询文件信息
     * @param keyword   关键字
     * @param column    条件
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ServerResponse<PageInfo> getFileByKeyWordAndColumn(String  keyword, String column, int pageNum, int pageSize){
        //FileInfo用来前端展示
        List<FileInfo> fileInfoList = new ArrayList<>() ;
        if (keyword == null) {
            keyword = "";
        }
        //keyword进行模糊查询拼接
        keyword = new StringBuffer("%").append(keyword).append("%").toString();
        PageHelper.startPage(pageNum, pageSize);
        //遍历出文件的courseId
        List<Integer> courseIds = fileMapper.selectByKeyWordAndColumn(keyword,column);
        if (courseIds.size() == 0){
            return ServerResponse.createByErrorMessage("查询失败") ;
        }
        //通过courseId查找文件信息
        List<File> courseList = fileMapper.selectFileByCourseId(courseIds,keyword, column);
        for (File fileList: courseList) {
            //通过courseId查找课程信息
            Course course = courseMapper.selectByPrimaryKey(fileList.getCourseId());
            //添加到文件信息类
            fileInfoList.add(FileUtil.setFileInfo(fileList,course)) ;
        }
        //分页
        PageInfo pageInfo = new PageInfo(courseList);
        pageInfo.setList(fileInfoList);
        return ServerResponse.createBySuccess(pageInfo);
    }
}
