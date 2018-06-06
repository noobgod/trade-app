package com.xianjinxia.trade.shared.utils;

import com.xianjinxia.trade.shared.exception.ServiceException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zengjiaji on 2017/8/31.
 */
public class DateUtil {
	
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	
    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate
     *            较小的时间
     * @param bdate
     *            较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            smdate = sdf.parse(sdf.format(smdate));
            bdate = sdf.parse(sdf.format(bdate));
        } catch (ParseException e) {
            e.printStackTrace();
            throw new ServiceException("日期转换异常");
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    public static Date addDay(Date sourceDate, int addDay){
        Calendar cl= Calendar.getInstance();
        cl.setTime(sourceDate);
        cl.add(Calendar.DAY_OF_MONTH,addDay);
        return cl.getTime();
    }
    
	public static String getDateStr(Date date) {
		if(null == date){
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD);// 格式化为年月
		return sdf.format(date);
	}

}
