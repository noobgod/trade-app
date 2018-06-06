package com.xianjinxia.trade.shared.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 生成放款通知签名
 * 
 * @author liuzhifang
 *
 *         2017年9月20日
 */
public class PaymentSignUtil {

    public static String sign(String bizId, Integer loanAmount, String bankcardNo) {
        if (StringUtils.isEmpty(bizId)) {
            return "";
        }
        if (loanAmount == null) {
            return "";
        }
        if (StringUtils.isEmpty(bankcardNo)) {
            return "";
        }
        StringBuffer stb = new StringBuffer();
        stb.append(bizId).append(loanAmount).append(bankcardNo);
        return Md5Util.md5(stb.toString());

    }


}
