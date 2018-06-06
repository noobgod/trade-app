package com.xianjinxia.trade.platform.remote;

import com.xianjinxia.trade.platform.openapi.OpenApiRequest;
import com.xianjinxia.trade.platform.request.OpenApiConfirmLoanRequest;
import com.xianjinxia.trade.platform.request.OrderNoReq;
import com.xianjinxia.trade.platform.response.LoanOrderStatusResponse;
import com.xianjinxia.trade.platform.response.OpenApiBaseResponse;
import com.xianjinxia.trade.shared.enums.PlatformInterfaceEnum;
import com.xianjinxia.trade.shared.exception.ServiceException;
import com.xianjinxia.trade.shared.remote.BaseRemoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;


@Service
public class OpenApiRemoteService extends BaseRemoteService {

	private static final String OPEN_API_URL = "/inner-gateway";
	
	private static final Logger logger = LoggerFactory.getLogger(OpenApiRemoteService.class);


	@Override
	protected String getServiceName() {
		return "OPEN-API";
	}

	public LoanOrderStatusResponse getOrderStatus(String appId,String orderNo) {
		 String url = super.buildUrl(OPEN_API_URL);
		OpenApiBaseResponse<LoanOrderStatusResponse> response =
	                myRestTemplate.httpPost(url, new OpenApiRequest(PlatformInterfaceEnum.GET_ORDER_STATUS.getCode(),appId,new OrderNoReq(orderNo)),new ParameterizedTypeReference<OpenApiBaseResponse<LoanOrderStatusResponse>>() {
	                        });
        if (!OpenApiBaseResponse.ResponseCode.SUCCESS.getValue().equals(response.getCode())) {
        	throw new ServiceException(response.getCode(), response.getMsg());
        }
        return response.getData();
	}

    public void confirmLoan(String appId,OpenApiConfirmLoanRequest openApiConfirmLoanRequest) {
        String url = super.buildUrl(OPEN_API_URL);
		OpenApiBaseResponse<Void> baseResponse = myRestTemplate.httpPost(url,
                new OpenApiRequest(PlatformInterfaceEnum.CONFIRM_LOAN.getCode(),appId,openApiConfirmLoanRequest),
                new ParameterizedTypeReference<OpenApiBaseResponse<Void>>() {});
        if (!baseResponse.getCode().equals(OpenApiBaseResponse.ResponseCode.SUCCESS.getValue())) {
        	logger.error("push to third part error,orderNo:{},appId:{},return code:{},return msg:{}",openApiConfirmLoanRequest.getOrder_no(),
					appId,baseResponse.getCode(),baseResponse.getMsg());
            throw new ServiceException(baseResponse.getCode(), baseResponse.getMsg());
        }

    }
}
