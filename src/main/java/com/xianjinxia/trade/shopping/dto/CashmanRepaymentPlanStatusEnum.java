package com.xianjinxia.trade.shopping.dto;

public enum CashmanRepaymentPlanStatusEnum {

    Waiting(10,"待还款"),
    Part(20,"部分还款"),//目前不支持，待扩展的还款类型
    Repaymented(30,"已还款"),
    Canceled(40,"已取消"); //风控审核失败，还款计划改成已取消


    //YHZ(-20,"已坏账"),
    //YQYHK(34,"逾期已还款")

    private int code;
    private String text;
    CashmanRepaymentPlanStatusEnum(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static String getText(int code){
        CashmanRepaymentPlanStatusEnum[] values = CashmanRepaymentPlanStatusEnum.values();
        for (int i = 0; i < values.length; i++) {
            CashmanRepaymentPlanStatusEnum enumRepaymentPlanStatus = values[i];
            if (enumRepaymentPlanStatus.getCode() == code){
                return enumRepaymentPlanStatus.getText();
            }
        }

        return null;
    }
}

