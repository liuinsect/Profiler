package com.liusoft.tools.profiler.utils;

/**
 * 自己实现StringUtils
 * Created with IntelliJ IDEA.
 * User: liukunyang
 * Date: 14-2-21
 * Time: 下午4:29
 * To change this template use File | Settings | File Templates.
 */
public class StringUtils {

    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(String str) {
        return !StringUtils.isBlank(str);
    }
}
