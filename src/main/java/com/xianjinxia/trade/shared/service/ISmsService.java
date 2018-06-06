package com.xianjinxia.trade.shared.service;

import com.xianjinxia.trade.app.dto.SmsDto;

public interface ISmsService {

    Boolean sendSms(SmsDto smsDto);

}
