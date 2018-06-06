package com.xianjinxia.trade.platform.request;

import com.xianjinxia.trade.app.request.BaseRequest;
import com.xianjinxia.trade.shared.idempotent.IdempotentKey;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by fanmaowen on 2017/11/25 0025.
 * 第三方返回绑卡结果请求
 */
public class BindCardResultReq extends BaseRequest {

    @NotNull(message="order_no couldn't be null")
    @ApiModelProperty(name = "order_no",value = "订单编号",example = "222",required = true,dataType = "String")
    @IdempotentKey(order=1)
    private String order_no;

    @NotNull(message="bind_status couldn't be null")
    @ApiModelProperty(name = "bind_status",value = "绑卡状态",example = "1",required = true,dataType = "Integer")
    private Integer bind_status;

    @ApiModelProperty(name = "bind_card_type",value = "绑卡的卡类型",example = "1",required = true,dataType = "Integer")
    private Integer bind_card_type ;

    @ApiModelProperty(name = "fail_reason",value = "失败原因",example = "",required = true,dataType = "String")
    private String fail_reason ;

    @ApiModelProperty(name = "bank_code",value = "银行code", example = "",required = true,dataType = "String")
    private String  bank_code ;

    @ApiModelProperty(name = "bank_card_id",value = "银行卡id",example = "",required = true,dataType = "String")
    private Long  bank_card_id;

    @ApiModelProperty(name = "bank_card",value = "银行卡号后4位",example = "",required = true,dataType = "String")
    private String bank_card;

    public Integer getBind_status() {
        return bind_status;
    }

    public void setBind_status(Integer bind_status) {
        this.bind_status = bind_status;
    }

    public Integer getBind_card_type() {
        return bind_card_type;
    }

    public void setBind_card_type(Integer bind_card_type) {
        this.bind_card_type = bind_card_type;
    }

    public String getFail_reason() {
        return fail_reason;
    }

    public void setFail_reason(String fail_reason) {
        this.fail_reason = fail_reason;
    }

    public String getBank_code() {
        return bank_code;
    }

    public void setBank_code(String bank_code) {
        this.bank_code = bank_code;
    }

    public Long getBank_card_id() {
        return bank_card_id;
    }

    public void setBank_card_id(Long bank_card_id) {
        this.bank_card_id = bank_card_id;
    }

    public String getBank_card() {
        return bank_card;
    }

    public void setBank_card(String bank_card) {
        this.bank_card = bank_card;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    @Override
    public String toString() {
        return "BindCardResultReq{" +
                "order_no='" + order_no + '\'' +
                ", bind_status=" + bind_status +
                ", bind_card_type=" + bind_card_type +
                ", fail_reason='" + fail_reason + '\'' +
                ", bank_code='" + bank_code + '\'' +
                ", bank_card_id='" + bank_card_id + '\'' +
                ", bank_card='" + bank_card + '\'' +
                '}';
    }
}
