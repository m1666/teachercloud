package com.teachercloud.controller.portal;

import com.teachercloud.common.Const;
import com.teachercloud.common.ResponseCode;
import com.teachercloud.common.ServerResponse;
import com.teachercloud.pojo.Sign;
import com.teachercloud.pojo.SignItem;
import com.teachercloud.pojo.User;
import com.teachercloud.service.ISigneService;
import com.teachercloud.util.CusAccessObjectUtil;
import com.teachercloud.util.DateTimeUtil;
import com.teachercloud.util.SignTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * Created with IDEA
 * @author:LiuYiBo(小博)
 * Date:2018/7/20
 * Time:11:05
 */



@Controller
@RequestMapping("/sign")
public class SignController {

    @Autowired
    private ISigneService signeService;

    /**
     * 学生签到
     * @param signId
     * @param request
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/signing.do", method = RequestMethod.POST)
    @ResponseBody
    ServerResponse<String> signe(Long signId,HttpServletRequest request,HttpSession httpSession) {
        //获取用户对象
        User user = (User)httpSession.getAttribute(Const.CURRENT_USER);
        if (user != null) {
            if (user.getRole() == 2){
                //学生签到的时间
                Date signTime = new Date();
                //获取当前签到状态
                Integer status = signeService.getStatus(signId);
                if (status == 0) {
                    //获取学生ip地址
                    String userIp = CusAccessObjectUtil.getIpAddress(request);
                    return signeService.signeCount(signId,user.getId(),userIp,signTime);
                } else {
                    return ServerResponse.createByErrorMessage("暂无开启的签到");
                }
            }else {
                return ServerResponse.createByErrorMessage("老师别闹。。。");
            }
        }else {
            return ServerResponse.createByErrorMessage(ResponseCode.NEED_LOGIN.getDesc());
        }

    }

    /**
     * 教师开启签到
     * @param courseId
     * @param session
     * @return
     */
    @RequestMapping(value = "/startSign.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> doStartSign(Integer courseId, HttpSession session){
        //获取session中的user对象
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user != null) {
            //判断是否是教师身份，只有教师身份可以开启签到
            if (user.getRole() == 1) {
                //开启签到
                return signeService.startSign(user.getId(), courseId);
            } else {
                return ServerResponse.createByErrorMessage("没有该权限");
            }
        }else {
            return ServerResponse.createByErrorMessage(ResponseCode.NEED_LOGIN.getDesc());
        }

    }

    /**
     * 教师根据实际情况关闭签到
     * @param signId
     * @return
     */
    @RequestMapping(value = "/closeSign.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> closeSign(@RequestParam("signId")Long signId,HttpSession session){
        //获取session中的user对象
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user != null) {
            //判断是否是教师身份，只有教师身份可以手动关闭签到
            if (user.getRole() == 1) {
                return signeService.endSign(signId);
            } else {
                return ServerResponse.createByErrorMessage("没有该权限");
            }
        }else {
            return ServerResponse.createByErrorMessage(ResponseCode.NEED_LOGIN.getDesc());
        }
    }

    /**
     * 获取当前签到信息
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/newSign.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<Sign> getSign(HttpSession httpSession){
        User user = (User)httpSession.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage(ResponseCode.NEED_LOGIN.getDesc());
        }
        if (user.getRole() == 2) {
            return ServerResponse.createByErrorMessage("没有该权限");
        }
        return signeService.getNewSign();
    }

    /**
     * 获取历史签到记录
     * @param httpSession
     * @param courseId
     * @param pagesize
     * @param startrow
     * @return
     */
    @RequestMapping(value = "/gethistorysign.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<Sign>> getHistorySign(HttpSession httpSession, @RequestParam("courseId")Long courseId,@RequestParam("pagesize") Integer pagesize, @RequestParam("startrow")Integer startrow){
        User user = (User)httpSession.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage(ResponseCode.NEED_LOGIN.getDesc());
        }
        if (user.getRole() == 2) {
            return ServerResponse.createByErrorMessage("没有该权限");
        }
        return signeService.getHistorySign(user.getId(),courseId,pagesize,startrow);
    }

    /**
     * 查询签到详细信息
     * @param httpSession
     * @param signId
     * @return
     */
    @RequestMapping(value = "/getsignitems.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<SignItem>> getSignItems(HttpSession httpSession,@RequestParam("signId")Long signId,@RequestParam("pagesize") Integer pagesize, @RequestParam("startrow")Integer startrow){
        User user = (User)httpSession.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage(ResponseCode.NEED_LOGIN.getDesc());
        }
        if (user.getRole() == 2) {
            return ServerResponse.createByErrorMessage("没有该权限");
        }
        return signeService.getSignItem(signId,pagesize,startrow);
    }
}

