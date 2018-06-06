package com.xianjinxia.trade.app.dto;

/**
 * Created by fanmaowen on 2017/11/2 0002.
 */
public class BigAmountLoanOrderComfirmRiskDto {
    // 流水号SeqNo
    private String assetOrderId ;
    // 手机号
    private String userPhone;
    // 申请金额
    private String currentApplyAmount;
    // 申请期数
    private String currentApplyPeriods;
    //商户类型(现金侠为001)
    private String businessType;

    public BigAmountLoanOrderComfirmRiskDto(String assetOrderId, String userPhone, String currentApplyAmount, String currentApplyPeriods, String businessType) {
        this.assetOrderId = assetOrderId;
        this.userPhone = userPhone;
        this.currentApplyAmount = currentApplyAmount;
        this.currentApplyPeriods = currentApplyPeriods;
        this.businessType = businessType;
    }

    public enum BusinessTypeEnum {

        XJX("SOURCE_XJX_BIG", "现金侠");


        private String code;
        private String value;

        BusinessTypeEnum(String code, String value) {
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

    public String getAssetOrderId() {
        return assetOrderId;
    }

    public void setAssetOrderId(String assetOrderId) {
        this.assetOrderId = assetOrderId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getCurrentApplyAmount() {
        return currentApplyAmount;
    }

    public void setCurrentApplyAmount(String currentApplyAmount) {
        this.currentApplyAmount = currentApplyAmount;
    }

    public String getCurrentApplyPeriods() {
        return currentApplyPeriods;
    }

    public void setCurrentApplyPeriods(String currentApplyPeriods) {
        this.currentApplyPeriods = currentApplyPeriods;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }
}
