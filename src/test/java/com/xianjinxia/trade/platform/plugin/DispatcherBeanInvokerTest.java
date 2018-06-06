package com.xianjinxia.trade.platform.plugin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.xianjinxia.trade.shared.domain.LoanOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class DispatcherBeanInvokerTest {

    @Test
    public void testToJSONString() {
        SerializeConfig config = new SerializeConfig();
        config.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;

        LoanOrder loanOrder = new LoanOrder();
        loanOrder.setUserId(1231l);
        String text = JSON.toJSONString(loanOrder, config);
        System.out.println(text);
    }

    @Test
    public void testParseObject() {

        ParserConfig parserConfig = new ParserConfig(); // 生产环境中，parserConfig要做singleton处理，要不然会存在性能问题
        parserConfig.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
        LoanOrder loanOrder = JSON.parseObject("{\"user_id\":1231}", LoanOrder.class, parserConfig);
        System.out.println(loanOrder.getUserId());
    }
}