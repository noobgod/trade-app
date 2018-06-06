package com.xianjinxia.trade.shared.utils;

import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xianjinxia.trade.shared.conf.serials.DateSerializer;

public class GsonUtil {
	
	private static Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateSerializer()).create();
			
	
	public static String toGson(Object object) {
		return gson.toJson(object);
	}
	
}

