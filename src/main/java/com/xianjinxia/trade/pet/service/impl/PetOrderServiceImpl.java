package com.xianjinxia.trade.pet.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xianjinxia.trade.pet.dto.BaseAssetRespDto;
import com.xianjinxia.trade.pet.dto.PetAsset;
import com.xianjinxia.trade.pet.dto.PointAccountDto;
import com.xianjinxia.trade.pet.remote.AssetRemoteService;
import com.xianjinxia.trade.pet.request.BuyPetReq;
import com.xianjinxia.trade.pet.request.PetOrderInfoReq;
import com.xianjinxia.trade.pet.request.QuerySaleOrdersReq;
import com.xianjinxia.trade.pet.request.SalePetReq;
import com.xianjinxia.trade.pet.request.UnSalePetReq;
import com.xianjinxia.trade.pet.request.UserSaleOrdersReq;
import com.xianjinxia.trade.pet.response.AssetBaseResponse;
import com.xianjinxia.trade.pet.response.PetBaseResponse.ResponseCode;
import com.xianjinxia.trade.pet.service.IPetOrderService;
import com.xianjinxia.trade.pet.service.action.petsettle.PetOnChainActionExec;
import com.xianjinxia.trade.shared.constant.PetConstant;
import com.xianjinxia.trade.shared.domain.TransferRequest;
import com.xianjinxia.trade.shared.domain.TrdSaleOrderRequest;
import com.xianjinxia.trade.shared.domain.TrdSettleRecord;
import com.xianjinxia.trade.shared.enums.pet.PetOrderStatusEnum;
import com.xianjinxia.trade.shared.enums.pet.PetSettleOrderTypeEnum;
import com.xianjinxia.trade.shared.enums.pet.PetSettleSourceTypeEnum;
import com.xianjinxia.trade.shared.enums.pet.PetSettleStatusEnum;
import com.xianjinxia.trade.shared.enums.pet.PetTxAbletStatusEnum;
import com.xianjinxia.trade.shared.enums.pet.TransferStatusEnum;
import com.xianjinxia.trade.shared.exception.ServiceException;
import com.xianjinxia.trade.shared.mapper.TransferReqeustDetailsMapper;
import com.xianjinxia.trade.shared.mapper.TransferRequestMapper;
import com.xianjinxia.trade.shared.mapper.TrdSaleOrderRequestMapper;
import com.xianjinxia.trade.shared.mapper.TrdSettleRecordMapper;
import com.xianjinxia.trade.shared.utils.GsonUtil;
import com.xianjinxia.trade.shared.utils.UniqueIdUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author liuzhifang
 *
 *  2017年9月6日
 */
@Service
public class PetOrderServiceImpl implements IPetOrderService {
    private static final Logger logger = LoggerFactory.getLogger(PetOrderServiceImpl.class);

    @Autowired
    private TrdSaleOrderRequestMapper trdSaleOrderRequestMapper;

    @Autowired
    private TrdSettleRecordMapper trdSettleRecordMapper;

    @Autowired
    AssetRemoteService assetRemoteService;

    @Autowired
    PetOnChainActionExec petOnChainActionExec;

    @Autowired
    private TransferRequestMapper requestMapper;

    @Autowired
    private TransferReqeustDetailsMapper reqeustDetailsMapper;

    @Override
    public PageInfo<TrdSaleOrderRequest> selectPageOrders(QuerySaleOrdersReq querySaleOrdersReq) {
        logger.info("params:{}", querySaleOrdersReq);
        String orderBy = querySaleOrdersReq.getSortBy() == null ? PetConstant.ORDER_BY_ORDER_TIME
                :querySaleOrdersReq.getSortBy().replaceAll(PetConstant.ORDER_BY_SPLIT, PetConstant.ORDER_BY_SPACE);
        PageHelper.startPage(querySaleOrdersReq.getPageNum(), querySaleOrdersReq.getPageSize());
        List<TrdSaleOrderRequest> list = trdSaleOrderRequestMapper.selectByStatus(PetOrderStatusEnum.SELLING.getCode()
                , orderBy);
        Page<TrdSaleOrderRequest> page = (Page<TrdSaleOrderRequest>) list;
        logger.info("result:{}", page);
        return page.toPageInfo();
    }


    @Override
    public PageInfo<TrdSettleRecord> selectOrdersByUserId(UserSaleOrdersReq userSaleOrdersReq) {
        logger.info("params:{}", userSaleOrdersReq);
        PageHelper.startPage(userSaleOrdersReq.getPageNum(), userSaleOrdersReq.getPageSize());
        List<TrdSettleRecord> list = trdSettleRecordMapper.selectOrdersByUserId(
                userSaleOrdersReq.getUserId(), "xxxxxx");
        Page<TrdSettleRecord> page = (Page<TrdSettleRecord>) list;
        logger.info("result:{}", page);
        return page.toPageInfo();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseCode salePet(SalePetReq salePetReq) {
        Long petId = salePetReq.getPetId();
        Long userId = salePetReq.getUserId();
        if (BigDecimal.ZERO.compareTo(salePetReq.getPrice()) >= 0) {
            return ResponseCode.PARAM_CHECK_FAIL;
        }

        PetAsset petInfo = assetRemoteService.getPetInfo(petId);
        if (petInfo == null) {
            return ResponseCode.PARAM_CHECK_FAIL;
        } else if (!userId.equals(petInfo.getUserId())) {
            return ResponseCode.PET_SELLED;
        } else if (PetTxAbletStatusEnum.FROZENED.getCode().equals(petInfo.getTxAbleStatus())) {
            return ResponseCode.PET_FROZEN;
        } else if (PetTxAbletStatusEnum.ONGOING.getCode().equals(petInfo.getTxAbleStatus())) {
            return ResponseCode.PET_ONGOING;
        }

        TrdSaleOrderRequest trdSaleOrderRequest = trdSaleOrderRequestMapper.selectUserSalePet(userId, petId);
        if (trdSaleOrderRequest != null
                && !PetOrderStatusEnum.SELLING_CANCEL.getCode().equals(trdSaleOrderRequest.getStatus())) {
            return ResponseCode.PET_SELLING;
        }

        BaseAssetRespDto baseAssetRespDto = assetRemoteService.frozenPet(userId, petId);
        if (StringUtils.isEmpty(baseAssetRespDto.getFlNo())) {
            return ResponseCode.PET_FROZEN_FAIL;
        }
        TrdSaleOrderRequest saleOrderRequest = new TrdSaleOrderRequest();
        saleOrderRequest.setSaleNo(UniqueIdUtil.getPetTradeNoUniqueId());
        saleOrderRequest.setUserId(userId);
        saleOrderRequest.setUserName(salePetReq.getUserName());
        saleOrderRequest.setPetId(petId);
        saleOrderRequest.setPetName(petInfo.getName());
        saleOrderRequest.setPetFrozenNo(baseAssetRespDto.getFlNo());
        saleOrderRequest.setPetCode(petInfo.getCode());
//        saleOrderRequest.setPetImg(petInfo.get);
        saleOrderRequest.setPetGenerateLevel(petInfo.getGenerateLevel());
        saleOrderRequest.setPetPropertyDetail(GsonUtil.toGson(petInfo.getPropertyDetail()));
        saleOrderRequest.setPetVarity(petInfo.getVarity());
        saleOrderRequest.setPrice(salePetReq.getPrice());
        saleOrderRequest.setStatus(PetOrderStatusEnum.SELLING.getCode());
        saleOrderRequest.setApplyTime(new Date());

        trdSaleOrderRequestMapper.insertSelective(saleOrderRequest);

        return ResponseCode.SUCCESS;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseCode unSalePet(UnSalePetReq unSalePetReq) {
        Long userId = unSalePetReq.getUserId();
        Long petId = unSalePetReq.getPetId();
        TrdSaleOrderRequest saleOrderRequest = trdSaleOrderRequestMapper.selectUserSalePet(
                userId, petId);

        ResponseCode checkResp = checkSaleOrder(saleOrderRequest);
        if (checkResp != null) {
            return checkResp;
        }

        // 更新状态
        trdSaleOrderRequestMapper.updateByUserSaleNo(unSalePetReq.getUserId(), saleOrderRequest.getSaleNo(),
                PetOrderStatusEnum.SELLING_CANCEL.getCode(), PetOrderStatusEnum.SELLING.getCode());

        // 解冻宠物
        assetRemoteService.unFrozenPet(saleOrderRequest.getPetFrozenNo());
        return ResponseCode.SUCCESS;
    }

    /**
     * 订单校验
     * @param saleOrderRequest
     * @return
     */
    private ResponseCode checkSaleOrder(TrdSaleOrderRequest saleOrderRequest) {
        if (saleOrderRequest == null) {
            return ResponseCode.PET_SELLED;
        }
        // 状态校验
        else if (StringUtils.isEmpty(saleOrderRequest.getPetFrozenNo())) {
            return ResponseCode.OPER_UNSALE_QUICK;
        } else if (PetOrderStatusEnum.PAYING.getCode().equals(saleOrderRequest.getStatus())) {
            return ResponseCode.PET_ONGOING;
        } else if (PetOrderStatusEnum.SELLING_CANCEL.getCode().equals(saleOrderRequest.getStatus())) {
            return ResponseCode.OPER_SUCCESS;
        } else if (PetOrderStatusEnum.SUCCESS.getCode().equals(saleOrderRequest.getStatus())) {
            return ResponseCode.PET_SELLED;
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseCode createSettle(BuyPetReq buyPetReq) {
        Long userId = buyPetReq.getUserId();
        TrdSaleOrderRequest saleOrderRequest = trdSaleOrderRequestMapper.selectSaleBySaleNo(buyPetReq.getSaleNo());

        ResponseCode checkResp = checkSaleOrder(saleOrderRequest);
        if (checkResp != null) {
            return checkResp;
        } else if (userId.equals(saleOrderRequest.getUserId())){
            return ResponseCode.PET_BELONGED;
        }
        BigDecimal totalPrice = saleOrderRequest.getPrice().add(PetConstant.EXPECT_FEE);
//        else if (price.compareTo(saleOrderRequest.getPrice()) != 0) {
//            return ResponseCode.PET_PRICE_CHANGED;
//        }

        PointAccountDto pointAccountDto = assetRemoteService.getAccountInfo(userId);
        if (pointAccountDto == null) {
            return ResponseCode.PARAM_CHECK_FAIL;
        }else if (totalPrice.compareTo(pointAccountDto.getAvaliableShare()) > 0) {
            return ResponseCode.COIN_NOT_ENOUGH;
        }

        // 更新状态
        int saleResult = trdSaleOrderRequestMapper.updateByUserSaleNo(saleOrderRequest.getUserId(),
                saleOrderRequest.getSaleNo(),
                PetOrderStatusEnum.PAYING.getCode(), PetOrderStatusEnum.SELLING.getCode());
        if (saleResult < 1) {
            return ResponseCode.PET_SELLED;
        }

        BaseAssetRespDto baseAssetRespDto = assetRemoteService.frozenCoin(userId, saleOrderRequest.getPrice(),
                PetConstant.EXPECT_FEE);
        if (StringUtils.isEmpty(baseAssetRespDto.getFlNo())) {
            return ResponseCode.COIN_FROZEN_FAIL;
        }

        TrdSettleRecord record = new TrdSettleRecord();
        record.setSourceId(saleOrderRequest.getSaleNo());
        record.setSourceType(PetSettleSourceTypeEnum.TRD_SALE_ORDER_REQUEST.getCode());
        record.setSettleNo(UniqueIdUtil.getPetTradeNoUniqueId());
        record.setSettleOderType(PetSettleOrderTypeEnum.TYPE_PET_SALE.getCode());
        record.setFromUserId(saleOrderRequest.getUserId());
        record.setFromUserName(saleOrderRequest.getUserName());
        record.setToUserId(userId);
        record.setToUserName(userId.toString());
        record.setPrice(saleOrderRequest.getPrice());
        record.setExpecteServiceCharge(PetConstant.EXPECT_FEE);
//        record.setActualServiceCharge();
        record.setPaymentTime(new Date());
        record.setStatus(PetSettleStatusEnum.BUYING.getCode());
        record.setPetName(saleOrderRequest.getPetName());
        record.setPetImg(saleOrderRequest.getPetImg());
        record.setPetPropertyDetail(saleOrderRequest.getPetPropertyDetail());
        record.setPetVarity(saleOrderRequest.getPetVarity());
        record.setFromFrozenNo(saleOrderRequest.getPetFrozenNo());
        record.setToFrozenNo(baseAssetRespDto.getFlNo());
//        record.setRemark("");
        record.setPetId(saleOrderRequest.getPetId());
        record.setPetCode(saleOrderRequest.getPetCode());
        record.setPetGenerateLevel(saleOrderRequest.getPetGenerateLevel());

        trdSettleRecordMapper.insertSelective(record);


        return ResponseCode.SUCCESS;
    }

    @Override
    public void handleSettle() {
        PageHelper.startPage(1, 200);
        List<TrdSettleRecord> trdSettleRecords = trdSettleRecordMapper.selectOrdersByStatus(
                PetSettleStatusEnum.BUYING.getCode());

        for (TrdSettleRecord trdSettleRecord: trdSettleRecords) {
            logger.debug("交割单入链处理，参数:{}", trdSettleRecord);
            try {
                petOnChainActionExec.execute(trdSettleRecord);
            } catch (Exception e) {
                logger.error("交割单请求处理失败, 交割单编号：" + trdSettleRecord.getSettleNo(), e);
            }
        }
    }

    @Override
    public TrdSaleOrderRequest getPetOrderInfo(PetOrderInfoReq petOrderInfoReq) {
        return trdSaleOrderRequestMapper.selectSaleBySaleNo(petOrderInfoReq.getSaleNo());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseCode settleCallback(AssetBaseResponse<BaseAssetRespDto> assetBaseResponse) {
        BaseAssetRespDto baseAssetRespDto = assetBaseResponse.getData();
        String reqNo = baseAssetRespDto.getTxNo();
        TransferRequest transferRequest = requestMapper.selectByReqNo(reqNo);
        if (transferRequest == null) {
            return ResponseCode.BIZ_FAIL;
        } else if (!TransferStatusEnum.REQUEST_APPLYING.getCode().equals(transferRequest.getStatus())) {
            return ResponseCode.SUCCESS;
        }
        int reqResult = requestMapper.updateByReqNoStatus(reqNo, TransferStatusEnum.REQUEST_COMPLETE.getCode(),
                TransferStatusEnum.REQUEST_APPLYING.getCode());
        int reqDetailResult = 0;
        if (reqResult > 0) {
            reqDetailResult = reqeustDetailsMapper.updateByReqIdStatus(transferRequest.getId(), TransferStatusEnum.REQUEST_COMPLETE.getCode(),
                    TransferStatusEnum.REQUEST_APPLYING.getCode());
        }
        if (reqResult < 1 || reqResult < 1) {
            throw new ServiceException(ResponseCode.BIZ_FAIL.getValue(), ResponseCode.BIZ_FAIL.getDescription());
        }

        String settleNo = transferRequest.getSourceId();
                TrdSettleRecord trdSettleRecord = trdSettleRecordMapper.selectOrdersBySettleNo(settleNo);
        if (PetSettleStatusEnum.PAYING.getCode().equals(trdSettleRecord.getStatus())) {
            if (AssetBaseResponse.ResponseCode.SUCCESS.getValue().equals(assetBaseResponse.getCode())) {
                commonUpdatePetOrder(baseAssetRespDto, settleNo, trdSettleRecord, PetOrderStatusEnum.SUCCESS.getCode(),
                        PetSettleStatusEnum.SUCCESS.getCode());
            } else {
                commonUpdatePetOrder(baseAssetRespDto, settleNo, trdSettleRecord, PetOrderStatusEnum.SELLING.getCode(),
                        PetSettleStatusEnum.FAIL.getCode());
            }
        }

        return ResponseCode.SUCCESS;
    }

    /**
     * 更新出售单、交割单信息
     * @param baseAssetRespDto
     * @param settleNo
     * @param trdSettleRecord
     */
    private void commonUpdatePetOrder(BaseAssetRespDto baseAssetRespDto, String settleNo,
                                      TrdSettleRecord trdSettleRecord, String petOrderSt, String petSettleSt) {
        int saleResult = 0;
        int settleResult = trdSettleRecordMapper.updateStatusByNoPreStatus(settleNo, petSettleSt,
                PetSettleStatusEnum.PAYING.getCode(), baseAssetRespDto.getGasUsed());
        if (settleResult > 0) {
            saleResult = trdSaleOrderRequestMapper.updateByUserSaleNo(
                    trdSettleRecord.getFromUserId(),
                    trdSettleRecord.getSourceId(),
                    petOrderSt,
                    PetOrderStatusEnum.PAYING.getCode());
        }

        if (settleResult < 1 || saleResult < 1) {
            throw new ServiceException(ResponseCode.BIZ_FAIL.getValue(), ResponseCode.BIZ_FAIL.getDescription());
        }
    }
}
