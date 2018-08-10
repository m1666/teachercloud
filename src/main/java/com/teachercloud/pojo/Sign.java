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
public class Sign {

    private Long signId;

    private Integer courseId;

    private Integer userId;

    private Integer status;

    private Integer count;

    private Date createTime;

    private Date endTime;

    private Date closeTime;

}