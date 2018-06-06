package com.xianjinxia.trade.shared.exception;

import com.xianjinxia.trade.shared.domain.EventIdempotent;

/**
 * 幂等异常，当 请求之前已经被处理过了，抛出此异常
 */
public class IdempotentException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public IdempotentException(EventIdempotent idempotentEvent) {
        super("has an idempotent exception for [idempotentKey:" + idempotentEvent.getSourceId()
                + ",idempotentType," + idempotentEvent.getSourceType() + "]");
    }

    public IdempotentException(String message) {
        super(message);
    }

    public IdempotentException(String message, Throwable cause) {
        super(message, cause);
    }

    public IdempotentException(Throwable cause) {
        super(cause);
    }

}
