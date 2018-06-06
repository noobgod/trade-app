package com.xianjinxia.trade.app.request;


import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.xianjinxia.trade.app.response.BaseResponse;

import java.util.Set;

/**
 * 请求基类
 * @author hym
 *
 */
public class BaseRequest {

	private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	
	/**
	 * 请求参数检查
	 * @param response
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean paramCheck(BaseResponse response){
		Set<ConstraintViolation<BaseRequest>> constraintViolations = validator.validate(this);
		if(!constraintViolations.isEmpty()){
			response.paramCheckFail();
			StringBuilder stringBuilder = new StringBuilder(response.getMsg());
			for(ConstraintViolation<BaseRequest> constraintViolation:constraintViolations){
				stringBuilder.append("|"+constraintViolation.getMessage());
			}
			response.setMsg(stringBuilder.toString());
			return false;
		}
		return true;
	}

    public boolean paramCheck(){
		Set<ConstraintViolation<BaseRequest>> constraintViolations = validator.validate(this);
		if(!constraintViolations.isEmpty()){
			return false;
		}
		return true;
	}

}
