package com.xianjinxia.trade.shopping.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xianjinxia.trade.shared.domain.TrdShoppingLoanCardInfo;
import com.xianjinxia.trade.shared.mapper.TrdShoppingLoanCardInfoMapper;
import com.xianjinxia.trade.shared.utils.JsonUtils;
import com.xianjinxia.trade.shopping.response.bgd.CardInfoDto;
import com.xianjinxia.trade.shopping.response.soouu.SoouuCard;
import com.xianjinxia.trade.shopping.response.soouu.SoouuSuccessResponse;
import com.xianjinxia.trade.shopping.service.ITrdShoppingLoanCardInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ITrdShoppingLoanCardInfoServiceImpl implements ITrdShoppingLoanCardInfoService {
    @Autowired
    private TrdShoppingLoanCardInfoMapper trdShoppingLoanCardInfoMapper;

    @Override
    @Transactional
    public void insert(Long shoppingProductOrderId, String productName, Integer productUnitPrice, SoouuSuccessResponse successResponse) {
        List<SoouuCard> cards = successResponse.getCards();
        if (cards != null && cards.size() > 0) {
            for (SoouuCard card : cards) {
                TrdShoppingLoanCardInfo cardInfo = new TrdShoppingLoanCardInfo();
                cardInfo.setShoppingProductOrderId(shoppingProductOrderId);
                cardInfo.setThirdOrderId(successResponse.getOrderId());
                cardInfo.setCardNo(card.getCardNumber());
                cardInfo.setCardPassword(card.getCardPwd());
                cardInfo.setCardDeadline(card.getCardDeadline());
                cardInfo.setProductUnitPrice(productUnitPrice);
				cardInfo.setProductName(productName);
				cardInfo.setRecycle("0");
				cardInfo.setCreatedUser("sys");
				cardInfo.setUpdatedUser("sys");
                trdShoppingLoanCardInfoMapper.insert(cardInfo);
            }

        }

    }

    @Override
    public List<CardInfoDto> getByOrderId(Long shoppingProductOrderId) {
        List<TrdShoppingLoanCardInfo> cards = trdShoppingLoanCardInfoMapper.selectByOrderId(shoppingProductOrderId);
        if (cards != null && cards.size() > 0) {
            List<CardInfoDto> result = new ArrayList<>();
            for (TrdShoppingLoanCardInfo cardInfo : cards) {
                CardInfoDto cardInfoDto = new CardInfoDto();
                int index = cards.indexOf(cardInfo) + 1;
                String key1;
                String key2;
                if (cards.size() > 1) {
                    key1 = "卡号" + index;
                    key2 = "密码" + index;
                } else {
                    key1 = "卡号";
                    key2 = "密码";
                }

                cardInfoDto.setCardKey(key1);
                cardInfoDto.setCardNo(cardInfo.getCardNo());
                cardInfoDto.setPasswordKey(key2);
                cardInfoDto.setPassword(cardInfo.getCardPassword());
                result.add(cardInfoDto);
            }
            return result;
        }

        return null;
    }

	@Override
	public String selectByNoAndPassword(String data) {
		JSONArray resultList=new JSONArray();
		JSONObject jsonObj=JsonUtils.fromJson(data);
		JSONArray jsonArr=(JSONArray) jsonObj.get("cards");
		for (Object object : jsonArr) {
			JSONObject cardObj=(JSONObject) object;
			TrdShoppingLoanCardInfo cardInfo=trdShoppingLoanCardInfoMapper.selectByNoAndPassword(cardObj.getString("card"), cardObj.getString("pwd"));
			if(cardInfo!=null){
				cardObj.put("value", cardInfo.getProductUnitPrice()/100);
				cardObj.put("type", getSouKaType(cardInfo.getProductName()));
			}else {
				cardObj.put("value", null);
				cardObj.put("type", null);
			}
			resultList.add(cardObj);
		}
		return JSONArray.toJSONString(resultList);
	}
	private static Integer getSouKaType(String productName){
		Integer type=null;
		if(productName.indexOf("移动")>=0){
			type=1;
		}else if(productName.indexOf("联通")>=0) {
			type=2;
		}else if(productName.indexOf("电信")>=0) {
			type=3;
		}else if(productName.indexOf("中国石化")>=0) {
			type=4;
		}else if(productName.indexOf("京东")>=0) {
			type=5;
		}else if(productName.indexOf("完美")>=0) {
			type=7;
		}else if(productName.indexOf("久游")>=0) {
			type=8;
		}else if(productName.indexOf("骏网")>=0) {
			type=9;
		}else if(productName.indexOf("盛付")>=0) {
			type=10;
		}else if(productName.indexOf("网易")>=0) {
			type=11;
		}else if(productName.indexOf("巨人")>=0) {
			type=12;
		}else if(productName.indexOf("苹果")>=0) {
			type=13;
		}else if(productName.indexOf("携程")>=0) {
			type=14;
		}else if(productName.indexOf("腾讯QB")>=0) {
			type=15;
		}
		return type;
	}
}
