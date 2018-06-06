package com.xianjinxia.trade.app.service.impl;

import com.xianjinxia.trade.app.service.IMqMessageService;
import com.xjx.mqclient.pojo.MqMessage;
import com.xjx.mqclient.service.MqClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MqMessageServiceImpl implements IMqMessageService {

    @Autowired
    private MqClient mqClient;

    @Override
    @Transactional
    public void sendMessage(MqMessage mqMessage) {
        mqClient.sendMessage(mqMessage);
    }

    @Override
    public void sendMessageList(List<MqMessage> mqMessage) {
        mqClient.sendMessagesByCommit(mqMessage, true);
    }
}
