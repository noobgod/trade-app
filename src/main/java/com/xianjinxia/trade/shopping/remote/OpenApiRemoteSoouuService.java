package com.xianjinxia.trade.shopping.remote;

import com.alibaba.fastjson.JSON;
import com.dianping.cat.common.EventInfo;
import com.dianping.cat.utils.CatUtils;
import com.xianjinxia.trade.platform.response.OpenApiBaseResponse;
import com.xianjinxia.trade.shared.exception.ServiceException;
import com.xianjinxia.trade.shared.remote.BaseRemoteService;
import com.xianjinxia.trade.shopping.openapi.SoouuOrderApplyRequest;
import com.xianjinxia.trade.shopping.response.soouu.SoouuFailResponse;
import com.xianjinxia.trade.shopping.response.soouu.SoouuSuccessResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;


@Service
public class OpenApiRemoteSoouuService extends BaseRemoteService {

    private static final String CARD_ORDER_ADD_URL = "/soouu-api/order/add";
    private static final String CARD_ORDER_GET_URL = "/soouu-api/order/get";

    private static final Logger logger = LoggerFactory.getLogger(OpenApiRemoteSoouuService.class);


    @Override
    protected String getServiceName() {
        return "OPEN-API";
    }

    public SoouuSuccessResponse cardOrderAdd(String orderNo, String thirdProductId, Integer buyNumber) {
        EventInfo eventInfo = new EventInfo();
        eventInfo.setEventType("soouu-order-add");
        eventInfo.put("orderNo", orderNo);
        eventInfo.put("thirdProductId", thirdProductId);
        eventInfo.put("buyNumber", buyNumber);
        String url = super.buildUrl(CARD_ORDER_ADD_URL);
        OpenApiBaseResponse response =
                myRestTemplate.httpPost(url, new SoouuOrderApplyRequest(orderNo, thirdProductId, Integer.toString(buyNumber)), new ParameterizedTypeReference<OpenApiBaseResponse>() {
                });
        String soouuResponse = (String) response.getData();
        logger.warn("call soouu api: order add , orderNo:{},thirdProductId:{},buyNumber:{}, result:{}} ", orderNo, thirdProductId, buyNumber, soouuResponse);
        if (!OpenApiBaseResponse.ResponseCode.SUCCESS.getValue().equals(response.getCode())) {
            eventInfo.put("soouuResult", soouuResponse);
            CatUtils.error(eventInfo);
            SoouuFailResponse failResponseObject = JSON.parseObject(String.valueOf(soouuResponse), SoouuFailResponse.class);
            throw new ServiceException("soouu_" + failResponseObject.getMessageCode(), failResponseObject.getMessageInfo());
        }
        return JSON.parseObject(String.valueOf(soouuResponse), SoouuSuccessResponse.class);
    }

    public SoouuSuccessResponse cardOrderGet(Long orderNo) {
        String url = super.buildUrl(CARD_ORDER_GET_URL);
        EventInfo eventInfo = new EventInfo();
        eventInfo.setEventType("soouu-order-get");
        eventInfo.put("orderNo", orderNo);
        OpenApiBaseResponse baseResponse = myRestTemplate.httpPost(url,
                orderNo,
                new ParameterizedTypeReference<OpenApiBaseResponse>() {
                });
        String soouuResponse = (String) baseResponse.getData();
        logger.warn("call soouu api: order get ， orderNo:{}, result:{}} ", orderNo, soouuResponse);
        if (!OpenApiBaseResponse.ResponseCode.SUCCESS.getValue().equals(baseResponse.getCode())) {
            eventInfo.put("soouuResult", soouuResponse);
            CatUtils.error(eventInfo);
            SoouuFailResponse failResponseObject = JSON.parseObject(String.valueOf(soouuResponse), SoouuFailResponse.class);
            throw new ServiceException("soouu_" + failResponseObject.getMessageCode(), failResponseObject.getMessageInfo());
        }
        return JSON.parseObject(String.valueOf(soouuResponse), SoouuSuccessResponse.class);
    }
}
