package com.teachercloud.util;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.text.ParsePosition;
import java.util.Date;
/**
 * Created with IDEA
 * author:LiuYiBo(小博)
 * Date:2018/7/20
 * Time:15:34
 */
public class SignTimeUtil {


    /**
     * @author 侯宇飞
     * 日期: 2018-07-20
     * 时间: 10:37
     */

        public static String pattern = "yyyy-MM-dd";

        /**
         * 将时间类型换成字符串类型
         *
         * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
         */



        /**
         * 获取现在时间
         *
         * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
         */
        public static Date getCreateTime() {
            Date currentTime = new Date();
            return currentTime;
        }

        /**
         * 时间后推n分钟,time.
         */
        public static String getCloseTime(Date nows, String time) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String now = sdf.format(nows);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String mydate = "";
            try {
                Date date1 = format.parse(now);
                long Time = (date1.getTime() / 1000) + Integer.parseInt(time) * 60;
                date1.setTime(Time * 1000);
                mydate = format.format(date1);
            } catch (Exception e) {
            }
            return mydate;
        }

    }

