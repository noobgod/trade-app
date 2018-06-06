package com.xianjinxia.trade.platform.plugin;

import java.lang.reflect.Method;

public class DispatcherBeanDefinition {

    private String beanName;
    private String platformApiNo;
    private Object bean;
    private Method method;

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getPlatformApiNo() {
        return platformApiNo;
    }

    public void setPlatformApiNo(String platformApiNo) {
        this.platformApiNo = platformApiNo;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
