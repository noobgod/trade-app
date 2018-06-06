package com.xianjinxia.trade.shared.conf.serials;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;

/**
 * 日期序列化反序列化方式
 *
 * @author hym
 * 2017年9月14日
 */
@Component
public class DateSerializer extends BaseSerial<Date> {

    private static final Pattern PATTERN_DATE = Pattern.compile("^\\d{1,4}-\\d{2}-\\d{2}$");
    private static final Pattern PATTERN_DATETIME = Pattern.compile("^\\d{1,4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}$");

    @Override
    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.getTime());
    }

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        String jsonStr = json.getAsString();
        if (PATTERN_DATE.matcher(jsonStr).matches()) {
            return getDate(jsonStr);
        } else if (PATTERN_DATETIME.matcher(jsonStr).matches()) {
            return getDatetime(jsonStr);
        } else {
            return new Date(json.getAsLong());
        }
    }

    private static Date getDate(String jsonStr) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(jsonStr);
        } catch (ParseException e) {
            throw new JsonParseException("can't parse field to date");
        }
    }

    private static Date getDatetime(String jsonStr) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(jsonStr);
        } catch (ParseException e) {
            throw new JsonParseException("can't parse field to date");
        }
    }

}