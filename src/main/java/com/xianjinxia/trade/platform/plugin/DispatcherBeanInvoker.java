package com.xianjinxia.trade.platform.plugin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.xianjinxia.trade.shared.exception.ServiceException;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class DispatcherBeanInvoker {

    private static final Logger logger = LoggerFactory.getLogger(DispatcherBeanInvoker.class);

    private Map<String,Object> paramMap;
    private String platformApiNo;
    private SnakeCaseConverter snakeCaseConverter = new SnakeCaseConverter();


    public DispatcherBeanInvoker(String platformApiNo,  Map<String,Object> paramMap) {
        this.platformApiNo = platformApiNo;
        this.paramMap = paramMap;
    }


    public Object invoke() throws InvocationTargetException, IllegalAccessException {
        DispatcherBeanDefinition dispatcherBeanDefinition = PlatformApiBeanMap.getDispatcherBeanDefinition(this.platformApiNo);
        if (ObjectUtils.isEmpty(dispatcherBeanDefinition)) {
            logger.warn("没找到匹配的处理类, PlatformApiNo :{}", this.platformApiNo);
            throw new ServiceException("服务器端未找到匹配的请求编号:" + this.platformApiNo);
        }

        Object bean = dispatcherBeanDefinition.getBean();
        Method method = dispatcherBeanDefinition.getMethod();
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (ArrayUtils.isEmpty(parameterTypes)) {
            Object methodReturnObj = method.invoke(bean);
            return snakeCaseConverter.convertResponse(methodReturnObj);
        }

        //只允许一个参数接收
        Class<?> parameterClass = parameterTypes[0];
        Object methodParameter = snakeCaseConverter.covertRequest(paramMap, parameterClass);
        Object methodReturnObj = method.invoke(bean, methodParameter);
        return snakeCaseConverter.convertResponse(methodReturnObj);
    }
}
