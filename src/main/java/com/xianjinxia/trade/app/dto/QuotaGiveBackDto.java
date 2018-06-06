package com.xianjinxia.trade.app.dto;

/**
 * 归还额度数据对象
 * 
 * @author liuzhifang
 *         <p>
 *         2017年9月11日
 */
public class QuotaGiveBackDto {

    /**
     * 用户id
     */
    private Long userId;
    /**
     * 归还金额
     */
    private Integer moneyAmount;
    /**
     * 追踪号
     */
    private String seqNo;

    private Integer productCategory;

    public QuotaGiveBackDto() {}

    public QuotaGiveBackDto(Long userId, Integer moneyAmount, String seqNo,
            Integer productCategory) {
        this.userId = userId;
        this.moneyAmount = moneyAmount;
        this.seqNo = seqNo;
        this.productCategory = productCategory;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(Integer moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public Integer getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(Integer productCategory) {
        this.productCategory = productCategory;
    }

    @Override
    public String toString() {
        return "QuotaGiveBackDto [userId=" + userId + ", moneyAmount=" + moneyAmount + ", seqNo="
                + seqNo + ", productCategory=" + productCategory + "]";
    }


}
