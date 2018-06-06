package com.xianjinxia.trade.platform.plugin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;
import org.springframework.util.ObjectUtils;

import java.util.Map;

/**
 * 1、request的下划线转驼峰（转换为输入的请求参数对象）
 * 2、response的驼峰转下划线（转换为输出的响应参数对象）
 */
public class SnakeCaseConverter {

    private static final SerializeConfig serialConfig;
    private static final ParserConfig parserConfig;

    static {
        serialConfig = new SerializeConfig();
        serialConfig.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;

        parserConfig  = new ParserConfig();
        parserConfig.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
    }

    public Object covertRequest(JSONObject jsonObject, Class<?> parameterClass) {
        return JSON.parseObject(jsonObject.toJSONString(), parameterClass, parserConfig);
    }

    public Object covertRequest(Map<String,Object> requestParamMap, Class<?> parameterClass) {

        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String, Object> entry : requestParamMap.entrySet()) {
            String parameterName = entry.getKey();
            Object parameterValue = entry.getValue();
            jsonObject.put(parameterName, parameterValue);
        }
        return this.covertRequest(jsonObject, parameterClass);
    }

    public Object convertResponse(Object obj){
        if (ObjectUtils.isEmpty(obj)){
            return null;
        }
        Object rtn = JSONObject.parse(JSON.toJSONString(obj, serialConfig));
        return rtn;
    }
}
