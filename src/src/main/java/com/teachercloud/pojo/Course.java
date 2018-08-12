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
public class Course {

    private Integer courseId;

    private String name;

    private String courseInfo;

    private Date createTime;

    private Date updateTime;

    private Integer status;

}