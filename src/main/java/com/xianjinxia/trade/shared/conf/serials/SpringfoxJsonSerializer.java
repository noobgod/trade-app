package com.xianjinxia.trade.shared.conf.serials;

import java.lang.reflect.Type;

import org.springframework.stereotype.Component;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSerializationContext;

import springfox.documentation.spring.web.json.Json;

@Component
public class SpringfoxJsonSerializer extends BaseSerial<Json>{

	@Override
    public JsonElement serialize(Json json, Type type, JsonSerializationContext context) {
        final JsonParser parser = new JsonParser();
        return parser.parse(json.value());
    }

	@Override
	public Json deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		// TODO Auto-generated method stub
		return null;
	}

}
