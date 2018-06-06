package com.xianjinxia.trade.app.controller;



import com.dianping.cat.common.EventInfo;
import com.dianping.cat.utils.CatUtils;
import com.xianjinxia.trade.platform.request.SyncLoanOrderReq;
import com.xianjinxia.trade.shared.enums.ProductCategoryEnum;
import com.xianjinxia.trade.shared.exception.IdempotentException;
import com.xianjinxia.trade.app.dto.TradeDto;
import com.xianjinxia.trade.app.request.RepaymentSuccessReq;
import com.xianjinxia.trade.app.request.PaymentResult;
import com.xianjinxia.trade.app.response.mqapp.ResultMsg;
import com.xianjinxia.trade.app.service.ILoanOrderService;
import com.xianjinxia.trade.app.service.ILoanService;
import com.xianjinxia.trade.app.service.IPaymentService;
import com.xianjinxia.trade.shopping.service.IShoppingLoanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = "trade_app event controller")
@RequestMapping("/service/event")
@RestController
public class EventController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(EventController.class);

    @Autowired
    private IPaymentService paymentService;

    @Autowired
    private ILoanOrderService loanOrderService;

    @Autowired
    private ILoanService loanService;

    @Autowired
    private IShoppingLoanService shoppingLoanService;

    /**
     * 处理放款后的结果
     * 
     * @description 此方法由支付中心放款后，推送消息到MQAPP，MQAPP调用此url
     * @param result
     */
    @PostMapping(value = "/pay-callback")
    @ApiOperation(value = "loan payment callback", notes = "支付中心放款后的结果回调处理函数")
    public ResultMsg processLoanPayResult(@RequestBody PaymentResult result) {
        try {
            logger.info("pay-callback请求参数为{}",result);
            EventInfo eventInfo = new EventInfo();
            eventInfo.setEventType("payment-callback");
            eventInfo.setMessage("receive paymentcenter payment message");
            eventInfo.put("paymentOrderSeqNo", result.getPaymentOrderSeqNo());
            CatUtils.info(eventInfo);

//            if (!result.paramCheck()) {
//                return paramErrorResp();
//            }

            // 如果状态码为成功，支付时间或者支付中心支付号为空，返回参数错误信息
//            if (PaymentResult.CodeEnum.SUCCESS.getCode().equals(result.getCode())
//                    && (result.getBankPayTime() == null || StringUtils.isBlank(result
//                            .getPaymentOrderNo()) || result.getLoanCaptionInfo()==null)) {
//                return paramErrorResp();
//            }

            paymentService.processPayResult(result);
            return successResp();
        } catch (IdempotentException e) {
            logger.error(e.getMessage(), e);
            return successResp();
        } catch (Exception e) {
            logger.error("processLoanPayResult error,url:{}", "/service/event/pay-callback", e);
            return innerErrorResp();
        }

    }



    /**
     * 接收风控结果，处理
     * 
     * @param tradeDto
     * @return
     */
    @ApiOperation(value = "recive loan data", notes = "接收风控结果，处理")
    @PostMapping(value = "/recive-loan-data")
    public ResultMsg reciveRiskData(@RequestBody TradeDto tradeDto) {
        try {
            // cat 日志收集
            EventInfo eventInfo = new EventInfo();
            eventInfo.put("assetOrderId", tradeDto.getAssetOrderId());
            eventInfo.setEventType("loan-data-callback");
            CatUtils.info(eventInfo);
            // check 参数
            if (!tradeDto.paramCheck()) {
                return paramErrorResp();
            }
            logger.info("request trade params :{}", tradeDto);
            loanOrderService.loanOrderRecord(tradeDto);
            return successResp();
        } catch (DuplicateKeyException e) {
            return successResp();
        } catch (Exception e) {
            logger.error("system error {}", e);
            return innerErrorResp();
        }
    }


    @PostMapping(value = "/loanRepaymentSuccess")
    @ApiOperation(value = "user repay success", notes = "还款已还清,改订单状态为已还清")
    public ResultMsg updateLoanStatusByRepay(@RequestBody RepaymentSuccessReq result){
        try {
            EventInfo eventInfo = new EventInfo();
            eventInfo.setEventType("loanRepayStatus-callback");
            CatUtils.info(eventInfo);
            if(!result.paramCheck()){
                return paramErrorResp();
            }
            loanService.updateLoanStatusByID(result.getLoanOrderId());
            return successResp();
        } catch (IdempotentException e) {
            logger.warn(e.getMessage(),e);
            return successResp();
        }catch(Exception e){
            logger.error("updateLoanStatusByRepay error,url:{}","/service/loan/loanRepayStatus-callback",e);
            return innerErrorResp();
        }
    }


    @PostMapping(value = "/sync-order-status")
    @ApiOperation(value = "sync order status", notes = "同步订单状态")
    public ResultMsg syncOrderStatus(@RequestBody SyncLoanOrderReq syncLoanOrderReq){
        logger.info("sync-order-status入参为:{}",syncLoanOrderReq);
        try {
            EventInfo eventInfo = new EventInfo();
            eventInfo.put("trdLoanOrderId", syncLoanOrderReq.getLoanOrderId());
            eventInfo.put("status", syncLoanOrderReq.getStatus());
            eventInfo.put("productCategory", syncLoanOrderReq.getProductCategory());
            eventInfo.setEventType("sync-order-status");
            CatUtils.info(eventInfo);
            if(!syncLoanOrderReq.paramCheck()){
                return paramErrorResp();
            }

            if (syncLoanOrderReq.getProductCategory().intValue() == ProductCategoryEnum.BIGAMOUNT.getCode()){
                loanService.syncOrderStatus(syncLoanOrderReq);
            }
            if (syncLoanOrderReq.getProductCategory().intValue() == ProductCategoryEnum.PRODUCT_CATEGORY_SHOPPING.getCode()){
                shoppingLoanService.syncLoanOrderStatus(syncLoanOrderReq);
            }


            return successResp();
        } catch (IdempotentException e) {
            logger.error(e.getMessage(),e);
            return successResp();
        }catch(Exception e){
            logger.error("syncOrderStatus error",e);
            return innerErrorResp();
        }
    }

}
