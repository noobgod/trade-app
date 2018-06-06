package com.xianjinxia.trade.app.dto;


import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

import com.xianjinxia.trade.app.request.BaseRequest;

/*
 * 接收风控审核结果
 */
public class TradeDto extends BaseRequest {


    /**
     * 风控通过
     */
    public static final String RISK_SUCC = "0000";
    /**
     * 风控失败
     */
    public static final String RISK_FAILED = "9991";

    /**
     * 借款业务流水号
     */
    @NotNull(message = "assetOrderId couldn't be null")
    @ApiModelProperty(name = "借款业务流水号", value = "借款业务流水号", example = "lo20171017185806944tx",
            required = true, dataType = "String")
    private String assetOrderId;

    /**
     * 风控结果 0000成功,9991失败
     */
    @NotNull(message = "code couldn't be null")
    @ApiModelProperty(name = "code", value = "风控结果", example = "0000", required = true,
            dataType = "String")
    private String code;
    /**
     * 风控审核结果描述
     */
    @ApiModelProperty(name = "message", value = "风控审核失败错误描述", example = "9991", required = false,
            dataType = "String")
    private String message;

    /**
     * 风控审核失败时间
     */
    private Long reviewFailTime;

    public Long getReviewFailTime() {
        return reviewFailTime;
    }

    public void setReviewFailTime(Long reviewFailTime) {
        this.reviewFailTime = reviewFailTime;
    }

    public String getAssetOrderId() {
        return assetOrderId;
    }

    public void setAssetOrderId(String assetOrderId) {
        this.assetOrderId = assetOrderId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "TradeDto{" +
                "assetOrderId='" + assetOrderId + '\'' +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", reviewFailTime=" + reviewFailTime +
                '}';
    }
}
