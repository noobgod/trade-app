package com.xianjinxia.trade.app.dto;


public class SmsDto {

    private String msgSource= "trade-app";

    private String msgBussinessId;

    private String telephone;

    private String msgTemplateCode;

    private String values;

    private String merchantNo;
    public SmsDto(String msgBusinessId, String telephone, String msgTemplateCode, String values) {
        this.msgBussinessId=msgBusinessId;
        this.telephone = telephone;
        this.msgTemplateCode = msgTemplateCode;
        this.values = values;
    }

    public SmsDto(String msgBussinessId, String telephone, String msgTemplateCode, String values, String merchantNo) {
        this.msgBussinessId = msgBussinessId;
        this.telephone = telephone;
        this.msgTemplateCode = msgTemplateCode;
        this.values = values;
        this.merchantNo = merchantNo;
    }

    public String getMsgSource() {
        return msgSource;
    }

    public String getMsgBussinessId() {
        return msgBussinessId;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getMsgTemplateCode() {
        return msgTemplateCode;
    }

    public String getValues() {
        return values;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }
}
