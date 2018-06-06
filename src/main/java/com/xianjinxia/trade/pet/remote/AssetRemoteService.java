package com.xianjinxia.trade.pet.remote;

import com.google.gson.JsonObject;
import com.xianjinxia.trade.pet.dto.BaseAssetReqDto;
import com.xianjinxia.trade.pet.dto.BaseAssetRespDto;
import com.xianjinxia.trade.pet.dto.FrozenCoinDto;
import com.xianjinxia.trade.pet.dto.FrozenPetDto;
import com.xianjinxia.trade.pet.dto.PetAsset;
import com.xianjinxia.trade.pet.dto.PointAccountDto;
import com.xianjinxia.trade.pet.dto.QueryPlatformPetsDto;
import com.xianjinxia.trade.pet.dto.SettleReqDto;
import com.xianjinxia.trade.pet.response.AssetBaseResponse;
import com.xianjinxia.trade.shared.exception.ServiceException;
import com.xianjinxia.trade.shared.remote.BaseRemoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
public class AssetRemoteService extends BaseRemoteService {

	private static final String URL_GET_PET_INFO = "/service/r/pet/detail";

	private static final String URL_GET_ACCOUNT_INFO = "/service/r/point/detail";

	//	-------------------以上是查询--------分割线-------------以下是更新操作--------------

	private static final String URL_FROZEN_PET = "/service/pet-frozen/tx-srv";

	private static final String URL_FROZEN_COIN = "/service/point-frozen/tx-srv";

	private static final String URL_UN_FROZEN_PET = "/service/pet-unfrozen/tx-srv";

	private static final String URL_SETTLE_REQ = "/service/pet-point-settle-request/tx-srv";

	private static final String URL_PET_LIST = "/service/r/pet/list";
	
	private static final Logger logger = LoggerFactory.getLogger(AssetRemoteService.class);


	@Override
	protected String getServiceName() {
		return "ASSET-APP";
	}

	/**
	 * 获取宠物信息
	 * @param petId
	 * @return
	 */
	public PetAsset getPetInfo(Long petId) {
		 String url =super.buildUrl(URL_GET_PET_INFO) + "?petId=" + petId;
		AssetBaseResponse<PetAsset> response =
	                myRestTemplate.httpGet(url, new ParameterizedTypeReference<AssetBaseResponse<PetAsset>>() {
	                        });
        if (!AssetBaseResponse.ResponseCode.SUCCESS.getValue().equals(response.getCode())) {
        	throw new ServiceException(response.getCode(), response.getMsg());
        }
        return response.getData();
	}

	/**
	 * 获取账户信息
	 * @param userId
	 * @return
	 */
	public PointAccountDto getAccountInfo(Long userId) {
		String url =super.buildUrl(URL_GET_ACCOUNT_INFO) + "?userId=" + userId;
		AssetBaseResponse<PointAccountDto> response =
				myRestTemplate.httpGet(url, new ParameterizedTypeReference<AssetBaseResponse<PointAccountDto>>() {
				});
		if (!AssetBaseResponse.ResponseCode.SUCCESS.getValue().equals(response.getCode())) {
			throw new ServiceException(response.getCode(), response.getMsg());
		}
		return response.getData();
	}

	/**
	 * 获取宠物列表信息
	 * @param userId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List<PetAsset> getPetListInfo(Long userId, String status, int pageNum, int pageSize) {
		String url =super.buildUrl(URL_PET_LIST);
		QueryPlatformPetsDto queryPlatformPetsDto = new QueryPlatformPetsDto();
		queryPlatformPetsDto.setUserId(userId);
		queryPlatformPetsDto.setStatus(status);
		queryPlatformPetsDto.setCurrent(pageNum);
		queryPlatformPetsDto.setLimit(pageSize);

		AssetBaseResponse<List<PetAsset>> response =
				myRestTemplate.httpGet(url, queryPlatformPetsDto,new ParameterizedTypeReference<AssetBaseResponse<List<PetAsset>>>() {
				});
		if (!AssetBaseResponse.ResponseCode.SUCCESS.getValue().equals(response.getCode())) {
			throw new ServiceException(response.getCode(), response.getMsg());
		}
		return response.getData();
	}

	/**
	 * 冻结宠物
	 * @param petId
	 * @return
	 */
	public BaseAssetRespDto frozenPet(Long userId, Long petId) {
		String url = super.buildUrl(URL_FROZEN_PET);

		// 参数
		BaseAssetReqDto<FrozenPetDto> baseAssetReqDto = new BaseAssetReqDto();
		baseAssetReqDto.setTxContent(new FrozenPetDto(userId, petId));
		AssetBaseResponse<BaseAssetRespDto> response =
				myRestTemplate.httpPost(url, baseAssetReqDto,new ParameterizedTypeReference<AssetBaseResponse<BaseAssetRespDto>>() {
				});
		if (!AssetBaseResponse.ResponseCode.SUCCESS.getValue().equals(response.getCode())) {
			throw new ServiceException(response.getCode(), response.getMsg());
		}
		return response.getData();
	}

	/**
	 * 取消冻结宠物
	 * @param frozenNo
	 * @return
	 */
	public BaseAssetRespDto unFrozenPet(String frozenNo) {
		String url = super.buildUrl(URL_UN_FROZEN_PET);

		// 参数
		BaseAssetReqDto<JsonObject> baseAssetReqDto = new BaseAssetReqDto();
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("frozenNo", frozenNo);
		baseAssetReqDto.setTxContent(jsonObject);
		AssetBaseResponse<BaseAssetRespDto> response =
				myRestTemplate.httpPost(url, baseAssetReqDto,new ParameterizedTypeReference<AssetBaseResponse<BaseAssetRespDto>>() {
				});
		if (!AssetBaseResponse.ResponseCode.SUCCESS.getValue().equals(response.getCode())) {
			throw new ServiceException(response.getCode(), response.getMsg());
		}
		return response.getData();
	}

	/**
	 * 冻结积分
	 * @param coinNum
	 * @return
	 */
	public BaseAssetRespDto frozenCoin(Long userId, BigDecimal coinNum, BigDecimal expectFee) {
		String url = super.buildUrl(URL_FROZEN_COIN);

		// 参数
		BaseAssetReqDto<FrozenCoinDto> baseAssetReqDto = new BaseAssetReqDto();
		baseAssetReqDto.setTxContent(new FrozenCoinDto(userId, coinNum, expectFee));
		AssetBaseResponse<BaseAssetRespDto> response =
				myRestTemplate.httpPost(url, baseAssetReqDto,new ParameterizedTypeReference<AssetBaseResponse<BaseAssetRespDto>>() {
				});
		if (!AssetBaseResponse.ResponseCode.SUCCESS.getValue().equals(response.getCode())) {
			throw new ServiceException(response.getCode(), response.getMsg());
		}
		return response.getData();
	}

	/**
	 * 发起交割请求
	 * @param settleReqDto
	 * @return
	 */
	public BaseAssetRespDto settleReq(String settleNo, SettleReqDto settleReqDto) {
		String url = super.buildUrl(URL_SETTLE_REQ);

		// 参数
		BaseAssetReqDto<SettleReqDto> baseAssetReqDto = new BaseAssetReqDto();
		baseAssetReqDto.setTxNo(settleNo);
		baseAssetReqDto.setTxContent(settleReqDto);
		AssetBaseResponse<BaseAssetRespDto> response =
				myRestTemplate.httpPost(url, baseAssetReqDto,new ParameterizedTypeReference<AssetBaseResponse<BaseAssetRespDto>>() {
				});
		if (!AssetBaseResponse.ResponseCode.SUCCESS.getValue().equals(response.getCode())) {
			throw new ServiceException(response.getCode(), response.getMsg());
		}
		return response.getData();
	}
}
