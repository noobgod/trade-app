package com.xianjinxia.trade.pet.controller;


import com.xianjinxia.trade.pet.dto.BaseAssetRespDto;
import com.xianjinxia.trade.pet.response.AssetBaseResponse;
import com.xianjinxia.trade.pet.response.PetBaseResponse;
import com.xianjinxia.trade.pet.service.IPetOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = "trade_app pet event controller")
@RequestMapping("/service/pet/event")
@RestController
public class EventPetController extends PetBaseController {

    private static final Logger logger = LoggerFactory.getLogger(EventPetController.class);

    @Autowired
    private IPetOrderService petOrderService;
    /**
     * 交割单入链回调
     * @return
     */
    @ApiOperation(value = "settle callback", httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE, notes = "宠物入链回调")
    @ApiImplicitParam(name = "assetBaseResponse", value = "assetBaseResponse", required = true,
            dataType = "AssetBaseResponse<BaseAssetRespDto>")
    @ApiResponse(response = PetBaseResponse.class, message = "baseResponse", code = 200)
    @PostMapping(value = "/settle")
    public String settleCallback(@RequestBody  AssetBaseResponse<BaseAssetRespDto> assetBaseResponse) {
        petOrderService.settleCallback(assetBaseResponse);
        return "ok";
    }
}
