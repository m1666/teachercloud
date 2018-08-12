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
public class ClassCourse {
    private Integer id;

    private Integer courseId;

    private Long classId;

    private Date createTime;

    private Date endTime;

    private Date updateTime;

    private Integer status;

}