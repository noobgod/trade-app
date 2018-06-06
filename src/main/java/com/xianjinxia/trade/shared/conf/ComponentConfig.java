package com.xianjinxia.trade.shared.conf;

import com.xjx.mqclient.service.MqClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.sql.DataSource;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 自定义bean注入到springboot
 */
@Configuration
public class ComponentConfig {
    private static final Logger logger = LoggerFactory.getLogger(ComponentConfig.class);

    @Autowired
    private DataSource dataSource;
    @Autowired
    private ExtProperties extProperties;
    @Bean
    public MqClient mqClient() {

        ExtProperties.ActiveMqConfiguration activeMqConfiguration = extProperties.getActiveMqConfiguration();
        MqClient mq = null;
        try {
            mq = new MqClient(activeMqConfiguration.getBrokerUrl(), dataSource, activeMqConfiguration.getQueueTableName(), activeMqConfiguration.getQueueName(), activeMqConfiguration.getQueueMaxCount(), activeMqConfiguration.getIsCreateTable());
            mq.start();
        } catch (Exception e) {
            logger.error("mq启动初始化异常",e);
        }
        return mq;

    }

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor(){
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(5);
        threadPoolTaskExecutor.setMaxPoolSize(20);
        threadPoolTaskExecutor.setQueueCapacity(2000);
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        return threadPoolTaskExecutor;
    }
}
