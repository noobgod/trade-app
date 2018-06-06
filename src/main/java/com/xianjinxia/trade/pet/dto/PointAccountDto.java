package com.xianjinxia.trade.pet.dto;

import java.math.BigDecimal;
import java.util.Date;

public class PointAccountDto {
    private Integer id;

    private String code;

    private String status;

    private BigDecimal totalShare;

    private BigDecimal avaliableShare;

    private BigDecimal frozenShare;

    private BigDecimal ongoingShare;

    private Integer userId;

    private Date openTime;

    private Integer version;

    private String description;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

    private String address;

    private String accountType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public BigDecimal getTotalShare() {
        return totalShare;
    }

    public void setTotalShare(BigDecimal totalShare) {
        this.totalShare = totalShare;
    }

    public BigDecimal getAvaliableShare() {
        return avaliableShare;
    }

    public void setAvaliableShare(BigDecimal avaliableShare) {
        this.avaliableShare = avaliableShare;
    }

    public BigDecimal getFrozenShare() {
        return frozenShare;
    }

    public void setFrozenShare(BigDecimal frozenShare) {
        this.frozenShare = frozenShare;
    }

    public BigDecimal getOngoingShare() {
        return ongoingShare;
    }

    public void setOngoingShare(BigDecimal ongoingShare) {
        this.ongoingShare = ongoingShare;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType == null ? null : accountType.trim();
    }
}