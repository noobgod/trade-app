package com.xianjinxia.trade.pet.service.action.petsettle;
import java.math.BigDecimal;

import com.xianjinxia.trade.pet.dto.SettleCoinReqDto;
import com.xianjinxia.trade.pet.dto.SettlePetReqDto;
import com.xianjinxia.trade.pet.dto.SettleReqDto;
import com.xianjinxia.trade.pet.remote.AssetRemoteService;
import com.xianjinxia.trade.pet.response.PetBaseResponse.ResponseCode;
import com.xianjinxia.trade.pet.service.action.Action;
import com.xianjinxia.trade.shared.domain.TransferReqeustDetails;
import com.xianjinxia.trade.shared.domain.TransferRequest;
import com.xianjinxia.trade.shared.domain.TrdSettleRecord;
import com.xianjinxia.trade.shared.enums.pet.PetSettleStatusEnum;
import com.xianjinxia.trade.shared.enums.pet.TransferSourceTypeEnum;
import com.xianjinxia.trade.shared.enums.pet.TransferStatusEnum;
import com.xianjinxia.trade.shared.enums.pet.TransferTypeEnum;
import com.xianjinxia.trade.shared.exception.ServiceException;
import com.xianjinxia.trade.shared.mapper.TransferReqeustDetailsMapper;
import com.xianjinxia.trade.shared.mapper.TransferRequestMapper;
import com.xianjinxia.trade.shared.mapper.TrdSettleRecordMapper;
import com.xianjinxia.trade.shared.utils.UniqueIdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 交割单处理
 * Created by Myron on 2017/6/29.
 */
@Service("petOnChainActionExec")
public class PetOnChainActionExec implements Action<TrdSettleRecord,TrdSettleRecord> {

    @Autowired
    AssetRemoteService assetRemoteService;

    @Autowired
    private TrdSettleRecordMapper trdSettleRecordMapper;

    @Autowired
    private TransferRequestMapper requestMapper;

    @Autowired
    private TransferReqeustDetailsMapper reqeustDetailsMapper;

    @Override
    public Long create(TrdSettleRecord settleRecord) throws Exception {
        return null;
    }

    @Override
    public void execute(Long actionId) {
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void execute(TrdSettleRecord settleRecord) {
        int settleResult = trdSettleRecordMapper.updateStatusByIdPreStatus(settleRecord.getId(),
                PetSettleStatusEnum.PAYING.getCode(), settleRecord.getStatus(), null);
        if (settleResult < 1) {
            throw new ServiceException(ResponseCode.SYS_ERROR.getValue(), "更新交割表状态失败");
        }

        String uniNo = UniqueIdUtil.getPetTradeNoUniqueId();
        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setReqNo(uniNo);
        transferRequest.setSourceId(settleRecord.getSettleNo());
        transferRequest.setSourceType(TransferSourceTypeEnum.TRD_SETTLE_RECORD.getCode());
        transferRequest.setStatus(TransferStatusEnum.REQUEST_APPLYING.getCode());
        transferRequest.setAppyleTime(new Date());
        requestMapper.insertSelective(transferRequest);

        TransferReqeustDetails reqeustDetails = new TransferReqeustDetails();
        reqeustDetails.setTransferRequestId(transferRequest.getId());
        reqeustDetails.setFromUserId(settleRecord.getFromUserId());
        reqeustDetails.setCoinNum(settleRecord.getPrice());
        reqeustDetails.setMediumId(settleRecord.getPetId());
        reqeustDetails.setToUserId(settleRecord.getToUserId());
        reqeustDetails.setTransactionType(TransferTypeEnum.REQUEST_ALL.getCode());
        reqeustDetails.setStatus(TransferStatusEnum.REQUEST_APPLYING.getCode());
        reqeustDetailsMapper.insertSelective(reqeustDetails);

        SettleReqDto settleReqDto = new SettleReqDto();
        SettlePetReqDto settlePetReqDto = new SettlePetReqDto();
        settlePetReqDto.setFrozenNo(settleRecord.getFromFrozenNo());
        settlePetReqDto.setOriginUserId(settleRecord.getFromUserId());
        settlePetReqDto.setTargetUserId(settleRecord.getToUserId());

        SettleCoinReqDto settleCoinReqDto = new SettleCoinReqDto();
        settleCoinReqDto.setFrozenNo(settleRecord.getToFrozenNo());
        settleCoinReqDto.setOriginUserId(settleRecord.getToUserId());
        settleCoinReqDto.setTargetUserId(settleRecord.getFromUserId());

        settleReqDto.setPet(settlePetReqDto);
        settleReqDto.setPoint(settleCoinReqDto);

        assetRemoteService.settleReq(uniNo, settleReqDto);

    }
}
