package com.teachercloud.service;

import com.github.pagehelper.PageInfo;
import com.teachercloud.common.ServerResponse;
import com.teachercloud.pojo.File;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author 郑爱民
 * 日期: 2018-07-29
 * 时间: 9:22
 */
public interface IFileService {

    /**
     * 上传文件
     * @param file
     * @param filePath
     * @return
     */
    ServerResponse<String> upload(File file, MultipartFile[] filePath);

    /**
     * 下载文件
     * @param file
     * @param response
     * @return
     */
    ServerResponse<String> downFile(File file, HttpServletResponse response);

    /**
     * 删除文件
     * @param file
     * @return
     */
    ServerResponse<String> deleteLocalAndSql(File file) ;

    /**
     * 更新文件
     * @param file
     * @param oldFileName
     * @param oldImageName
     * @return
     */
    ServerResponse<File> updateDetail(File file, String oldFileName,String oldImageName) ;

    /**
     * 根据多种方式模糊查询
     * @param keyword
     * @param column
     * @param pageNum
     * @param pageSize
     * @return
     */
    ServerResponse<PageInfo> getFileByKeyWordAndColumn(String  keyword, String column, int pageNum, int pageSize);

}