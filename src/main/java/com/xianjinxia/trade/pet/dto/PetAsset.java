package com.xianjinxia.trade.pet.dto;

import com.google.gson.JsonObject;

import java.util.Date;

public class PetAsset {
    private Long id;

    private String name;

    private String code;

    private Integer generateLevel;

    private String status;

    private String source;

    private Long userId;

    private Date endTime;

    private String varity;

    private JsonObject propertyDetail;

    private String description;

    private Integer version;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

    private String txAbleStatus;

    private String address;

    private Integer fatherId;

    private Integer motherId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Integer getGenerateLevel() {
        return generateLevel;
    }

    public void setGenerateLevel(Integer generateLevel) {
        this.generateLevel = generateLevel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getVarity() {
        return varity;
    }

    public void setVarity(String varity) {
        this.varity = varity == null ? null : varity.trim();
    }

    public JsonObject getPropertyDetail() {
        return propertyDetail;
    }

    public void setPropertyDetail(JsonObject propertyDetail) {
        this.propertyDetail = propertyDetail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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

    public String getTxAbleStatus() {
        return txAbleStatus;
    }

    public void setTxAbleStatus(String txAbleStatus) {
        this.txAbleStatus = txAbleStatus == null ? null : txAbleStatus.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getFatherId() {
        return fatherId;
    }

    public void setFatherId(Integer fatherId) {
        this.fatherId = fatherId;
    }

    public Integer getMotherId() {
        return motherId;
    }

    public void setMotherId(Integer motherId) {
        this.motherId = motherId;
    }
}