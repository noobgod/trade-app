package com.xianjinxia.trade.shared.domain;


import com.xianjinxia.trade.platform.status.PlatformTraceOrderEventEnum;
import com.xianjinxia.trade.shared.enums.TraceOrderEventEnum;
import com.xianjinxia.trade.shared.idempotent.IdempotentKey;

import java.io.Serializable;
import java.util.Date;

/**
 * 交易追踪表
 *
 * @author liuzhifang
 * <p>
 * 2017年9月6日
 */
public class Trace implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 表id
     */
    private Long id;
    /**
     * 追踪号
     */
    @IdempotentKey(order=1)
    private String traceNo;
    /**
     * 订单事件
     */
    @IdempotentKey(order=2)
    private String orderEvent;

    /**
     * 事件文本
     */
    private String eventText;


    /**
     * 事件时间
     */
    private Date eventTime;
    /**
     * 追踪数据，json格式
     */
    private String traceData;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 数据有效性 0 无效  1 有效
     */
    private Boolean dataValid;
    /**
     * 更新时间
     */
    private Date updatedTime;
    /**
     * 创建人
     */
    private String createdUser;

    public String getEventText() {
        return eventText;
    }

    public void setEventText(String eventText) {
        this.eventText = eventText;
    }


    public Trace(String traceNo, String orderEvent, String eventText, Date eventTime, String traceData) {
        this.traceNo = traceNo;
        this.orderEvent = orderEvent;
        this.eventText = eventText;
        this.eventTime = eventTime;
        this.traceData = traceData;
    }

    public Trace(String traceNo, TraceOrderEventEnum orderEventEnum, Date eventTime, String traceData) {
        this.traceNo = traceNo;
        this.orderEvent = orderEventEnum.getCode();
        this.eventText = orderEventEnum.getText();
        this.eventTime = eventTime;
        this.traceData = traceData;
    }
    
    public Trace(String traceNo, PlatformTraceOrderEventEnum platformOrderEventEnum, Date eventTime, String traceData) {
        this.traceNo = traceNo;
        this.orderEvent = platformOrderEventEnum.getCode();
        this.eventText = platformOrderEventEnum.getText();
        this.eventTime = eventTime;
        this.traceData = traceData;
    }

    public Trace() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTraceNo() {
        return traceNo;
    }

    public void setTraceNo(String traceNo) {
        this.traceNo = traceNo;
    }

    public String getOrderEvent() {
        return orderEvent;
    }

    public void setOrderEvent(String orderEvent) {
        this.orderEvent = orderEvent;
    }

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    public String getTraceData() {
        return traceData;
    }

    public void setTraceData(String traceData) {
        this.traceData = traceData;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Boolean getDataValid() {
        return dataValid;
    }

    public void setDataValid(Boolean dataValid) {
        this.dataValid = dataValid;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    @Override
    public String toString() {
        return "Trace [id=" + id + ", traceNo=" + traceNo + ", orderEvent="
                + orderEvent + ", eventTime=" + eventTime + ", traceData="
                + traceData + ", createdTime=" + createdTime + ", dataValid="
                + dataValid + ", updatedTime=" + updatedTime + ", createdUser="
                + createdUser + "]";
    }


}
