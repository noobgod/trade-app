package com.xianjinxia.trade.shared.listener;


import com.xianjinxia.trade.shared.utils.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * spring监听器 当spring初始化装载完bean后会调用此监听器方法
 * 监听器在项目初始化时，自动动态添加所有需要的队列监控
 *
 * @author mjh
 */
@Component
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();
        //设置context
        SpringUtil.setContext(context);
    }
}