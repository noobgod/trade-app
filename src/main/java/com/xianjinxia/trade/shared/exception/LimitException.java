package com.xianjinxia.trade.shared.exception;

/**
 * 自定义限流异常
 * @author ganminghui
 */
public class LimitException extends RuntimeException{

    /* 默认限流提醒消息 */ public static final String DEFAULT_LIMITER_MSG = "请求过于频繁, 请稍后在试!!!";

    public LimitException(String message) { super(message); }

    public LimitException(String message, Throwable cause) { super(message, cause); }

    public LimitException(Throwable cause) { super(cause); }
}