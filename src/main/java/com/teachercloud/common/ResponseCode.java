package com.teachercloud.common;

/**
 * Created by M on 2018/6/23.
 */
public enum ResponseCode {

    /**
     * 成功
     */
    SUCCESS(0,"SUCCESS"),
    /**
     * 失败
     */
    ERROR(1,"ERROR"),
    /**
     * 用户未登录，需要登陆
     */
    NEED_LOGIN(10,"NEED_LOGIN"),
    /**
     * 参数错误
     */
    ILLEGAL_ARGUMENT(2,"ILLEGAL_ARGUMENT");

    private final int code ;
    private final String desc ;

    ResponseCode(int code,String desc){
        this.code = code ;
        this.desc = desc;
    }

    public int getCode() {
        return code ;
    }

    public String getDesc() {
        return desc ;
    }
}
