package com.xianjinxia.trade.activity.request;

import java.util.Date;

/**
 * 支付回調
 *
 * @author JaJian
 * @create 2018-05-30 11:43
 **/
public class SeckillOrderRepayCallbackRequest {

    private Integer	orderDetailId;	// 详情id Y
    private String	orderNo;		// 支付中心订单号 varchar Y
    private Long	payAmount;		// 金额（分） Long Y
    private String	payType;		// 支付类型 int Y
    private String	payName;		// 支付类型名称 varchar Y
    private String	code;			// 交易结果 varchar Y
    private String	msg;			// 交易结果描述 varchar Y
    private Date orderTime;		// 交易时间 date Y
    private Integer	outordersId;	// 订单表id N
    private String	exextData;
    private String requestSource;//	来源ID
    private String thirdOrderNo;//第三方订单号

    public Integer getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Integer orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Long payAmount) {
        this.payAmount = payAmount;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Integer getOutordersId() {
        return outordersId;
    }

    public void setOutordersId(Integer outordersId) {
        this.outordersId = outordersId;
    }

    public String getExextData() {
        return exextData;
    }

    public void setExextData(String exextData) {
        this.exextData = exextData;
    }

    public String getRequestSource() {
        return requestSource;
    }

    public void setRequestSource(String requestSource) {
        this.requestSource = requestSource;
    }

    public String getThirdOrderNo() {
        return thirdOrderNo;
    }

    public void setThirdOrderNo(String thirdOrderNo) {
        this.thirdOrderNo = thirdOrderNo;
    }
}
