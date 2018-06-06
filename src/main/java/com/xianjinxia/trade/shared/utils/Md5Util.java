package com.xianjinxia.trade.shared.utils;

/**
 * Created by xuehan on 2017/9/11.
 */

import java.security.MessageDigest;

import org.apache.commons.lang3.StringUtils;

/**
 * md5工具类
 */
public class Md5Util {

    public static String md5(String value) {
        if (StringUtils.isEmpty(value)) {
            return "";
        }
        return md5LowerCase(value).toUpperCase();
    }

    private static String md5LowerCase(String value) {

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(value.getBytes("utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        byte[] digest = md.digest();
        StringBuffer hexValue = new StringBuffer();

        for (int i = 0; i < digest.length; i++) {
            int val = ((int) digest[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }

        return hexValue.toString();
    }

}
