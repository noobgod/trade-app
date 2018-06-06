package com.xianjinxia.trade.app.request;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: cff
 * Date: 2017-12-25
 * Time: 下午 4:13
 */
public class OrderApplyReq extends BaseRequest{

    private String applyStartTime;
    private String applyEndTime;
    private String loanStartTime;
    private String loanEndTime;
    private String userName;
    private String userPhone;
    private String status;
    private Long loanId;
    /**
     * 页码
     */
    @ApiModelProperty(name = "pageNum",value = "页码",example = "1",required = true,dataType = "Integer")
    @NotNull(message = "pageNum couldn't be null")
    private Integer pageNum;
    /**
     * 每页长度
     */
    @ApiModelProperty(name = "pageSize",value = "每页长度",example = "5",required = true,dataType = "Integer")
    @NotNull(message = "pageSize couldn't be null")
    private Integer pageSize;


    public String getApplyStartTime() {
        return applyStartTime;
    }

    public void setApplyStartTime(String applyStartTime) {
        this.applyStartTime = applyStartTime;
    }

    public String getApplyEndTime() {
        return applyEndTime;
    }

    public void setApplyEndTime(String applyEndTime) {
        this.applyEndTime = applyEndTime;
    }

    public String getLoanStartTime() {
        return loanStartTime;
    }

    public void setLoanStartTime(String loanStartTime) {
        this.loanStartTime = loanStartTime;
    }

    public String getLoanEndTime() {
        return loanEndTime;
    }

    public void setLoanEndTime(String loanEndTime) {
        this.loanEndTime = loanEndTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
