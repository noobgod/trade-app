package com.xianjinxia.trade.shared.remote;

import org.springframework.beans.factory.annotation.Autowired;

import com.xianjinxia.trade.shared.conf.MyRestTemplate;


public abstract class BaseRemoteService {

	@Autowired
	protected MyRestTemplate myRestTemplate;
	
	protected String buildUrl(String relativeUrl){
		StringBuilder stringBuilder = new StringBuilder(getServiceName());
		return stringBuilder.append(relativeUrl).toString();
	}
	
	protected abstract String getServiceName();
}
