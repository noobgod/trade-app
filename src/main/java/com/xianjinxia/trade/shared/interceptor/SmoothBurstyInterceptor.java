package com.xianjinxia.trade.shared.interceptor;

import com.google.common.util.concurrent.RateLimiter;
import com.xianjinxia.trade.shared.exception.LimitException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * 限流器
 * @author ganminghui
 */
@Component
public class SmoothBurstyInterceptor extends HandlerInterceptorAdapter {

    public enum LimitType {
        /* 丢弃 */ DROP,
        /* 阻塞 */ WAIT
    }

    /* 默认tps(10个并发/s) */ private static final Integer DEFAULT_LIMITER_TPS = 10;

    /* 限流器 */private RateLimiter limiter;

    /* 限流方式 */private LimitType limitType = LimitType.DROP;

    public SmoothBurstyInterceptor() { this(DEFAULT_LIMITER_TPS,LimitType.DROP); }

    /**
     * @param tps       限流量 (每秒处理量)
     * @param limitType 限流类型:等待/丢弃(达到限流量)
     */
    public SmoothBurstyInterceptor(int tps, SmoothBurstyInterceptor.LimitType limitType) {
        this.limiter = RateLimiter.create(tps);
        this.limitType = limitType;
    }

    /**
     * @param permitsPerSecond  每秒新增的令牌数
     * @param limitType 限流类型:等待/丢弃(达到限流量)
     */
    public SmoothBurstyInterceptor(double permitsPerSecond, SmoothBurstyInterceptor.LimitType limitType) {
        this.limiter = RateLimiter.create(permitsPerSecond, 1000, TimeUnit.MILLISECONDS);
        this.limitType = limitType;
    }

    @Override public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (limitType.equals(LimitType.DROP)) {
            if (limiter.tryAcquire()) {
                return super.preHandle(request, response, handler);
            }
        } else {
            limiter.acquire();
            return super.preHandle(request, response, handler);
        }
        /* 达到限流后，往页面提示的错误信息 */
        throw new LimitException(LimitException.DEFAULT_LIMITER_MSG);
    }
}