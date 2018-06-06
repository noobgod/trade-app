package com.xianjinxia.trade.pet.dto;

import com.xianjinxia.trade.shared.utils.UniqueIdUtil;

/**
 * Created by Myron on 2018/2/28.
 */
public class BaseAssetReqDto<T> {
    private String channel = "trd";
    private String txNo = UniqueIdUtil.getPetTradeNoUniqueId();
    private T txContent;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getTxNo() {
        return txNo;
    }

    public void setTxNo(String txNo) {
        this.txNo = txNo;
    }

    public T getTxContent() {
        return txContent;
    }

    public void setTxContent(T txContent) {
        this.txContent = txContent;
    }
}
