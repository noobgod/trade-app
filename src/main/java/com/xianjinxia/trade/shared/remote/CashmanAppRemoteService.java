package com.xianjinxia.trade.shared.remote;

import com.xianjinxia.trade.app.response.BaseResponse;
import com.xianjinxia.trade.app.response.mqapp.ResultMsg;
import com.xianjinxia.trade.shared.exception.ServiceException;
import com.xianjinxia.trade.shopping.dto.CashmanRepaymentPlanDto;
import com.xianjinxia.trade.shopping.request.SyncRepaymentPlanReq;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class CashmanAppRemoteService extends BaseRemoteService {

    private static final String GET_REPAYMENT_PLAN_LIST = "/service/repayment/repayment-plan/shopping";

    private static final String SYNC_REPAYMENT_TIME = "/service/shopping/sync-repayment-plan-time";

    @Override
    protected String getServiceName() {
        return "CASHMAN-APP";
    }

    public List<CashmanRepaymentPlanDto> getRepaymentPlanList(Long loanOrderId) {
        String url = super.buildUrl(GET_REPAYMENT_PLAN_LIST) +"?loanOrderId="+loanOrderId;
        BaseResponse<List<CashmanRepaymentPlanDto>> baseResponse = myRestTemplate.httpGet(url, null, new ParameterizedTypeReference<BaseResponse<List<CashmanRepaymentPlanDto>>>() {
        });
        if (!baseResponse.getCode().equals(BaseResponse.ResponseCode.SUCCESS.getValue())) {
            throw new ServiceException(baseResponse.getCode(), baseResponse.getMsg());
        }
        List<CashmanRepaymentPlanDto> data = baseResponse.getData();
        return data;
    }



    public void syncRepaymentPlanTime(Long loanOrderId, Date deliverTime) {
        String url = super.buildUrl(SYNC_REPAYMENT_TIME);

        SyncRepaymentPlanReq syncRepaymentPlanReq = new SyncRepaymentPlanReq(loanOrderId, deliverTime);
        BaseResponse<Void> baseResponse = myRestTemplate.httpPost(url, syncRepaymentPlanReq, new ParameterizedTypeReference<BaseResponse<Void>>() {
        });
        if (!baseResponse.getCode().equals(BaseResponse.ResponseCode.SUCCESS.getValue())) {
            throw new ServiceException(baseResponse.getCode(), baseResponse.getMsg());
        }
    }


}
