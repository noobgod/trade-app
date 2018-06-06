package com.xianjinxia.trade.pet.request;


import com.alibaba.fastjson.JSON;
import com.xianjinxia.trade.app.response.BaseResponse;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * 请求基类
 * @author hym
 *
 */
public class PetBaseRequest {

	private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	
	/**
	 * 请求参数检查
	 * @param response
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean paramCheck(BaseResponse response){
		Set<ConstraintViolation<PetBaseRequest>> constraintViolations = validator.validate(this);
		if(!constraintViolations.isEmpty()){
			response.paramCheckFail();
			StringBuilder stringBuilder = new StringBuilder(response.getMsg());
			for(ConstraintViolation<PetBaseRequest> constraintViolation:constraintViolations){
				stringBuilder.append("|"+constraintViolation.getMessage());
			}
			response.setMsg(stringBuilder.toString());
			return false;
		}
		return true;
	}

    public boolean paramCheck(){
		Set<ConstraintViolation<PetBaseRequest>> constraintViolations = validator.validate(this);
		if(!constraintViolations.isEmpty()){
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
