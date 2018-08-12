package com.teachercloud.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class File {

    private Long fileId;

    private Integer userId;

    private Integer courseId;

    private Long classId;

    private String name;

    private String detail;

    private String mainImage;

    private Integer status;

    private Integer type;

    private Integer downloads;

    private Integer count;

    private Date createTime;

    private Date updateTime;

}