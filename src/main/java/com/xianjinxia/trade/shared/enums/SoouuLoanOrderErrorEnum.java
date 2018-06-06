package com.xianjinxia.trade.shared.enums;


public enum SoouuLoanOrderErrorEnum {

    ERR_1206("soouu_1206", "查询错误"),
    ERR_2100("soouu_2100", "商品不存在或无法购买"),
    ERR_2101("soouu_2101", "商品在禁售时间段内，无法购买"),
    ERR_2102("soouu_2102", "该帐号没有充值区域商品权限"),
    ERR_2103("soouu_2103", "商品购买策略限制"),
    ERR_2104("soouu_2104", "该商品正在维护"),
    ERR_2106("soouu_2106", "商品黑名单"),
    ERR_2107("soouu_2107", "商品类型错误"),
    ERR_2108("soouu_2108", "库存不足"),
    ERR_2109("soouu_2109", "账户余额不足"),
    ERR_2110("soouu_2110", "站点余额不足"),
    ERR_2111("soouu_2111", "客户外部系统订单号已存在"),
    ERR_2115("soouu_2115", "添加订单失败"),
    ERR_2118("soouu_2118", "负利润报警"),
    ERR_2407("soouu_2407", "执行下单超时，请查单确认下单结果"),
    ERR_3000("soouu_3000", "风控系统拦截"),
    ERR_2200("soouu_2200", "外部系统订单号不存在"),
    ;

    private String code;
    private String value;

    SoouuLoanOrderErrorEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return this.code;
    }

    public String getValue() {
        return this.value;
    }
}