package com.xianjinxia.trade.shared.enums;

/**
 * Created by fanmaowen on 2017/9/25 0025.
 */
public enum LoanCodeMsgEnum {

    SUCCESS("00", "成功"),

    CHECK_ULTIMATE_ORDER("15","当前有一笔正在运行的订单"),

    CHECK_REJECTORDER("28","被拒的订单冷却时间还没有到期");;


    /**
     * 名称
     */
    private final String code;

    /**
     * 值
     */
    private final String value;

    LoanCodeMsgEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    /**
     * 获取名称
     * @return
     */
    public String getCode() {
        return code;
    }

    /**
     * 获取值
     * @return
     */
    public String getValue() {

        return value;
    }

}