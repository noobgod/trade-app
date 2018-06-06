package com.xianjinxia.trade.shared.conf;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.stereotype.Component;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

/**
 * httpmessage转换类，用于后续自定义转换方式
 * 
 * @author hym 2017年9月14日
 */
@Component
public class MyGsonHttpMessageConverter extends GsonHttpMessageConverter {

    private Logger logger = LoggerFactory.getLogger(MyGsonHttpMessageConverter.class);

    private GsonBuilder gsonBuilder = new GsonBuilder();

    private String jsonPrefix;

    @PostConstruct
    public void init() {
        setGson(gsonBuilder.create());
    }

    public void registerTypeSerialOrDeseril(Type type, Object serilOrDeseril) {
        gsonBuilder.registerTypeAdapter(type, serilOrDeseril);
        init();
    }

    @Override
    protected void writeInternal(Object o, Type type, HttpOutputMessage outputMessage)
            throws IOException {
        Charset charset = getCharset(outputMessage.getHeaders());
        OutputStreamWriter writer = new OutputStreamWriter(outputMessage.getBody(), charset);
        try {
            String responseMessage = null;
            if (this.jsonPrefix != null) {
                writer.append(this.jsonPrefix);
            }
            if (type != null) {
                responseMessage = getGson().toJson(o, type);
            } else {
                responseMessage = getGson().toJson(o);
            }
            writer.append(responseMessage);
            logger.info("output the response message is {}", responseMessage);
        } catch (JsonIOException ex) {
            logger.error("Could not write JSON: ", ex);
            throw new HttpMessageNotWritableException("Could not write JSON: " + ex.getMessage(),
                    ex);
        }finally{
            writer.close();
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage)
            throws IOException {

        TypeToken<?> token = getTypeToken(type);
        return readTypeToken(token, inputMessage);
    }

    @Override
    @SuppressWarnings("deprecation")
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage)
            throws IOException {

        TypeToken<?> token = getTypeToken(clazz);
        return readTypeToken(token, inputMessage);
    }

    private Charset getCharset(HttpHeaders headers) {
        if (headers == null || headers.getContentType() == null
                || headers.getContentType().getCharset() == null) {
            return DEFAULT_CHARSET;
        }
        return headers.getContentType().getCharset();
    }

    private Object readTypeToken(TypeToken<?> token, HttpInputMessage inputMessage)
            throws IOException {
        Reader json = new InputStreamReader(inputMessage.getBody(),
                getCharset(inputMessage.getHeaders()));
        try {
            String requestMessage = StringUtils.join(IOUtils.readLines(json).toArray());
            logger.info("received the request message is{} ", requestMessage);
            return getGson().fromJson(requestMessage, token.getType());
        } catch (JsonParseException ex) {
            logger.error("JSON parse error:", ex);
            throw new HttpMessageNotReadableException("JSON parse error: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void setJsonPrefix(String jsonPrefix) {
        this.jsonPrefix = jsonPrefix;
    }

    @Override
    public void setPrefixJson(boolean prefixJson) {
        this.jsonPrefix = (prefixJson ? ")]}', " : null);
    }
}
