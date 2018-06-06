package com.xianjinxia.trade.pet.controller;

import com.xianjinxia.trade.app.response.BaseResponse;
import com.xianjinxia.trade.pet.dto.PetAsset;
import com.xianjinxia.trade.pet.request.BuyPetReq;
import com.xianjinxia.trade.pet.request.SalePetReq;
import com.xianjinxia.trade.pet.response.PetBaseResponse;
import com.xianjinxia.trade.pet.service.IPetOrderService;
import com.xianjinxia.trade.shared.constant.PetConstant;
import com.xianjinxia.trade.shared.domain.TrdSaleOrderRequest;
import com.xianjinxia.trade.shared.enums.pet.PetOrderStatusEnum;
import com.xianjinxia.trade.shared.mapper.TrdSaleOrderRequestMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Myron
 * 2017年9月8日
 */
@Api(tags = "auto_trade controller")
@RestController
@RequestMapping("/service/pet")
@Configuration
@EnableAsync
@EnableFeignClients
public class AutoTradeController extends PetBaseController {

	@Autowired
	private IPetOrderService petOrderService;
	
    @Autowired
    private TrdSaleOrderRequestMapper trdSaleOrderRequestMapper;
	
    @Autowired
    private AssetQueryApi assetQueryApi;
    
    @ApiOperation(value = "job_buy_pet", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, notes = "宠物购买定时任务")
    @ApiResponse(response = PetBaseResponse.class, message = "baseResponse", code = 200)
    @PostMapping(value = "/random/buy")
    @Async
    public void petBuy() {
    	long platformUserId = assetQueryApi.petWanted(1, "V1").getData().stream().map(pet -> pet.getUserId()).findAny().orElse(0L); 
    	
    	List<TrdSaleOrderRequest> saleOrderRequests = trdSaleOrderRequestMapper.selectByStatusPrices(
                PetOrderStatusEnum.SELLING.getCode(),
                PetConstant.PLAT_FORM_BUY_LOW_PRICE,
                PetConstant.PLAT_FORM_BUY_HIGH_PRICE,
                PetConstant.ORDER_BY_ORDER_PRICE);
    	
    	saleOrderRequests.parallelStream()
    		.map(req -> {
	    		BuyPetReq buyPetReq = new BuyPetReq();
	    		buyPetReq.setUserId(platformUserId);
	            buyPetReq.setSaleNo(req.getSaleNo());
	    		return buyPetReq;
	    	})
	    	.forEach(petOrderService::createSettle);
    }

    @ApiOperation(value = "job_sale_pet", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, notes = "宠物出售定时任务")
    @ApiResponse(response = PetBaseResponse.class, message = "baseResponse", code = 200)
    @PostMapping(value = "/random/sale")
    @Async
    public void petSale() {    	
    	int saleOrderRequests = trdSaleOrderRequestMapper.selectCntByStatusPrices(
                PetOrderStatusEnum.SELLING.getCode(),
                PetConstant.PLAT_FORM_BUY_LOW_PRICE,
                PetConstant.PLAT_FORM_BUY_HIGH_PRICE,
                PetConstant.ORDER_BY_ORDER_PRICE);
    	
        if (saleOrderRequests > PetConstant.PLAT_FORM_SALE_TOTAL) {
        	return ;
        }
    	
        assetQueryApi.petWanted(ThreadLocalRandom.current().nextInt(50, 100), "V" + ThreadLocalRandom.current().nextInt(1, 7))
	        .getData()
	        .parallelStream()
	        .map(pet -> {
	        	SalePetReq salePetReq = new SalePetReq();
	        	salePetReq.setUserId(pet.getUserId());
	            salePetReq.setPetId(pet.getId());
	            salePetReq.setUserName(PetConstant.PLAT_FORM_NAMES[ThreadLocalRandom.current().nextInt(PetConstant.PLAT_FORM_NAMES.length)]);
	            salePetReq.setPrice(BigDecimal.valueOf(1000000000000000L * ThreadLocalRandom.current().nextLong(1000)));
	        	return salePetReq;
	        })
	        .forEach(petOrderService::salePet);
    }
    
	@RequestMapping("/service/r")
	@FeignClient(name = "asset-app")
	public static interface AssetQueryApi{
		
		@GetMapping("/pet/wanted")
	    public BaseResponse<List<PetAsset>> petWanted(@RequestParam("count") Integer count, @RequestParam("varity") String varity);
	}
}
