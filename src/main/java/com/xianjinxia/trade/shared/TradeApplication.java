package com.xianjinxia.trade.shared;

import com.github.pagehelper.autoconfigure.PageHelperAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

//@SpringBootApplication

@Configuration
@ComponentScan("com.xianjinxia.trade")
@EnableAutoConfiguration//(exclude = {PageHelperAutoConfiguration.class})
@ServletComponentScan
@EnableTransactionManagement
@EnableDiscoveryClient
@EnableWebMvc
@MapperScan("com.xianjinxia.trade.shared.mapper")
public class TradeApplication extends SpringBootServletInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(TradeApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(TradeApplication.class, args);
	}
}