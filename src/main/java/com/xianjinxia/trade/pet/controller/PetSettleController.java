package com.xianjinxia.trade.pet.controller;

import com.xianjinxia.trade.pet.response.PetBaseResponse;
import com.xianjinxia.trade.pet.response.mqapp.ResultMsg;
import com.xianjinxia.trade.pet.service.IPetOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "trade_app pet job controller")
@RequestMapping("/service/pet")
@RestController
public class PetSettleController extends PetBaseController {

    private static final Logger logger = LoggerFactory.getLogger(PetSettleController.class);

    @Autowired
    private IPetOrderService petOrderService;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @ApiOperation(value = "job_settle_pet", httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE, notes = "宠物入链定时任务")
    @ApiResponse(response = PetBaseResponse.class, message = "baseResponse", code = 200)
    @PostMapping(value = "/settle/handle")
    public ResultMsg settleHandle() {

        try {
            threadPoolTaskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    logger.info("定时任务宠物入链开始");
                    petOrderService.handleSettle();
                    logger.info("定时任务宠物入链结束");
                }
            });
        }catch (Exception e){
            logger.error("定时任务宠物入链出现错误", e);
        }
        return successResp();
    }
}
