package com.xianjinxia.trade.shared.utils;

import org.springframework.context.ApplicationContext;

/**
 * SpringUtil帮助类，持有ApplicationContext
 * Created by MJH on 2017/6/2.
 */
public class SpringUtil {

    private static ApplicationContext context = null;

    public static ApplicationContext getContext() {
        return context;
    }

    public static void setContext(ApplicationContext ctx){
        context = ctx;
    }
}
