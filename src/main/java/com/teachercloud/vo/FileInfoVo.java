package com.teachercloud.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @author Lenovo
 * 日期: 2018-08-02
 * 时间: 10:01
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileInfoVo {

    private Long fileId;

    private Integer courseId;

    private String fileName;

    private String courseName;

    private String courseInfo;

    private Integer userId;

    private Long classId;

    private String detail;

    private String mainImage;

    private Integer status;

    private Integer type;

    private Integer count;

    private Integer downloads;

    private Date createTime;

    private Date updateTime;

}