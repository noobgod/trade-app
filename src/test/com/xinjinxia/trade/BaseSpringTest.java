package com.xinjinxia.trade;

import com.xianjinxia.trade.TradeApplication;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zhangjiayuan
 * @create 2017-11-01 13:59
 **/
@Rollback
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TradeApplication.class)
public class BaseSpringTest {
    protected Logger log = LoggerFactory.getLogger(getClass());

}
