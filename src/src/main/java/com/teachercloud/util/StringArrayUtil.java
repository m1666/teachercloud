package com.teachercloud.util;

/**
 * User: mann
 * DateTime: 2018/7/30 15:30
 */
public class StringArrayUtil {
    public static int[] stringToInteger(String courseIdStr) {
        //根据“,”拆分字符串
        String[] args = courseIdStr.split(",");
        int[] courseIds = new int[args.length];
        for (int i = 0;i<args.length;i++) {
            Integer courseId = Integer.parseInt(args[i]);
            courseIds[i] = courseId;

        }
        return courseIds;
    }


    public static Long[] stringToLong(String courseIdStr) {
        //根据“,”拆分字符串
        String[] args = courseIdStr.split(",");
        Long[] courseIds = new Long[args.length];
        for (int i = 0;i<args.length;i++) {
            Long courseId = Long.parseLong(args[i]);
            courseIds[i] = courseId;
        }
        return courseIds;
    }

}
