package com.xianjinxia.trade.platform.plugin;

import com.xianjinxia.trade.platform.plugin.annotation.PlatformApi;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class PlatformApiBeanAware implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(PlatformApi.class);

        for (Map.Entry<String, Object> entry: beansWithAnnotation.entrySet()){
            String beanName = entry.getKey();
            Object bean = entry.getValue();
            PlatformApiBeanMap.put(beanName, bean);
        }


    }
}
