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
public class FileItem {

    private Integer id;

    private Long fileId;

    private Integer userId;

    private Date viewTime;

}