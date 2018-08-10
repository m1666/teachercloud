package com.teachercloud.service.impl;

import com.teachercloud.common.ServerResponse;
import com.teachercloud.dao.SignItemMapper;
import com.teachercloud.dao.SignMapper;
import com.teachercloud.pojo.Sign;
import com.teachercloud.pojo.SignItem;
import com.teachercloud.service.ISigneService;
import com.teachercloud.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
            //更新签到状态
            setStatus(signId, signTime);
        }
        Date nowTime = new Date();
        //判断签到状态是否开始
        if (nowTime.getTime() >= record.getCreateTime().getTime() || nowTime.getTime() <= record.getCloseTime().getTime()) {
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
        } else {
            //签到关闭
            return ServerResponse.createByErrorMessage("暂无开启的签到");
        }
    }

    //老师开启签到
    // todo
    @Override
    public ServerResponse<String> startSign(Integer userId, Integer courseId) {

        Date createTime = SignTimeUtil.getCreateTime();
        Long signId = getSignId(userId, createTime);
        //获取签到关闭时间(自动关闭) TODO
        Date closeTime = DateTimeUtil.strToDate(SignTimeUtil.getCloseTime(createTime, "10"));
        Integer status = 0;
        Integer count = 0;
        Date endTime = null;
        Sign record = new Sign(signId, courseId, userId, status, count, createTime, endTime, closeTime);
        int a = signMapper.insert(record);
        String content = "http://localhost:8080/sign/signing.do?signId=" + signId;
        //signMapper.insertImgPath(signId,imgPath);
        //返回二维码存放路径
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
        Integer status = sign.getStatus();
        Date nowTime = new Date();
        if (nowTime.getTime() >= sign.getCreateTime().getTime() || nowTime.getTime() <= sign.getCloseTime().getTime()) {
            Date endTime = new Date();
            if (setStatus(signId, endTime)) {
//                DeletFileUtil.deleteFile(imgPath);
//                signMapper.deleteImgPath(signId);
                return ServerResponse.createBySuccessMessage("设置关闭成功");
            } else {
                return ServerResponse.createByErrorMessage("未知错误，设置失败");
            }
        } else {
            return ServerResponse.createBySuccessMessage("签到已经关闭了");
        }
    }

    /**
     * 查询签到状态
     *
     * @param signId
     * @return
     */
    @Override
    public int getStatus(Long signId) {
        Sign sign = signMapper.selectByPrimaryKey(signId);
        return sign.getStatus();
    }


    /**
     * 更新签到状态及时间
     *
     * @param signId
     * @return
     */
    private boolean setStatus(Long signId, Date endTime) {
        int a = signMapper.updateSatus(signId);
        int b = signMapper.setEndTime(signId, endTime);
        if (a <= 0 || b <= 0) {
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
    public ServerResponse<Sign> getNewSign() {
        Sign sign = signMapper.selectByMaxTime();
        //空值判断
        if (sign == null){
            return ServerResponse.createBySuccessMessage("未知错误");
        }
        //最新的签到如果是关闭的则没有开启的签到
        if (sign.getStatus() == 1){
            return ServerResponse.createBySuccessMessage("暂无开启的签到");
        }
           return ServerResponse.createBySuccess("查询成功",sign);
    }

    @Override
    public ServerResponse<List<Sign>> getHistorySign(Integer userId, Long courseId, Integer pagesize, Integer startrow) {

        if (pagesize == null){
           pagesize = 10;
       }
        if (startrow == null){
            startrow = 0;
        }

        List<Sign> signList = signMapper.querySign(userId,courseId,pagesize,startrow);
        if (signList == null){
            return ServerResponse.createBySuccessMessage("未知错误");
        }
        if (signList.size() <= 0){
            return ServerResponse.createBySuccessMessage("没有历史签到纪录");
        }
        return ServerResponse.createBySuccess("查询成功",signList);
    }

    @Override
    public ServerResponse<List<SignItem>> getSignItem(Long signId,Integer pagesize,Integer startrow) {
        List<SignItem> signItemList = signItemMapper.select(signId,pagesize,startrow);
        //空值判断
        if (signItemList == null){
            return ServerResponse.createBySuccessMessage("未知错误");
        }
        return ServerResponse.createBySuccess("查询成功",signItemList);
    }
}
