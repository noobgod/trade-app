package com.xianjinxia.trade.shared.conf;

import com.xianjinxia.logcenter.sql.mybatis.CatInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
public class MybatisInterceptorConfig {

    private static final Logger logger = LoggerFactory.getLogger(MybatisInterceptorConfig.class);

    @Autowired
    private List<SqlSessionFactory> sqlSessionFactoryList;

    @PostConstruct
    public void addCatInterceptor() {
        CatInterceptor catInterceptor = new CatInterceptor();

        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
            org.apache.ibatis.session.Configuration configuration = sqlSessionFactory.getConfiguration();
            //page helper的interceptor要在cat的后面
            //List<Interceptor> interceptors = configuration.getInterceptors();//.addInterceptor(catInterceptor);
            //logger.info("interceptors:"+interceptors.size());//0
            configuration.addInterceptor(catInterceptor);
            logger.info("add mybatis interceptors:{}", catInterceptor.getClass().getSimpleName().toString());
        }
    }
}

