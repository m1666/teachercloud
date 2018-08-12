package com.teachercloud.util;

import com.teachercloud.common.Const;
import com.teachercloud.pojo.Course;
import com.teachercloud.pojo.File;
import com.teachercloud.vo.FileInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Lenovo
 * 日期: 2018-08-01
 * 时间: 8:52
 */
public class FileUtil {

    private static Logger logger = LoggerFactory.getLogger(FileUtil.class) ;

    public static boolean uploadFile(String path, MultipartFile file){
        if (file.isEmpty()) {
            return false;
        }
        String fileName = file.getOriginalFilename();
        java.io.File dest = new java.io.File(path, fileName);
        logger.info("上传的文件名为：" + fileName);
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        logger.info("上传的后缀名为：" + suffixName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            return true;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false ;
    }

    public static int updateFile(String oldName, String newName, String path){
        if (oldName != newName){
            java.io.File changeFile = new java.io.File(path +"\\" + oldName);
            changeFile.renameTo(new java.io.File(path + "\\" + newName)) ;
            return 1 ;
        }
        return 0 ;
    }

    public static String judgmentPath(Integer pathType){
        if (pathType == Const.FILEPATH.VIDEO.getCode()) {
            return Const.FILEPATH.VIDEO.getValue();
        }
        if (pathType == Const.FILEPATH.HANDOUT.getCode()) {
            return Const.FILEPATH.HANDOUT.getValue();
        }
        if (pathType == Const.FILEPATH.COURSEWARE.getCode()) {
            return Const.FILEPATH.COURSEWARE.getValue();
        }
        if (pathType == Const.FILEPATH.FILEIMAGE.getCode()) {
            return Const.FILEPATH.FILEIMAGE.getValue();
        }
        return null ;
    }

    public static FileInfo setFileInfo(File fileList, Course course){
        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileId(fileList.getFileId());
        fileInfo.setUserId(fileList.getUserId());
        fileInfo.setCourseId(fileList.getCourseId());
        fileInfo.setClassId(fileList.getClassId());
        fileInfo.setFileName(fileList.getName()) ;
        fileInfo.setDetail(fileList.getDetail());
        fileInfo.setMainImage(fileList.getMainImage());
        fileInfo.setStatus(fileList.getStatus());
        fileInfo.setType(fileList.getType());
        fileInfo.setDownloads(fileList.getDownloads());
        fileInfo.setCount(fileList.getCount()) ;
        fileInfo.setCreateTime(fileList.getCreateTime());
        fileInfo.setUpdateTime(fileList.getUpdateTime());
        fileInfo.setCourseName(course.getName());
        fileInfo.setCourseInfo(course.getCourseInfo());
        return fileInfo ;
    }
}
