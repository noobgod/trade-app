package com.xianjinxia.trade.shared.enums;

/**
 * Created by fanmaowen on 2017/12/13 0013.
 */
public enum ManualReviewPassEnum {
    MANUAL_REVIEW_PASS(1, "人工审核通过"),
    MANUAL_REVIEW_FAIL(0, "人工审核未通过");

    private int		code;
    private String	value;

    ManualReviewPassEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
