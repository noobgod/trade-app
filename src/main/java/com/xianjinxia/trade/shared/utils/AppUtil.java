package com.xianjinxia.trade.shared.utils;

import java.util.Calendar;
import java.util.Date;

public class AppUtil {
    /**
     * 根据date 以及 已经重试的次数，计算下一次重试的时间
     * @param date
     * @param hasTryTimes 已经重试的次数
     * @return
     */
    public static Date getNextRetryTime(Date date, int hasTryTimes) {
        Calendar cl= Calendar.getInstance();
        cl.setTime(date);
        if(hasTryTimes <=1){
            cl.add(Calendar.MINUTE,10);
            return cl.getTime();
        }
        else if(hasTryTimes<=3){
            cl.add(Calendar.MINUTE,20);
            return cl.getTime();
        }
        else{
            cl.add(Calendar.MINUTE,30);
            return cl.getTime();
        }

    }
}
