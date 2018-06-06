package com.xianjinxia.trade.shared.utils;

import java.math.BigDecimal;

/**
 * 计算器工具类
 * 提供准确精度的 加减乘除
 * 默认除法保留2位小数，四舍五入
 * @author mjh
 */
public class MoneyUtil {

	
    /**
     * 除法运算 当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
     * @param v1           被除数
     * @param v2            除数
     * @param scale   表示表示需要精确到小数点以后几位。
     * @return        两个参数的商
     */
	public static BigDecimal div(String v1, String v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"精度必须是正整数或者0");
		}
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP);
	}



	/**
	 * 把分转换为元
	 * @param money
	 * @return
	 */
	public static BigDecimal changeCentToYuan(Integer money ){
		return div(String.valueOf(money),"100",2);
	}

	/**
	 * 把元转换为分
	 * @param money
	 * @return
	 */
	public static Integer changeYuanToCent(BigDecimal money){
		return money.multiply(new BigDecimal("100")).intValue();
	}


}
