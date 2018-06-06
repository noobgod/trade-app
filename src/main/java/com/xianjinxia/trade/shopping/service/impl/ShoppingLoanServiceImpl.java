package com.xianjinxia.trade.shopping.service.impl;

import com.alibaba.fastjson.JSON;
import com.xianjinxia.trade.app.dto.*;
import com.xianjinxia.trade.app.dto.UserReceiveAddressDto;
import com.xianjinxia.trade.app.service.IMqMessageService;
import com.xianjinxia.trade.platform.request.SyncLoanOrderReq;
import com.xianjinxia.trade.shared.constant.QueueConst;
import com.xianjinxia.trade.shared.constant.SmsConstant;
import com.xianjinxia.trade.shared.domain.*;
import com.xianjinxia.trade.shared.enums.*;
import com.xianjinxia.trade.shared.exception.ServiceException;
import com.xianjinxia.trade.shared.exception.SqlUpdateException;
import com.xianjinxia.trade.shared.idempotent.IdempotentService;
import com.xianjinxia.trade.shared.mapper.TraceMapper;
import com.xianjinxia.trade.shared.mapper.TrdShoppingLoanOrderMapper;
import com.xianjinxia.trade.shared.remote.CashmanAppRemoteService;
import com.xianjinxia.trade.shared.service.ISmsService;
import com.xianjinxia.trade.shared.service.LoanApplyChecker;
import com.xianjinxia.trade.shared.utils.*;
import com.xianjinxia.trade.shopping.dto.*;
import com.xianjinxia.trade.shopping.remote.OpenApiRemoteSoouuService;
import com.xianjinxia.trade.shopping.request.app.ShoppingConfirmLoanReq;
import com.xianjinxia.trade.shopping.request.app.ShoppingConfirmReceiptLoanReq;
import com.xianjinxia.trade.shopping.request.app.ShoppingLoanOrderReq;
import com.xianjinxia.trade.shopping.request.app.ShoppingLogisticsOrderReq;
import com.xianjinxia.trade.shopping.response.bgd.ConfirmLoanOrderDetailResponse;
import com.xianjinxia.trade.shopping.response.bgd.ReceiveLoanOrderDetailResponse;
import com.xianjinxia.trade.shopping.response.soouu.SoouuCard;
import com.xianjinxia.trade.shopping.response.soouu.SoouuSuccessResponse;
import com.xianjinxia.trade.shopping.service.*;
import com.xjx.mqclient.pojo.MqMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 分期商城订单立即申请、确认申请的接口
 * <pre>
 *     1. 订单立即申请
 *     2. 确认申请时，更新订单状态、冻结编号
 *     3. 异步更新订单状态
 * </pre>
 *
 * @author chunliny ycl@xianjinxia.com
 */
@Service
public class ShoppingLoanServiceImpl implements IShoppingLoanService {

    private static final Logger logger = LoggerFactory.getLogger(ShoppingLoanServiceImpl.class);

    @Autowired
    private ITrdShoppingLoanOrderService trdShoppingLoanOrderService;

    @Autowired
    private ITrdShoppingLogisticsOrderService trdShoppingLogisticsOrderService;

    @Autowired
    private ITrdShoppingProductOrderService trdShoppingProductOrderService;

    @Autowired
    private TrdShoppingLoanOrderMapper shoppingLoanOrderMapper;

    @Autowired
    private IMqMessageService mqMessageService;

    @Autowired
    private IdempotentService idempotentService;

    @Autowired
    private ISmsService smsService;
    

    @Autowired
    private TraceMapper traceMapper;

    @Autowired
    private LoanApplyChecker loanApplyChecker;
    @Autowired
    private ITrdShoppingLoanCardInfoService trdShoppingLoanCardInfoService;
    @Autowired
    private OpenApiRemoteSoouuService openApiRemoteSoouuService;

    @Autowired
    private CashmanAppRemoteService cashmanAppRemoteService;


    /**
     * 立即申请,把申请的订单落表，初始化申请
     * 1、检查当前用户是否有未结清的订单
     * 2、检查traceNo是否已使用
     * 3、插入金融商品订单表、物流订单表、商品订单表
     */
    @Transactional
    public ShoppingLoanOrderDto applyLoan(ShoppingLoanOrderReq shoppingLoanOrderReq) {
        logger.info("shopping trade apply loan params:{}", shoppingLoanOrderReq);

        if (ProductCategoryEnum.PRODUCT_CATEGORY_SHOPPING.getCode().intValue() != shoppingLoanOrderReq.getProductCategory()) {
            throw new ServiceException("产品类型错误,不能操作");
        }

        loanApplyChecker.check(shoppingLoanOrderReq.getUserId(), shoppingLoanOrderReq.getQuietPeriod());

        //        Long userId = shoppingLoanOrderReq.getUserId();
//        int count = trdShoppingLoanOrderService.updateByUserIdAndStatus(userId, ShoppingLoanOrderStatusEnum.CANCEL.getCode(), ShoppingLoanOrderStatusEnum.APPLY.getCode());
//        logger.info("更新用户[{}]之前的立即申请订单未取消状态", userId);


        // 创建trace记录
        Trace trace = new Trace();
        String traceNo = UniqueIdUtil.getTraceNoUniqueId();
        trace.setEventTime(new Date());
        trace.setOrderEvent(TraceOrderEventEnum.CREATE_TRACE.getCode());
        trace.setEventText(TraceOrderEventEnum.CREATE_TRACE.getText());
        trace.setTraceData(GsonUtil.toGson(shoppingLoanOrderReq));
        trace.setTraceNo(traceNo);
        traceMapper.insert(trace);
        logger.info("生成trace表:{}", trace);

        Integer countByTraceNo = trdShoppingLoanOrderService.countByTraceNo(traceNo);
        if (countByTraceNo > 0) {
            throw new ServiceException("订单系统追踪号已使用,不能重复提交");
        }

        ShoppingProductDto shoppingProductDto = shoppingLoanOrderReq.getShoppingProductDto();
        UserBankCardDto userBankCardDto = shoppingLoanOrderReq.getUserBankCardDto();
        UserReceiveAddressDto userReceivwAddressDto = shoppingLoanOrderReq.getUserReceivwAddressDto();

        String unToken = UniqueIdUtil.getLoanOrderUniqueId();//创建unique unKoken
        String status = ShoppingLoanOrderStatusEnum.APPLY.getCode();

        TrdShoppingLoanOrder trdShoppingLoanOrder = new TrdShoppingLoanOrder(null, "", shoppingLoanOrderReq.getOrderAmount(),
                shoppingLoanOrderReq.getFeeAmount(), shoppingLoanOrderReq.getInterestAmount(),
                shoppingLoanOrderReq.getPaymentAmount(), shoppingLoanOrderReq.getRepaymentAmount(),
                shoppingLoanOrderReq.getTerm(), shoppingLoanOrderReq.getTermUnit(), shoppingLoanOrderReq.getTermRate(),
                shoppingLoanOrderReq.getProductId(), shoppingLoanOrderReq.getProductCategory(), shoppingLoanOrderReq.getIsDepository(),
                (shoppingLoanOrderReq.getUserType() == 1 ? true : false), status, traceNo, shoppingLoanOrderReq.getUserId(),
                shoppingLoanOrderReq.getUserIdcardType(), shoppingLoanOrderReq.getUserIdcardNo(), userBankCardDto.getUserBankCardId(),
                userBankCardDto.getBankName(), userBankCardDto.getBankCardNoLastFour(), shoppingLoanOrderReq.getUserPhone(),
                shoppingLoanOrderReq.getUserName(), shoppingLoanOrderReq.getSource(), shoppingLoanOrderReq.getTerminal(),
                unToken, null, "", null, "", shoppingLoanOrderReq.getSpecificationJson(), shoppingLoanOrderReq.getSpecificationDesc(),
                "", null, shoppingProductDto.getKindId() == null ? 1:shoppingProductDto.getKindId());
        trdShoppingLoanOrderService.insert(trdShoppingLoanOrder);

        Long shoppingLoanOrderId = trdShoppingLoanOrder.getId();

        TrdShoppingLogisticsOrder trdShoppingLogisticsOrder = new TrdShoppingLogisticsOrder(null, shoppingLoanOrderId, userReceivwAddressDto.getReceiveProvince(), userReceivwAddressDto.getReceiveCity(), userReceivwAddressDto.getReceiveArea(), userReceivwAddressDto.getReceiveAddr(), userReceivwAddressDto.getReceivePostNo(), userReceivwAddressDto.getReceiveUsername(), userReceivwAddressDto.getReceiveMobile(), userReceivwAddressDto.getLogisticsCompany(), userReceivwAddressDto.getLogisticsNo(), userReceivwAddressDto.getLogisticsPhone(), status, new Date(), "", null, "", null, null, null, null, null, null, null, null, "");
        trdShoppingLogisticsOrderService.insert(trdShoppingLogisticsOrder);

        TrdShoppingProductOrder trdShoppingProductOrder = new TrdShoppingProductOrder(null, shoppingLoanOrderId, shoppingProductDto.getProductId(),
                shoppingProductDto.getProductPrice(), shoppingProductDto.getProductName(), shoppingProductDto.getProductCategory(), status,
                null, "", null, "", null, shoppingProductDto.getBuyNumber(), shoppingProductDto.getThirdProductId(), shoppingProductDto.getProductUnitPrice());
        trdShoppingProductOrderService.insert(trdShoppingProductOrder);

        ShoppingLoanOrderDto shoppingLoanOrderDto = new ShoppingLoanOrderDto(LoanCodeMsgEnum.SUCCESS);
        shoppingLoanOrderDto.setUnToken(unToken);
        shoppingLoanOrderDto.setShoppingLoanOrderId(shoppingLoanOrderId);
        shoppingLoanOrderDto.setTraceNo(shoppingLoanOrderReq.getTraceNo());
        logger.info("立即购买申请订单成功，订单ID:{}", shoppingLoanOrderId);
        return shoppingLoanOrderDto;
    }

    /**
     * 确认申请时,更新三张表的订单状态,商品系统冻结编号、发送MQ到cashman-app创建借款订单，还款计划
     * 1、检查该订单是否存在
     * 2、检查状态是否是待确认申请(未提交)状态
     * 3、更新三张表的订单状态、商品系统冻结编号
     * 4、发送MQ到cashman-app创建借款订单，还款计划
     */
    @Transactional
    public void confirmLoan(ShoppingConfirmLoanReq shoppingConfirmLoanReq) {
        Long shoppingLoanOrderId = shoppingConfirmLoanReq.getShoppingLoanOrderId();
        TrdShoppingLoanOrder trdShoppingLoanOrder = trdShoppingLoanOrderService.getById(shoppingLoanOrderId);
        if (null == trdShoppingLoanOrder) {
            throw new ServiceException("数据不存在,不能操作");
        }

        if (!ShoppingLoanOrderStatusEnum.APPLY.getCode().equals(trdShoppingLoanOrder.getStatus())) {
            throw new ServiceException("状态无效,不能操作");
        }


        // 创建trace记录
        Trace trace = new Trace();
        trace.setEventTime(new Date());
        trace.setOrderEvent(TraceOrderEventEnum.LOAN_APPLY.getCode());
        trace.setEventText(TraceOrderEventEnum.LOAN_APPLY.getText());
        trace.setTraceData(GsonUtil.toGson(shoppingConfirmLoanReq));
        trace.setTraceNo(trdShoppingLoanOrder.getTraceNo());
        traceMapper.insert(trace);
        logger.info("生成trace表:{}", trace);


        String newStatus = ShoppingLoanOrderStatusEnum.NEW.getCode();
        String preStatus = ShoppingLoanOrderStatusEnum.APPLY.getCode();

        int updateLoanOrderNum = trdShoppingLoanOrderService.updateLoanOrderStatusToPending(shoppingLoanOrderId, newStatus, preStatus);

        if (updateLoanOrderNum <= 0) {
            logger.error("更新金融商品订单表状态失败,订单order_no:{}", shoppingLoanOrderId);
            throw new SqlUpdateException("更新金融商品订单表状态失败");
        }

        int updateProductOrderNum = trdShoppingProductOrderService.updateProductOrderStatus(shoppingLoanOrderId, newStatus, preStatus);

        if (updateProductOrderNum <= 0) {
            logger.error("更新商品订单表状态失败,订单order_no:{}", shoppingLoanOrderId);
            throw new SqlUpdateException("更新商品订单表状态失败");
        }

        int updateLogisticsOrderNum = trdShoppingLogisticsOrderService.updateLogisticsOrderStatus(shoppingLoanOrderId, newStatus, preStatus);

        if (updateLogisticsOrderNum <= 0) {
            logger.error("更新物流订单表状态失败,订单order_no:{}", shoppingLoanOrderId);
            throw new SqlUpdateException("更新物流订单表状态失败");
        }

        LoanOrder loanOrder = new LoanOrder();
        BeanUtils.copyProperties(trdShoppingLoanOrder, loanOrder);
        loanOrder.setBizSeqNo(UniqueIdUtil.getLoanOrderUniqueId());
        loanOrder.setCreatedTime(trdShoppingLoanOrder.getCreatedAt());
        loanOrder.setLastFourBankCardNo(trdShoppingLoanOrder.getBankCardNoLastFour());
        loanOrder.setOrderType(LoanOrderTypeEnum.LOAN.getCode());
        loanOrder.setPeriods(trdShoppingLoanOrder.getTerm());
        loanOrder.setStatus(ShoppingLoanOrderStatusEnum.NEW.getCode());
        loanOrder.setProductCategory(ProductCategoryEnum.PRODUCT_CATEGORY_SHOPPING.getCode());//商城订单
        loanOrder.setId(trdShoppingLoanOrder.getId());
        loanOrder.setMerchantNo("cjxjx");

        logger.info("start send mq message, queue:{}, request content:{}", QueueConst.ORDER_CONFIRM_SUCCESS_QUEUE, JsonUtils.toJSONString(loanOrder));

        //发送MQ到cashman-app创建借款订单，还款计划
        mqMessageService.sendMessage(new MqMessage(JsonUtils.toJSONString(loanOrder), QueueConst.ORDER_CONFIRM_SUCCESS_QUEUE));
    }

    /**
     * 异步更新订单状态,如果分类不是商城订单类型,忽略此请求
     */
    @Transactional
    public void syncLoanOrderStatus(SyncLoanOrderReq syncLoanOrderReq) {
        // 1、接口幂等验证
        idempotentService.idempotentCheck(IdempotentTypeEnum.SYNC_SHOPPING_ORDER_STATUS_TO_TRADE, syncLoanOrderReq);

        int shoppingProductEnumVal = ProductCategoryEnum.PRODUCT_CATEGORY_SHOPPING.getCode().intValue();
        if (syncLoanOrderReq.getProductCategory().intValue() != shoppingProductEnumVal) {
            logger.info("非金融商品的订单状态同步请求，忽略此请求，请求参数：{}", syncLoanOrderReq.toString());
            return;
        }

        // 2、订单信息验证
        Long shoppingLoanOrderId = syncLoanOrderReq.getLoanOrderId();
        TrdShoppingLoanOrder trdShoppingLoanOrder = trdShoppingLoanOrderService.getById(shoppingLoanOrderId);
        if (null == trdShoppingLoanOrder) {
            throw new ServiceException("数据不存在,不能操作");
        }
        String newStatus = syncLoanOrderReq.getStatus();
        String preStatus = trdShoppingLoanOrder.getStatus();
        if (!ShoppingLoanOrderStatusEnum.canUpdate(newStatus, preStatus)) {
            logger.warn("订单状态不允许更新: new status {}, current status{}", newStatus, preStatus);
            throw new ServiceException("订单状态不允许更新");
        }

        // 3、修改物流订单、商品订单、金融订单的状态
        this.updateShoppingLoanOrderStatus(shoppingLoanOrderId, newStatus, preStatus);



        // 4、判断状态：如果是风控审核的结果
        // 审核通过：发送MQ给Product-App真实扣减之前冻结的库存
        // 审核拒绝：发送MQ给User-App归还用户额度，发送MQ给Product-App归还之前扣减的库存，并发送SMS给用户
        if (StringUtils.equals(newStatus, ShoppingLoanOrderStatusEnum.REFUSED.getCode())){
            this.onRiskRefused(trdShoppingLoanOrder);
        }else if(StringUtils.equals(newStatus, ShoppingLoanOrderStatusEnum.MANUAL_REFUSED.getCode())) {
            this.onRiskRefused(trdShoppingLoanOrder);
        }else if (StringUtils.equals(newStatus, ShoppingLoanOrderStatusEnum.APPROVED.getCode())){
            this.onRiskApproved(trdShoppingLoanOrder);
        }else if(StringUtils.equals(newStatus, ShoppingLoanOrderStatusEnum.MANUAL_APPROVED.getCode())){
            this.onRiskApproved(trdShoppingLoanOrder);
        }

    }

    /**
     * 获取订单的详情
     */
    @Override
    public ReceiveLoanOrderDetailResponse getOrderDetailForReceive(Long shoppingLoanOrderId) {
        ReceiveLoanOrderDetailResponse resp = new ReceiveLoanOrderDetailResponse();
        TrdShoppingLoanOrder trdShoppingLoanOrder = trdShoppingLoanOrderService.getById(shoppingLoanOrderId);
        if (null == trdShoppingLoanOrder) {
            throw new ServiceException("订单不存在");
        }

        TrdShoppingLogisticsOrder trdShoppingLogisticsOrder = trdShoppingLogisticsOrderService.getByShoppingLoanOrderId(shoppingLoanOrderId);
        trdShoppingLogisticsOrder.setReceiveAddr(trdShoppingLogisticsOrder.getReceiveAddr());
        BeanUtils.copyProperties(trdShoppingLogisticsOrder, resp);

        TrdShoppingProductOrder trdShoppingProductOrder = trdShoppingProductOrderService.getByShoppingLoanOrderId(shoppingLoanOrderId);
        BeanUtils.copyProperties(trdShoppingLoanOrder, resp);

        resp.setProductCount(trdShoppingProductOrder.getProductNumber());
        resp.setProductPrice(trdShoppingProductOrder.getProductPrice());
        resp.setProductName(trdShoppingProductOrder.getProductName());
        resp.setStatusText(ShoppingLoanOrderStatusEnum.getByCode(trdShoppingLoanOrder.getStatus()).getValue());
        resp.setDefaultReceivedMaxDayCount(3);
        resp.setKindId(trdShoppingLoanOrder.getProductKind());

        Date sendTime = trdShoppingLogisticsOrder.getSendTime();
        if (null != sendTime){
            resp.setAutoReceiveTime(DateUtil.addDay(sendTime, 10).getTime());
        }else{
            resp.setAutoReceiveTime(0L);
        }
        //返回虚拟卡卡密列表
        resp.setCards(trdShoppingLoanCardInfoService.getByOrderId(trdShoppingProductOrder.getId()));
        return resp;
    }

    @Override
    public ConfirmLoanOrderDetailResponse getOrderDetailForConfirm(Long shoppingLoanOrderId) {
        TrdShoppingLoanOrder trdShoppingLoanOrder = trdShoppingLoanOrderService.getById(shoppingLoanOrderId);
        if (null == trdShoppingLoanOrder) {
            throw new ServiceException("订单不存在");
        }
        TrdShoppingLogisticsOrder trdShoppingLogisticsOrder = trdShoppingLogisticsOrderService.getByShoppingLoanOrderId(shoppingLoanOrderId);

        TrdShoppingProductOrder trdShoppingProductOrder = trdShoppingProductOrderService.getByShoppingLoanOrderId(shoppingLoanOrderId);

        ConfirmLoanOrderDetailResponse confirmLoanOrderDetailResponse = new ConfirmLoanOrderDetailResponse();
        BeanUtils.copyProperties(trdShoppingLoanOrder, confirmLoanOrderDetailResponse);

        ShoppingLogisticsOrderDto shoppingLogisticsOrderDto = new ShoppingLogisticsOrderDto();

        if(null != trdShoppingLogisticsOrder){
            BeanUtils.copyProperties(trdShoppingLogisticsOrder, shoppingLogisticsOrderDto);
        }

        ShoppingProductOrderDto shoppingProductOrderDto = new ShoppingProductOrderDto();

        if (null != trdShoppingProductOrder) {
            BeanUtils.copyProperties(trdShoppingProductOrder, shoppingProductOrderDto);
        }

        confirmLoanOrderDetailResponse.setShoppingLogisticsOrderDto(shoppingLogisticsOrderDto);

        confirmLoanOrderDetailResponse.setShoppingProductOrderDto(shoppingProductOrderDto);

        return confirmLoanOrderDetailResponse;
    }

    private void onRiskRefused(TrdShoppingLoanOrder trdShoppingLoanOrder){
        List<MqMessage> mqMessageList = new ArrayList<>();
        // 若风控结果为失败 调用cashman接口 归还用户额度（mq消息）
        QuotaGiveBackDto quotaGiveBackDto = new QuotaGiveBackDto(trdShoppingLoanOrder.getUserId(), trdShoppingLoanOrder.getOrderAmount(), trdShoppingLoanOrder.getTraceNo(), ProductCategoryEnum.BIGAMOUNT.getCode());
        mqMessageList.add(new MqMessage(JsonUtils.toJSONString(quotaGiveBackDto), QueueConst.RETURN_QUOTA));
        logger.info("风控结果为失败,归还用户[{}]额度", trdShoppingLoanOrder.getUserId());

        mqMessageService.sendMessageList(mqMessageList);

        //短信通知风控审核失败，SMSService中对发送短信进行exception处理，不向上抛出异常信息
        Boolean isSendShortMsgSuccess = smsService.sendSms(new SmsDto(trdShoppingLoanOrder.getId().toString(), trdShoppingLoanOrder.getUserPhone(), SmsConstant.SMS_RISK_FAILED_ID, ""));
        if (!isSendShortMsgSuccess) {
            logger.info("发送短信失败，商品借款单ID：{}", trdShoppingLoanOrder.getId());
        }
    }

    private void onRiskApproved(TrdShoppingLoanOrder trdShoppingLoanOrder){
        // 通知商品APP解冻产品库存
        ProductAppNotifyDto productAppNotifyDto = new ProductAppNotifyDto(trdShoppingLoanOrder.getProductFreezeNo(), trdShoppingLoanOrder.getId(), true);
        MqMessage mqMessage = new MqMessage(JsonUtils.toJSONString(productAppNotifyDto), QueueConst.PRODUCT_UNFREEZE_QUEUE);
        logger.info("风控结果为成功,扣减商品[{}]库存", trdShoppingLoanOrder.getProductFreezeNo());
        mqMessageService.sendMessage(mqMessage);
    }

    @Transactional
    public void updateShoppingLoanOrderStatus(Long shoppingLoanOrderId, String newStatus, String preStatus){
        int updateLoanOrderNum = trdShoppingLoanOrderService.updateLoanOrderStatus(shoppingLoanOrderId, newStatus, preStatus);

        if (updateLoanOrderNum <= 0) {
            logger.error("更新金融商品订单表状态失败,订单order_no:{}", shoppingLoanOrderId);
            throw new SqlUpdateException("更新金融商品订单表状态失败");
        }

        int updateProductOrderNum = trdShoppingProductOrderService.updateProductOrderStatus(shoppingLoanOrderId, newStatus, preStatus);

        if (updateProductOrderNum <= 0) {
            logger.error("更新商品订单表状态失败,订单order_no:{}", shoppingLoanOrderId);
            throw new SqlUpdateException("更新商品订单表状态失败");
        }

        int updateLogisticsOrderNum = trdShoppingLogisticsOrderService.updateLogisticsOrderStatus(shoppingLoanOrderId, newStatus, preStatus);

        if (updateLogisticsOrderNum <= 0) {
            logger.error("更新物流订单表状态失败,订单order_no:{}", shoppingLoanOrderId);
            throw new SqlUpdateException("更新物流订单表状态失败");
        }
    }

    @Override
    @Transactional
    public void updateVirtualShoppingLoanOrderStatus(TrdShoppingProductOrder trdShoppingProductOrder, String newStatus, String preStatus, SoouuSuccessResponse successResponse) {
        int updateLoanOrderNum;
        //只有之前状态为31的时候才需要记录下单时间
        if (ShoppingLoanOrderStatusEnum.APPROVED.getCode().equals(preStatus)) {
            updateLoanOrderNum = trdShoppingLoanOrderService.updateVirtualLoanOrderStatus(trdShoppingProductOrder.getShoppingLoanOrderId(), newStatus, preStatus, new Date());
        } else {
            updateLoanOrderNum = trdShoppingLoanOrderService.updateLoanOrderStatus(trdShoppingProductOrder.getShoppingLoanOrderId(), newStatus, preStatus);
        }
        if (updateLoanOrderNum <= 0) {
            logger.error("更新金融商品订单表状态失败,订单order_no:{}", trdShoppingProductOrder.getShoppingLoanOrderId());
            throw new SqlUpdateException("更新金融商品订单表状态失败");
        }
        String price = null;
        if (successResponse != null) {
            price = successResponse.getPurchasePrice().toString();
            //卡密入库
            trdShoppingLoanCardInfoService.insert(trdShoppingProductOrder.getId(), trdShoppingProductOrder.getProductName(), trdShoppingProductOrder.getProductUnitPrice(), successResponse);
        }
        //保存第三方订单价格
        int updateProductOrderNum = trdShoppingProductOrderService.updateVirtualProductOrderStatus(trdShoppingProductOrder.getShoppingLoanOrderId(), newStatus, preStatus, price);
        if (updateProductOrderNum <= 0) {
            logger.error("更新商品订单表状态失败,订单order_no:{}", trdShoppingProductOrder.getShoppingLoanOrderId());
            throw new SqlUpdateException("更新商品订单表状态失败");
        }

        int updateLogisticsOrderNum = trdShoppingLogisticsOrderService.updateLogisticsOrderStatus(trdShoppingProductOrder.getShoppingLoanOrderId(), newStatus, preStatus);
        if (updateLogisticsOrderNum <= 0) {
            logger.error("更新物流订单表状态失败,订单order_no:{}", trdShoppingProductOrder.getShoppingLoanOrderId());
            throw new SqlUpdateException("更新物流订单表状态失败");
        }


    }

    @Override
    public IndexLoanOrderDto getUserLastLoanOrder(LoanOrderDto loanOrderDto) {
        TrdShoppingLoanOrder loanOrder= shoppingLoanOrderMapper.getLastLoanOrderByUserIdAndProductCategory(loanOrderDto.getUserId(),
                loanOrderDto.getProductCategory());
        if(loanOrder==null){
            return null;
        }

        // 支付中心放款失败，用户取消，风控推送失败，异常 状态的订单用户应当可以继续借款
        if (loanOrder.getStatus().equals(ShoppingLoanOrderStatusEnum.APPLY.getCode()) ||
                loanOrder.getStatus().equals(LoanOrderStatusEnum.CANCEL.getCode()) ||
                loanOrder.getStatus().equals(LoanOrderStatusEnum.PUSH_FAIL.getCode())||
                loanOrder.getStatus().equals(LoanOrderStatusEnum.FAIL.getCode())){
            return null;
        }

        return new IndexLoanOrderDto(loanOrder.getId(), loanOrder.getStatus(),loanOrder.getProductId(),
                loanOrder.getProductCategory(), loanOrder.getTerm(),
                MoneyUtil.changeCentToYuan(loanOrder.getOrderAmount()), loanOrder.getUpdatedAt());
    }

    /**
     * 订单物流发货
     */
    @Transactional
	public void logisticsShipmentLoan(ShoppingLogisticsOrderReq shoppingLogisticsOrderReq) {
    	
    	// 1、接口幂等验证
        idempotentService.idempotentCheck(IdempotentTypeEnum.LOGISTICS_SHIPMENT_LOAN, shoppingLogisticsOrderReq);

        // 2、更新shopping loan order的状态
		TrdShoppingLoanOrder trdShoppingLoanOrder = trdShoppingLoanOrderService.getById(shoppingLogisticsOrderReq.getLogisticsOrderId());
		if (null == trdShoppingLoanOrder) {
			throw new ServiceException("数据不存在");
		}
		String newStatus = ShoppingLoanOrderStatusEnum.SENDING.getCode();
		String preStatus = trdShoppingLoanOrder.getStatus();
 
		if (!ShoppingLoanOrderStatusEnum.canUpdate(newStatus, preStatus)) {
	        logger.warn("订单状态不允许更新: new status {}, current status{}", newStatus, preStatus);
	        throw new ServiceException("订单状态不允许更新");
	    }
		
		Long shoppingLoanOrderId = trdShoppingLoanOrder.getId();
        this.updateShoppingLoanOrderStatus(shoppingLoanOrderId, newStatus, preStatus);


//        TrdShoppingLogisticsOrder trdShoppingLogisticsOrder = trdShoppingLogisticsOrderService.getByShoppingLoanOrderIdAndOrderType(shoppingLoanOrderId, ShoppingLogisticsOrderTypeEnum.RECEIVE_TYPE.getCode());

        // 3、更新发货信息
        TrdShoppingLogisticsOrder shoppingLogisticsOrder = trdShoppingLogisticsOrderService.getById(shoppingLogisticsOrderReq.getLogisticsOrderId());
        if(null == shoppingLogisticsOrder){
            logger.error("没有收货人信息,订单order_no:{}", shoppingLoanOrderId);
            throw new ServiceException("没有收货人信息,不允许操作");
        }
        BeanUtils.copyProperties(shoppingLogisticsOrderReq, shoppingLogisticsOrder);
        Date now = new Date();
        shoppingLogisticsOrder.setSendTime(now);
        shoppingLogisticsOrder.setOrderType("");
        shoppingLogisticsOrder.setStatus(newStatus);

        trdShoppingLogisticsOrderService.updateLogisticsOrder(shoppingLogisticsOrder, true);


        //新增trace
        String traceNo = trdShoppingLoanOrder.getTraceNo();
        Trace trace = new Trace(traceNo, TraceOrderEventEnum.LOAN_LOGISTICS_SHIPMENT, new Date(), JSON.toJSONString(shoppingLogisticsOrderReq));
        traceMapper.insert(trace);
        

        //定单发货时，发送给cashman-app异步更新还款计划的预期还款时间
        SyncRepaymentPlanDto syncRepaymentPlanDto = new SyncRepaymentPlanDto(shoppingLoanOrderId, now);
        mqMessageService.sendMessage(new MqMessage(JsonUtils.toJSONString(syncRepaymentPlanDto), QueueConst.SYNC_REPAYMENT_PLAN_TIME));
	}

    @Transactional
	public void confirmReceiptLoan(ShoppingConfirmReceiptLoanReq shoppingConfirmReceiptLoanReq) {
    	
		Long shoppingLoanOrderId = shoppingConfirmReceiptLoanReq.getShoppingLoanOrderId();

		// 1、接口幂等验证
		idempotentService.idempotentCheck(IdempotentTypeEnum.CONFIRM_RECEIPT_LOAN, shoppingConfirmReceiptLoanReq);
		TrdShoppingLoanOrder trdShoppingLoanOrder = trdShoppingLoanOrderService.getById(shoppingLoanOrderId);
		if (null == trdShoppingLoanOrder) {
			throw new ServiceException("数据不存在");
		}
		String preStatus = trdShoppingLoanOrder.getStatus();
		String newStatus = ShoppingLoanOrderStatusEnum.RECEIVED.getCode();
		if (!ShoppingLoanOrderStatusEnum.canUpdate(newStatus, preStatus)) {
			logger.warn("订单状态不允许更新: new status {}, current status{}", newStatus, preStatus);
			throw new ServiceException("订单状态不允许更新");
		}
		this.updateShoppingLoanOrderStatus(shoppingLoanOrderId, newStatus, preStatus);
	}


    @Override
    @Transactional
    public void cardOrderApply(TrdShoppingLoanOrder trdShoppingLoanOrder) {
        TrdShoppingProductOrder trdShoppingProductOrder = trdShoppingProductOrderService.getByShoppingLoanOrderId(trdShoppingLoanOrder.getId());
        if (null == trdShoppingProductOrder) {
            logger.warn("TrdShoppingLoanOrder id:{} do not hava a TrdShoppingProductOrder!", trdShoppingLoanOrder.getId());
            throw new ServiceException("该订单没有产品订单");
        }
        String traceNo = trdShoppingLoanOrder.getTraceNo();
        try {
            logger.info("start call soouu card-order-add,request parame:{},{},{}",Long.toString(trdShoppingProductOrder.getId()), trdShoppingProductOrder.getThirdProductId(), trdShoppingProductOrder.getProductNumber());
            SoouuSuccessResponse successResponse = openApiRemoteSoouuService.cardOrderAdd(Long.toString(trdShoppingProductOrder.getId()), trdShoppingProductOrder.getThirdProductId(), trdShoppingProductOrder.getProductNumber());



            List<SoouuCard> cards = successResponse.getCards();
            if (cards != null && cards.size() > 0) {
//                StringBuilder smsContent = new StringBuilder();
                /*for (SoouuCard card:cards) {
                    int index = cards.indexOf(card) + 1;
                    smsContent.append("卡号");
                    smsContent.append(cards.size()>1? index: "");
                    smsContent.append("：");
                    smsContent.append(card.getCardNumber());
                    smsContent.append("，密码");
                    smsContent.append(cards.size()>1? index: "");
                    smsContent.append("：");
                    smsContent.append(card.getCardPwd());
                    smsContent.append("；");
                }
                smsContent.append(",");*/
                String smsContentStr = "卡号及密码在“APP-商品订单详情”查看";
                //短信通知用户卡密信息，不向上抛出异常信息
                Boolean isSendShortMsgSuccess = smsService.sendSms(new SmsDto(trdShoppingLoanOrder.getId().toString(), trdShoppingLoanOrder.getUserPhone(), SmsConstant.SMS_VIRTUAL__CARD_SEND_ID, smsContentStr));
                if (!isSendShortMsgSuccess) {
                    logger.info("发送短信失败，商品借款单ID：{}", trdShoppingLoanOrder.getId());
                }
            }
            //下单成功，拿到结果后状态变成已发货
            updateVirtualShoppingLoanOrderStatus(trdShoppingProductOrder, ShoppingLoanOrderStatusEnum.RECEIVED.getCode(), ShoppingLoanOrderStatusEnum.APPROVED.getCode(), successResponse);


            //新增trace
            Trace trace = new Trace(traceNo, TraceOrderEventEnum.THIRD_APPLY_SUCCESS, new Date(), JSON.toJSONString(trdShoppingLoanOrder));
            traceMapper.insert(trace);
            //定单发货时，发送给cashman-app异步更新还款计划的预期还款时间
            logger.info("start to call cashman-app, syncRepaymentPlanTime, request parameter:{},{}",trdShoppingLoanOrder.getId(), new Date());
            cashmanAppRemoteService.syncRepaymentPlanTime(trdShoppingLoanOrder.getId(), new Date());



        }  catch (ServiceException se) {
            //下单异常，针对异常code做处理
            String errorCode = se.getCode();
            logger.info("call soouu order add exception:{}", errorCode);
            //errorcode 为1206  订单转60状态，做查询操作
            if (SoouuLoanOrderErrorEnum.ERR_1206.getCode().equals(errorCode)
                    || SoouuLoanOrderErrorEnum.ERR_2111.getCode().equals(errorCode)
                    || SoouuLoanOrderErrorEnum.ERR_2115.getCode().equals(errorCode)
                    || SoouuLoanOrderErrorEnum.ERR_2407.getCode().equals(errorCode)) {
                logger.info("to change order status to 60");
                updateVirtualShoppingLoanOrderStatus(trdShoppingProductOrder, ShoppingLoanOrderStatusEnum.THIRD_ORDERED.getCode(), ShoppingLoanOrderStatusEnum.APPROVED.getCode(), null);

            }
            //errorcode 做关单操作
            if (SoouuLoanOrderErrorEnum.ERR_2109.getCode().equals(errorCode)
                    || SoouuLoanOrderErrorEnum.ERR_2110.getCode().equals(errorCode)) {
                updateShoppingLoanOrderStatus(trdShoppingLoanOrder.getId(), ShoppingLoanOrderStatusEnum.THIRD_ORDERED_FAIL.getCode(), ShoppingLoanOrderStatusEnum.APPROVED.getCode());
                //新增trace
                Trace trace = new Trace(traceNo, TraceOrderEventEnum.THIRD_APPLY_FAIL, new Date(), JSON.toJSONString(trdShoppingLoanOrder));
                traceMapper.insert(trace);

                List<MqMessage> mqMessageList = new ArrayList<>();

                //发送MQ消息(发给userapp的都是大额,userapp不能识别商城的订单)
                QuotaGiveBackDto quotaGiveBackDto = new QuotaGiveBackDto(trdShoppingLoanOrder.getUserId(),
                        trdShoppingLoanOrder.getOrderAmount(), trdShoppingLoanOrder.getTraceNo(),ProductCategoryEnum.BIGAMOUNT.getCode());

                //若是失败，调用Mq client客户端发送消息给 userapp 归还额度
                mqMessageList.add(new MqMessage(JSON.toJSONString(quotaGiveBackDto),QueueConst.RETURN_QUOTA));
                logger.info("start to call mq, RETURN_QUOTA, request parameter:{}",JsonUtils.toJSONString(quotaGiveBackDto));
                logger.info("下单失败恢复额度,{}",quotaGiveBackDto);


                //发送MQ到cashman-app/同步cashman-app 订单状态
                SyncLoanOrderReq syncLoanOrderReq = new SyncLoanOrderReq();
                syncLoanOrderReq.setLoanOrderId(trdShoppingLoanOrder.getId());
                syncLoanOrderReq.setStatus(LoanOrderStatusEnum.LOAN_FAIL.getCode());
                syncLoanOrderReq.setProductCategory(ProductCategoryEnum.PRODUCT_CATEGORY_SHOPPING.getCode());
                logger.info("start sync status to cashman-app, request info: {}", JsonUtils.toJSONString(syncLoanOrderReq));
                mqMessageList.add(new MqMessage(JsonUtils.toJSONString(syncLoanOrderReq), QueueConst.SYNC_VIRTUAL_ORDER_STATUS));

                mqMessageService.sendMessageList(mqMessageList);
            }

        }

    }

    @Override
    @Transactional
    public void cardOrderGet(TrdShoppingLoanOrder trdShoppingLoanOrder) {
        TrdShoppingProductOrder trdShoppingProductOrder = trdShoppingProductOrderService.getByShoppingLoanOrderId(trdShoppingLoanOrder.getId());
        if (null == trdShoppingProductOrder) {
            logger.warn("TrdShoppingLoanOrder id:{} do not hava a TrdShoppingProductOrder!", trdShoppingLoanOrder.getId());
            throw new ServiceException("该订单没有产品订单");
        }
        String traceNo = trdShoppingLoanOrder.getTraceNo();

        try {
            //订单查询
            SoouuSuccessResponse successResponse = openApiRemoteSoouuService.cardOrderGet(trdShoppingProductOrder.getId());

            List<SoouuCard> cards = successResponse.getCards();
            if (cards != null && cards.size() > 0) {
                /*StringBuilder smsContent = new StringBuilder();
                for (SoouuCard card:cards) {
                    Integer index = cards.indexOf(card) + 1;
                    smsContent.append("卡号");
                    smsContent.append(cards.size()>1? index: "");
                    smsContent.append("：");
                    smsContent.append(card.getCardNumber());
                    smsContent.append("，密码");
                    smsContent.append(cards.size()>1? index: "");
                    smsContent.append("：");
                    smsContent.append(card.getCardPwd());
                    smsContent.append("；");
                }
                smsContent.append(",");*/
                String smsContentStr = "卡号及密码在“APP-商品订单详情”查看";
                //短信通知用户卡密信息，不向上抛出异常信息
                Boolean isSendShortMsgSuccess = smsService.sendSms(new SmsDto(trdShoppingLoanOrder.getId().toString(), trdShoppingLoanOrder.getUserPhone(), SmsConstant.SMS_VIRTUAL__CARD_SEND_ID, smsContentStr));
                if (!isSendShortMsgSuccess) {
                    logger.info("发送短信失败，商品借款单ID：{}", trdShoppingLoanOrder.getId());
                }
            }
            //下单成功，拿到结果后状态变成已发货
            updateVirtualShoppingLoanOrderStatus(trdShoppingProductOrder, ShoppingLoanOrderStatusEnum.RECEIVED.getCode(), ShoppingLoanOrderStatusEnum.THIRD_ORDERED.getCode(), successResponse);

            //新增trace
            Trace trace = new Trace(traceNo, TraceOrderEventEnum.THIRD_APPLY_SUCCESS, new Date(), JSON.toJSONString(trdShoppingLoanOrder));
            traceMapper.insert(trace);
            //定单发货时，发送给cashman-app异步更新还款计划的预期还款时间
            logger.info("start to call cashman-app, syncRepaymentPlanTime, request parameter:{},{}",trdShoppingLoanOrder.getId(), new Date());
            cashmanAppRemoteService.syncRepaymentPlanTime(trdShoppingLoanOrder.getId(), new Date());


        } catch (ServiceException se) {
            String errorCode = se.getCode();
            //errorcode 做关单操作
            if (SoouuLoanOrderErrorEnum.ERR_2200.getCode().equals(errorCode)) {
                updateShoppingLoanOrderStatus(trdShoppingLoanOrder.getId(), ShoppingLoanOrderStatusEnum.THIRD_ORDERED_FAIL.getCode(), ShoppingLoanOrderStatusEnum.THIRD_ORDERED.getCode());
                //新增trace
                Trace trace = new Trace(traceNo, TraceOrderEventEnum.THIRD_APPLY_FAIL, new Date(), JSON.toJSONString(trdShoppingLoanOrder));
                traceMapper.insert(trace);

                List<MqMessage> mqMessageList = new ArrayList<>();

                //发送MQ消息
                QuotaGiveBackDto quotaGiveBackDto = new QuotaGiveBackDto(trdShoppingLoanOrder.getUserId(),
                        trdShoppingLoanOrder.getOrderAmount(), trdShoppingLoanOrder.getTraceNo(),trdShoppingLoanOrder.getProductCategory());
                //若是失败，调用Mq client客户端发送消息给 userapp 归还额度
                logger.info("start to call mq, RETURN_QUOTA, request parameter:{}",JsonUtils.toJSONString(quotaGiveBackDto));
                mqMessageList.add(new MqMessage(JSON.toJSONString(quotaGiveBackDto),QueueConst.RETURN_QUOTA));
                logger.info("下单失败恢复额度,{}",quotaGiveBackDto);


                //发送MQ到cashman-app/同步cashman-app 订单状态
                SyncLoanOrderReq syncLoanOrderReq = new SyncLoanOrderReq();
                syncLoanOrderReq.setLoanOrderId(trdShoppingLoanOrder.getId());
                syncLoanOrderReq.setStatus(LoanOrderStatusEnum.LOAN_FAIL.getCode());
                syncLoanOrderReq.setProductCategory(ProductCategoryEnum.PRODUCT_CATEGORY_SHOPPING.getCode());
                logger.info("start sync status to cashman-app, request info: {}", JsonUtils.toJSONString(syncLoanOrderReq));
                mqMessageList.add(new MqMessage(JsonUtils.toJSONString(syncLoanOrderReq), QueueConst.SYNC_VIRTUAL_ORDER_STATUS));

                mqMessageService.sendMessageList(mqMessageList);

            }

        }

    }
}
