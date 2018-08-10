package com.teachercloud.service;

import com.teachercloud.common.ServerResponse;
import com.teachercloud.pojo.Sign;
import com.teachercloud.pojo.SignItem;

import java.util.Date;
import java.util.List;

/**
 * 签到service层接口
 * Created with IDEA
 * author:LiuYiBo(小博)
 * Date:2018/7/19
 * Time:19:01
 */

public interface ISigneService {
    /**
     * 学生签到
     * @param signId
     * @param userIp
     * @param signTime
     * @return
     */
    ServerResponse<String> signeCount(Long signId, Integer userId, String userIp, Date signTime);

    /**
     * 老师开启签到
     * @return
     */
    ServerResponse<String> startSign(Integer userId, Integer courseId);
    /**
     * 老师关闭签到
     * @return
     */
    ServerResponse<String> endSign(Long signId);

    /**
     * 获取当前签到状态
     * @param signId
     * @return
     */
    int getStatus(Long signId);



    /**
     * 获取当前签到
     * @return
     */
    ServerResponse<Sign> getNewSign();

    /**
     * 获取历史签到记录
     * @param userId
     * @param courseId
     * @param pagesize
     * @param startrow
     * @return
     */
    ServerResponse<List<Sign>> getHistorySign(Integer userId, Long courseId, Integer pagesize, Integer startrow);

    ServerResponse<List<SignItem>> getSignItem(Long signId, Integer pagesize, Integer startrow);

}
