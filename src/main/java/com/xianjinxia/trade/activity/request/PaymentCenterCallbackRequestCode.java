package com.xianjinxia.trade.activity.request;

import java.util.HashMap;
import java.util.Map;

public class PaymentCenterCallbackRequestCode {

	public static final String					SUCCESS	= "success";					// 处理成功
	public static final String					FAILURE	= "failed";						// 处理失败

	private static final Map<String, String>	err		= new HashMap<String, String>();

	static {
		err.put("40001", "参数错误");
		err.put("40002", "用户信息为空");
		err.put("40003", "证件号码不能为空");
		err.put("40004", "银行编号不能为空");
		err.put("40005", "银行名不能为空");
		err.put("40006", "持卡人不能为空");
		err.put("40007", "证件类型不能为空");
		err.put("40008", "付款人Id不能为空");
		err.put("40009", "组合支付参数错误");
		err.put("40010", "业务id不能为空");
		err.put("40011", "业务类型不能为空");
		err.put("40012", "支付来源不能为空");
		err.put("40013", "支付方式不能为空");
		err.put("40014", "代扣金额不能为空");
		err.put("40015", "代扣金额不能太大");
		err.put("40016", "相同业务id和业务类型重复请求");
		err.put("40017", "电话号码格式不对");
		err.put("40018", "银行卡不能为空");
		err.put("40019", "银行卡格式不正确");
		err.put("40020", "路由策略不能为空");
	}

	public static String getErrorMessage(String code) {
		String r = err.get(code);
		return r;
	}
}
