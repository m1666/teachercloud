package com.teachercloud.vo;

import com.teachercloud.pojo.SignItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @Created by IDEA
 * @author:LiuYiBo(小博)
 * @Date:2018/8/9
 * @Time:19:33
 */
@Getter
@Setter
@NoArgsConstructor
public class SignItmsInfo {

    private Long signId;

    private Integer userId;

    private String name;

    private String userIp;

    private Date signTime;

    public SignItmsInfo(SignItem signItem, String name) {
        this.signId = signItem.getSignId();
        this.userId = signItem.getUserId();
        this.name = name;
        this.userIp = signItem.getUserIp();
        this.signTime = signItem.getSignTime();
    }
}
