package com.xianjinxia.trade.platform.controller;

import com.xianjinxia.trade.platform.plugin.DispatcherBeanInvoker;
import com.xianjinxia.trade.platform.response.OpenApiBaseResponse;
import com.xianjinxia.trade.shared.exception.IdempotentException;
import com.xianjinxia.trade.shared.exception.ServiceException;
import com.xianjinxia.trade.shared.exception.SqlUpdateException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@Api(tags = "trade platform dispatcher controller")
@RequestMapping("/service/platform")
@RestController
public class DispatcherController {

    private static final Logger logger = LoggerFactory.getLogger(DispatcherController.class);


    @ApiOperation(value = "dispatcher controller", notes = "请求分发器")
    @RequestMapping(value = "/{platformApiNo}")
    public OpenApiBaseResponse<Object> dispatch(@PathVariable("platformApiNo") String platformApiNo, @RequestBody Map<String, Object> paramMap) {
        OpenApiBaseResponse<Object> response = new OpenApiBaseResponse<>();
        try {
            logger.info("收到客户端请求:{}", platformApiNo);
            Object obj = new DispatcherBeanInvoker(platformApiNo, paramMap).invoke();
            response.setData(obj);
            return response;
        } catch (InvocationTargetException ite) {
            // 由于是反射调用，所以proxy抛出的exception都是InvocationTargetException类型的异常
            // getTargetException可获取具体的业务层抛出的异常，再进行类型的判断
            Throwable targetException = ite.getTargetException();
            if (targetException instanceof IdempotentException) {
                logger.info("该请求已处理过 , 请求参数为{}", paramMap);
                return response;
            } else if (targetException instanceof ServiceException) {
                logger.error("请求发生业务异常,PlatformApiNo:{}", platformApiNo, targetException);
                response.setCode(OpenApiBaseResponse.ResponseCode.SYS_ERROR.getValue());
                response.setMsg(((ServiceException)targetException).getMsg());
                return response;
            } else if (targetException instanceof SqlUpdateException) {
                logger.error("请求发生Mapper层操作异常,PlatformApiNo:{}", platformApiNo, targetException);
                response.setCode(OpenApiBaseResponse.ResponseCode.SYS_ERROR.getValue());
                response.setMsg(targetException.getMessage());
                return response;
            } else {
                logger.error("请求发生异常,PlatformApiNo:{}", platformApiNo, ite);
                response.systemError();
                return response;
            }
        } catch (Exception e) {
            logger.error("请求发生异常,PlatformApiNo:{}", platformApiNo, e);
            response.systemError();
            return response;
        }

    }
}

