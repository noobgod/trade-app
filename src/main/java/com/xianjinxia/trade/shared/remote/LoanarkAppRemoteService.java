package com.xianjinxia.trade.shared.remote;

import com.xianjinxia.trade.app.response.BaseResponse;
import com.xianjinxia.trade.shared.exception.ServiceException;
import com.xianjinxia.trade.shared.remote.dto.ProductInfoDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;


@Service
public class LoanarkAppRemoteService extends BaseRemoteService {

    private static final String GET_PRODUCT_BY_CODE = "/service/product/get-by-code";

    @Override
    protected String getServiceName() {
        return "LOANARK-APP";
    }

    public ProductInfoDto getProductInfoByCode(String productCode) {
        String url = super.buildUrl(GET_PRODUCT_BY_CODE) +"?productCode="+productCode;
        BaseResponse<ProductInfoDto> baseResponse = myRestTemplate.httpGet(url, null, new ParameterizedTypeReference<BaseResponse<ProductInfoDto>>() {
        });
        if (!baseResponse.getCode().equals(BaseResponse.ResponseCode.SUCCESS.getValue())) {
            throw new ServiceException(baseResponse.getCode(), baseResponse.getMsg());
        }
        ProductInfoDto productInfoDto = baseResponse.getData();
        if (ObjectUtils.isEmpty(productInfoDto)) {
            throw new ServiceException("loanark-app获取产品信息失败, productCode:" + productCode);
        }
        return productInfoDto;

//        // TODO 张永嘉  mock response
//        ProductInfoDto productInfoDto = new ProductInfoDto();
//        productInfoDto.setProductCode(productCode);
//        productInfoDto.setServiceCompany("XJX");
//        productInfoDto.setProductName("现金侠7天贷款");
//        return productInfoDto;
    }
}
