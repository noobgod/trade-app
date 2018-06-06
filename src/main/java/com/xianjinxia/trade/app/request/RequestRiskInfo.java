/****************************************
 * Copyright (c) 2017 XinJinXia.
 * All rights reserved.
 * Created on 2017年9月14日
 * 
 * Contributors:
 * tennyqin - initial implementation
 ****************************************/
package com.xianjinxia.trade.app.request;


/**
 * @title RequestRiskInfo.java
 *
 * @author tennyqin
 * @version 1.0
 * @created 2017年9月14日
 */
public class RequestRiskInfo {

	private Long userId;
	
	private String refactorAssetId;
	
	private Integer moneyAmount;

	public RequestRiskInfo(Long userId, String refactorAssetId, Integer moneyAmount) {
		this.userId = userId;
		this.refactorAssetId = refactorAssetId;
		this.moneyAmount = moneyAmount;
	}

	public RequestRiskInfo() {
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getRefactorAssetId() {
		return refactorAssetId;
	}

	public void setRefactorAssetId(String refactorAssetId) {
		this.refactorAssetId = refactorAssetId;
	}

	public Integer getMoneyAmount() {
		return moneyAmount;
	}

	public void setMoneyAmount(Integer moneyAmount) {
		this.moneyAmount = moneyAmount;
	}

	@Override
	public String toString() {
		return "RequestRiskInfo [userId=" + userId + ", refactorAssetId="
				+ refactorAssetId + ", moneyAmount=" + moneyAmount + "]";
	}
	
}
