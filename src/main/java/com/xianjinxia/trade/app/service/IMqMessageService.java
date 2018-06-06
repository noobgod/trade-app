package com.xianjinxia.trade.app.service;

import com.xjx.mqclient.pojo.MqMessage;

import java.util.List;

public interface IMqMessageService {

    void sendMessage(MqMessage mqMessage);

    void sendMessageList(List<MqMessage> mqMessage);
}
