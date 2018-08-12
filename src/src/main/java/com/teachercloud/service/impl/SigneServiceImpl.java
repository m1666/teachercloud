package com.teachercloud.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.teachercloud.common.ServerResponse;
import com.teachercloud.dao.SignItemMapper;
import com.teachercloud.dao.SignMapper;
import com.teachercloud.dao.UserMapper;
import com.teachercloud.pojo.Sign;
import com.teachercloud.pojo.SignItem;
import com.teachercloud.pojo.User;
import com.teachercloud.service.ISigneService;
import com.teachercloud.util.*;
import com.teachercloud.vo.SignItmsInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IDEA
 * author:LiuYiBo(小博)
 * Date:2018/7/19
 * Time:19:15
 */
@Service("iSigneService")
public class SigneServiceImpl implements ISigneService {

    @Autowired
    private SignMapper signMapper;

    @Autowired
    private SignItemMapper signItemMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 学生签到记录
     *
     * @param signId
     * @param userIp
     * @param signTime
     * @return
     */
    @Override
    public ServerResponse<String> signeCount(Long signId, Integer userId, String userIp, Date signTime) {
        //获取签到表实体对象
        Sign record = signMapper.selectByPrimaryKey(signId);
        //获取签到明细表实体对象
        SignItem signItem = new SignItem(signId, userId, userIp, signTime);

        //不在签到时间段内
        if (!(signTime.getTime() >= record.getCreateTime().getTime() && signTime.getTime() <= record.getCloseTime().getTime())) {
            //更新签到表实际关闭时间
            setStatus(signId, signTime);
            //签到关闭
            return ServerResponse.createByErrorMessage("暂无开启的签到");

        }
        //判断是否有签到记录，有的话则判定为重复签到
        if (signItemMapper.selectSignItems(signItem.getSignId(), userId) == null) {
            if (signItemMapper.checkUserIp(signId, userIp) > 0) {
                //重复ip
                return ServerResponse.createByErrorMessage("该客户端已签到");
            } else {
                if (signMapper.updateByPrimaryKey(signId) <= 0) {
                    return ServerResponse.createByErrorMessage("未知错误1，签到失败");
                } else {
                    if (signItemMapper.insert(signItem) == 1) {
                        return ServerResponse.createBySuccessMessage("签到成功");
                    } else {
                        return ServerResponse.createByErrorMessage("未知错误2，签到失败");
                    }
                }
            }
        } else {
            //重复签到
            return ServerResponse.createByErrorMessage("重复签到");
        }
    }


    //老师开启签到
    // todo
    @Override
    public ServerResponse<Long> startSign(Integer userId, Integer courseId) {

        Date createTime = SignTimeUtil.getCreateTime();
        Long signId = getSignId(userId, createTime);
        //获取签到关闭时间(自动关闭) TODO
        Date closeTime = DateTimeUtil.strToDate(SignTimeUtil.getCloseTime(createTime, "10"));
        Integer count = 0;
        Date endTime = null;
        Sign record = new Sign(signId, courseId, userId, count, createTime, endTime, closeTime);
        int a = signMapper.insert(record);
        Long content = signId;
        //返回签到路径
        return ServerResponse.createBySuccess("开启签到", content);
    }

    /**
     * 产生一个特定格式的SignId
     * 创建时间+userId
     *
     * @return
     */
    public Long getSignId(Integer userId, Date createTime) {
        String signId = DateTimeUtil.dateToSign(createTime);
        // TODO
        Long a = Long.parseLong(signId);
        signId = a.toString() + userId;
        return Long.parseLong(signId);
    }

    //老师关闭签到
    @Override
    public ServerResponse<String> endSign(Long signId) {
        Sign sign = signMapper.selectByPrimaryKey(signId);
        Date nowTime = new Date();
        if (nowTime.getTime() >= sign.getCreateTime().getTime() && nowTime.getTime() <= sign.getCloseTime().getTime()) {
            if (setStatus(signId, nowTime)) {
                return ServerResponse.createBySuccessMessage("设置关闭成功");
            } else {
                return ServerResponse.createByErrorMessage("未知错误，设置失败");
            }
        } else {
            return ServerResponse.createBySuccessMessage("签到已经关闭了");
        }
    }



    /**
     * 更新签到状态及时间
     *
     * @param signId
     * @return
     */
    private boolean setStatus(Long signId, Date endTime) {
        int b = signMapper.setEndTime(signId, endTime);
        if (b <= 0) {
            return false;
        }
        return true;
    }

//    @Override
//    public void timeTask() {
//        //获取签到表实体对象
//        Sign record = new Sign();
//        record = signMapper.selectByMaxTime();
//        Date nowTime = new Date();
//        if (record.getStatus() == 1){
//            System.out.println("无签到");
//        }else {
//            System.out.println("签到......");
//        }
//        if (nowTime.getTime() <= record.getCreateTime().getTime() || nowTime.getTime() >= record.getCloseTime().getTime()&&record.getStatus() == 0) {
//                //更新签到状态，
//                setStatus(record.getSignId(), nowTime);
//        }
//
//}


    @Override
    public ServerResponse<Sign> getNewSign(Integer userId) {
        Sign sign = signMapper.selectByMaxTime(userId);
        //空值判断
        if (sign == null){
            return ServerResponse.createBySuccessMessage("未知错误");
        }
        //最新的签到如果是关闭的则没有开启的签到
        if (sign.getEndTime().getTime() <= sign.getCreateTime().getTime() || sign.getEndTime().getTime() >= sign.getCloseTime().getTime()) {
            return ServerResponse.createBySuccessMessage("暂无开启的签到");
        }
        return ServerResponse.createBySuccess("查询成功",sign);
    }

    @Override
    public ServerResponse<PageInfo> getHistorySign(Integer userId, Long courseId, int pageNum, int pageSize) {
        List<Sign> signList = signMapper.querySign(userId,courseId);
        if (signList == null){
            return ServerResponse.createBySuccessMessage("未知错误");
        }
        if (signList.size() <= 0){
            return ServerResponse.createBySuccessMessage("没有历史签到纪录");
        }
        PageHelper.startPage(pageNum, pageSize);
        PageInfo pageInfo = new PageInfo(signList);
        return ServerResponse.createBySuccess("查询成功",pageInfo);
    }

    @Override
    public ServerResponse<PageInfo> getSignItem(Long signId,int pageNum, int pageSize) {
        List<SignItem> signItemList = signItemMapper.select(signId);
        List<SignItmsInfo> signItmsInfoList = new ArrayList<SignItmsInfo>();
        for (SignItem signItem : signItemList){
            User user = userMapper.selectByPrimaryKey(signItem.getUserId());
            SignItmsInfo signItmsInfo = new SignItmsInfo(signItem,user.getName());
            signItmsInfoList.add(signItmsInfo);

        }
        PageHelper.startPage(pageNum, pageSize);
        PageInfo pageInfo = new PageInfo(signItmsInfoList);
        return ServerResponse.createBySuccess("查询成功",pageInfo);
    }
}
