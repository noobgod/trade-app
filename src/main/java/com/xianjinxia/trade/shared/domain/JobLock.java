package com.xianjinxia.trade.shared.domain;

import java.util.Date;

public class JobLock {
	
	private Long id;
	
	private String jobName;
	
	private Long ukToken;
	
	private Date createdTime;
	
	private Date updatedTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public Long getUkToken() {
		return ukToken;
	}

	public void setUkToken(Long ukToken) {
		this.ukToken = ukToken;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}
	

}
