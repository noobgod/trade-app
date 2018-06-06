package com.xianjinxia.trade.platform.plugin;

import com.xianjinxia.trade.platform.plugin.annotation.PlatformApiNo;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class PlatformApiBeanMap {

    private static final Logger logger = LoggerFactory.getLogger(PlatformApiBeanMap.class);

    private static final Map<String, DispatcherBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    public static void put(String beanName, Object bean) {
        // 原生service impl是由cglib生成, 所以要先获取Superclass再获取原生的接口
        // 业务实现类只支持1个接口，多个不支持
        Class<?> nativeInterfaceType = bean.getClass().getSuperclass().getInterfaces()[0];
        Method[] methods = nativeInterfaceType.getDeclaredMethods();

        if (ArrayUtils.isEmpty(methods)) {
            return;
        }

        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            PlatformApiNo[] annotationsByType = method.getAnnotationsByType(PlatformApiNo.class);
            if (ArrayUtils.isEmpty(annotationsByType)) {
                continue;
            }

            if (annotationsByType.length > 1) {
                continue;
            }
            String platformApiNo = annotationsByType[0].value();
            DispatcherBeanDefinition dispatcherBeanDefinition = new DispatcherBeanDefinition();
            dispatcherBeanDefinition.setBeanName(beanName);
            dispatcherBeanDefinition.setPlatformApiNo(platformApiNo);
            dispatcherBeanDefinition.setBean(bean);
            dispatcherBeanDefinition.setMethod(method);


            logger.info("请求代由DispatcherController分发:{}", nativeInterfaceType.getSimpleName() + "." + method.getName());
            beanDefinitionMap.put(platformApiNo, dispatcherBeanDefinition);
        }
    }

    public static DispatcherBeanDefinition getDispatcherBeanDefinition(String apiRequestNo) {
        return beanDefinitionMap.get(apiRequestNo);
    }

}
