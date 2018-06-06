package com.xianjinxia.trade.shared.domain;

import java.util.Date;

/**
 * 放款资金渠道
 * @author liuzhifang
 *
	2017年9月4日
 */
public class FinanceChannelConf {
	private Long id;

	private Date createdTime;

	private Boolean dataValid;

	private Date updateTime;

	private String createdUser;

	private Byte fundsRate;

	private Byte settlementPeriod;

	private Integer dailyLimit;

	private Integer collateralCompensation;

	private Integer loanPrescription;

	private Byte bondRatio;

	private String channelName;

	private String channelFlag;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser == null ? null : createdUser.trim();
	}

	public Byte getFundsRate() {
		return fundsRate;
	}

	public void setFundsRate(Byte fundsRate) {
		this.fundsRate = fundsRate;
	}

	public Byte getSettlementPeriod() {
		return settlementPeriod;
	}

	public void setSettlementPeriod(Byte settlementPeriod) {
		this.settlementPeriod = settlementPeriod;
	}

	public Integer getDailyLimit() {
		return dailyLimit;
	}

	public void setDailyLimit(Integer dailyLimit) {
		this.dailyLimit = dailyLimit;
	}

	public Integer getCollateralCompensation() {
		return collateralCompensation;
	}

	public void setCollateralCompensation(Integer collateralCompensation) {
		this.collateralCompensation = collateralCompensation;
	}

	public Integer getLoanPrescription() {
		return loanPrescription;
	}

	public void setLoanPrescription(Integer loanPrescription) {
		this.loanPrescription = loanPrescription;
	}

	public Byte getBondRatio() {
		return bondRatio;
	}

	public void setBondRatio(Byte bondRatio) {
		this.bondRatio = bondRatio;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName == null ? null : channelName.trim();
	}

	public String getChannelFlag() {
		return channelFlag;
	}

	public void setChannelFlag(String channelFlag) {
		this.channelFlag = channelFlag == null ? null : channelFlag.trim();
	}

	@Override
	public String toString() {
		return "FinanceChannelConf{" +
				"id=" + id +
				", createdTime=" + createdTime +
				", dataValid=" + dataValid +
				", updateTime=" + updateTime +
				", createdUser='" + createdUser + '\'' +
				", fundsRate=" + fundsRate +
				", settlementPeriod=" + settlementPeriod +
				", dailyLimit=" + dailyLimit +
				", collateralCompensation=" + collateralCompensation +
				", loanPrescription=" + loanPrescription +
				", bondRatio=" + bondRatio +
				", channelName='" + channelName + '\'' +
				", channelFlag='" + channelFlag + '\'' +
				'}';
	}
}
