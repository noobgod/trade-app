package com.xianjinxia.trade.app.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

/**
 * Created by Administrator on 2017/9/17 0017.
 */
public class ApplyLoanReq  extends BaseRequest {

    @NotNull(message="userId couldn't be null")
    private Long userId;

    @NotNull(message="orderAmount couldn't be null")
    private BigDecimal orderAmount;

    @NotNull(message="periods couldn't be null")
    private String periods;

    @NotNull(message="productId couldn't be null")
    private String productId;

    @NotNull(message="quietPeriod couldn't be null")
    private String quietPeriod;

    /**
     * 商户号
     */
    private String merchantNo;

    /**
     * 是否购买提速卡
     */
    private String buyCard;

    /**
     * 提速卡ID
     */
    private String speedCardId;

    public String getBuyCard() {
        return buyCard;
    }

    public void setBuyCard(String buyCard) {
        this.buyCard = buyCard;
    }

    public String getQuietPeriod() {
        return quietPeriod;
    }

    public void setQuietPeriod(String quietPeriod) {
        this.quietPeriod = quietPeriod;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getPeriods() {
        return periods;
    }

    public void setPeriods(String periods) {
        this.periods = periods;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getSpeedCardId() {
        return speedCardId;
    }

    public void setSpeedCardId(String speedCardId) {
        this.speedCardId = speedCardId;
    }

    @Override
    public String toString() {
        return "ApplyLoanReq{" +
                "userId=" + userId +
                ", orderAmount=" + orderAmount +
                ", periods='" + periods + '\'' +
                ", productId='" + productId + '\'' +
                ", quietPeriod='" + quietPeriod + '\'' +
                ", merchantNo='" + merchantNo + '\'' +
                ", buyCard='" + buyCard + '\'' +
                ", speedCardId='" + speedCardId + '\'' +
                '}';
    }
}
