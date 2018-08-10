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
public class SignItem {

    private Integer id;

    private Long signId;

    private Integer userId;

    private String userIp;

    private Date signTime;

    public SignItem(Long signId, Integer userId, String userIp, Date signTime) {
        this.signId = signId;
        this.userId = userId;
        this.userIp = userIp;
        this.signTime = signTime;
    }
}