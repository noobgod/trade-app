package com.xianjinxia.trade.shared.service.impl;

import com.xianjinxia.trade.app.dto.SmsDto;
import com.xianjinxia.trade.shared.conf.ExtProperties;
import com.xianjinxia.trade.shared.conf.MyRestTemplate;
import com.xianjinxia.trade.shared.service.ISmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SmsServiceImpl implements ISmsService {

    private Logger logger= LoggerFactory.getLogger(SmsServiceImpl.class);

    @Autowired
    private MyRestTemplate myRestTemplate;

    @Autowired
    private ExtProperties extProperties;

    @Override
    public Boolean sendSms(SmsDto smsDto) {
        try {
            myRestTemplate.httpPostWithAbsoluteUrl(extProperties.getSmsConfig().getUrl(),
                    smsDto, new ParameterizedTypeReference<Map>(){});
            return true;
        } catch (Exception e) {
            logger.error("send sms fail,templateCode:{},telephone:{}",smsDto.getMsgTemplateCode(),smsDto.getTelephone(),e);
            return false;
        }
    }
}
