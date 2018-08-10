package com.teachercloud.common;


import com.google.common.collect.Sets;

import java.util.Set;

/**
 * Created by M on 2018/6/24.
 */
public class Const {

    public static final String CURRENT_USER = "currentUser";

    public static final String USERNAME = "username";

//    public interface ProductListOrderBy {
//        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_desc", "price_asc");
//    }


    /**
     * 用户角色管理
     */
    public interface Role {
        /**
         * 管理员
         */
        int ROLE_ADMIN = 0;
        /**
         * 教师
         */
        int ROLE_TEACHER = 1;
        /**
         * 学生
         */
        int ROLE_STUDENT = 2;
    }


    /**
     * 文件路径
     */
    public enum FILEPATH {
        VIDEO(1, "D:teachercloud\\file\\upload\\video"),
        HANDOUT(2, "D:teachercloud\\file\\upload\\handout"),
        COURSEWARE(3, "D:teachercloud\\file\\upload\\courseware"),
        FILEIMAGE(4, "D:teachercloud\\file\\upload\\image\\fileimage");
        private int code;
        private String value;

        FILEPATH(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }
    }
    /**
     * 文件类型
     */
    public interface FileType {

        /**
         * 文件主图
         */
        int FILE_MAIN_IMAGE = 0;
        /**
         * 课件
         */
        int TYPE_COURSEWARE = 1;
        /**
         * 视频
         */
        int TYPE_VIDEO = 2;
        /**
         * 讲义
         */
        int TYPE_HANDOUT = 3;

    }

    public enum USERDEFAULT {
        DEFAULT_PASSWD(1, "123456"),
        DEFAULT_QUESTION(2, "问题"),
        DEFAULT_ANSWER(3, "000000");
        private int code;
        private String value;

        USERDEFAULT(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }
    }

//    public enum ProductStatusEnum {
//        ON_SALE(1, "在线");
//
//        private String value;
//        private int code;
//
//        ProductStatusEnum(int code, String value) {
//            this.code = code;
//            this.value = value;
//        }
//
//        public int getCode() {
//            return code;
//        }
//
//        public String getValue() {
//            return value;
//        }
//
//    }


}
