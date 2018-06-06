package com.xianjinxia.trade.shared.remote;

import com.alibaba.fastjson.JSONObject;
import com.dianping.cat.common.EventInfo;
import com.dianping.cat.utils.CatUtils;
import com.xianjinxia.trade.shared.remote.dto.CashmanUserInfoDto;
import com.xianjinxia.trade.shared.remote.dto.UserInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import com.xianjinxia.trade.shared.exception.ServiceException;
import com.xianjinxia.trade.shared.conf.ExtProperties;
import com.xianjinxia.trade.app.request.BankInfoReq;
import com.xianjinxia.trade.app.response.BaseResponse;
import com.xianjinxia.trade.app.response.BankInfoResponse;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;


@Service
public class OldCashmanRemoteService extends BaseRemoteService {

    private static final String GET_BANK_INFO_BY_BANKID_AND_USERID = "/refactoruser/usercard";

    private static final String GET_USER_INFO_BY_ID = "/refactoruser/userAndUsercard";

    private static final String CHECK_ORDER_INFO = "/refactorOrder/checkOrderInfo";


    @Autowired
    private ExtProperties extProperties;

    @Override
    protected String getServiceName() {
        return extProperties.getOldCashmanServerAddressConfig().getServerAddress();
    }

    public BankInfoResponse getBankInfoByBankIdAndUserId(BankInfoReq bankInfoReq) {
        String url = super.buildUrl(GET_BANK_INFO_BY_BANKID_AND_USERID);
        BaseResponse<BankInfoResponse> baseResponse = myRestTemplate.httpPostWithAbsoluteUrl(url, bankInfoReq, new ParameterizedTypeReference<BaseResponse<BankInfoResponse>>() {
        });
        if (!baseResponse.getCode().equals(BaseResponse.ResponseCode.SUCCESS.getValue())) {
            throw new ServiceException(baseResponse.getCode(), baseResponse.getMsg());
        }
        BankInfoResponse bankInfo = baseResponse.getData();
        if (ObjectUtils.isEmpty(bankInfo)) {
            throw new ServiceException("cashman系统查询用户银行卡为空,用户ID：" + bankInfoReq.getUserId() + "，银行卡ID：" + bankInfoReq.getUserBankCardId());
        }
        return bankInfo;
    }


    public CashmanUserInfoDto getUserInfoByUserId(Long userId) {
        String url = super.buildUrl(GET_USER_INFO_BY_ID);
        CashmanUserInfoDto userInfoDto = new CashmanUserInfoDto();
        userInfoDto.setUserId(userId);
        BaseResponse<CashmanUserInfoDto> baseResponse = myRestTemplate.httpPostWithAbsoluteUrl(url, userInfoDto, new ParameterizedTypeReference<BaseResponse<CashmanUserInfoDto>>() {
        });
        // cashman系统的成功都是以"0"来表示的
        if (!baseResponse.getCode().equals("0")) {
            throw new ServiceException(baseResponse.getCode(), baseResponse.getMsg());
        }
        userInfoDto = baseResponse.getData();
        if (ObjectUtils.isEmpty(userInfoDto)) {
            throw new ServiceException("cashman系统查询用户信息为空， userId："+userId);
        }
        return userInfoDto;
    }


    public Boolean checkOrderInfo(Long userId) {
        String url = super.buildUrl(CHECK_ORDER_INFO + "?userId=" + userId);
        BaseResponse<Boolean> baseResponse = myRestTemplate.httpGetWithAbsoluteUrl(url, new ParameterizedTypeReference<BaseResponse<Boolean>>() {
        });
        // cashman系统的成功都是以"0"来表示的
        if (!baseResponse.getCode().equals("00")) {
            throw new ServiceException(baseResponse.getCode(), baseResponse.getMsg());
        }
        Boolean checkResult = baseResponse.getData();
        if (ObjectUtils.isEmpty(checkResult)) {
            throw new ServiceException("查询验证用户订单失败， userId："+userId);
        }
        return checkResult;
    }
}
