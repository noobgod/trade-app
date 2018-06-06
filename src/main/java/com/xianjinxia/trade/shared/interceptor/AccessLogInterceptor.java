package com.xianjinxia.trade.shared.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AccessLogInterceptor implements HandlerInterceptor {

    private static Logger logger = LoggerFactory.getLogger(AccessLogInterceptor.class);
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        return true;
    }

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
        int responseStatus = response.getStatus();
        String remoteAddr = request.getRemoteAddr();
        String requestMethod = request.getMethod();
        StringBuffer requestUrl = request.getRequestURL();
        String queryString = request.getQueryString();
        String userAgent = request.getHeader("User-Agent");
        if (!StringUtils.isEmpty(queryString)){
            requestUrl.append("?");
            requestUrl.append(queryString);
        }

        if (null != handler){
            HandlerMethod handlerMethod = (HandlerMethod)handler;
            String controllerClassName = handlerMethod.getBeanType().getName();
            String method = handlerMethod.getMethod().getName();
            logger.info("{}.{}", controllerClassName, method);
        }
        logger.info("{} {} {} {} {}", responseStatus, requestMethod, requestUrl.toString(), remoteAddr, userAgent);
    }

}
