package com.xianjinxia.trade.shared.idempotent;

import com.xianjinxia.trade.shared.domain.EventIdempotent;
import com.xianjinxia.trade.shared.enums.IdempotentTypeEnum;
import com.xianjinxia.trade.shared.exception.IdempotentException;
import com.xianjinxia.trade.shared.exception.ServiceException;
import com.xianjinxia.trade.shared.mapper.EventIdempotentMapper;
import com.xianjinxia.trade.shared.utils.Md5Util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.TreeMap;

/**
 * 幂等service
 * 
 * @author fanmaowen 2017年11月27日
 */
@Service
public class IdempotentService {

    private static final Logger logger = LoggerFactory.getLogger(IdempotentService.class);

    @Autowired
    private EventIdempotentMapper eventIdempotentMapper;

    /**
     * 幂等check,通过类上加@idempotentKey方式调用
     * 
     * @param idempotentType
     * @param keyObj 包含幂等key对象
     * @throws IdempotentException
     */
    public <T> void idempotentCheck(IdempotentTypeEnum idempotentType, T keyObj)
            throws IdempotentException {
        EventIdempotent idempotentEvent = new EventIdempotent();
        getIdempotentKeys(keyObj, idempotentEvent);
        if (StringUtils.isBlank(idempotentEvent.getSourceId())) {
            throw new ServiceException("fail to get idempotentkey");
        }
        idempotentEvent.setSourceType(idempotentType.name());
        try {
            eventIdempotentMapper.saveEventIdempotent(idempotentEvent);
        } catch (DuplicateKeyException e) {
            logger.error("idempotent check fail", e);
            throw new IdempotentException(idempotentEvent);
        }
    }

    /**
     * 幂等check，直接将幂等key元数据传入方式调用
     * 
     * @param idempotentType
     * @param keys 幂等key元数据
     * @throws IdempotentException
     */
    public void idempotentCheck(IdempotentTypeEnum idempotentType, Object... keys)
            throws IdempotentException {
        EventIdempotent idempotentEvent = new EventIdempotent();
        generateIdempotentKey(idempotentEvent, keys);
        if (StringUtils.isBlank(idempotentEvent.getSourceId())) {
            throw new ServiceException("fail to get idempotentkey");
        }
        idempotentEvent.setSourceType(idempotentType.name());
        try {
            eventIdempotentMapper.saveEventIdempotent(idempotentEvent);
        } catch (DuplicateKeyException e) {
            logger.error("", e);
            throw new IdempotentException(idempotentEvent);
        }
    }

    /**
     * 获取幂等key元数据
     * 
     * @param idempotentEvent
     * @param keySource
     */
    private void getIdempotentKeys(Object keySource, EventIdempotent idempotentEvent) {
        TreeMap<Integer, Object> keyMap = new TreeMap<Integer, Object>();
        for (Field field : keySource.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(IdempotentKey.class)) {
                try {
                    field.setAccessible(true);
                    keyMap.put(field.getAnnotation(IdempotentKey.class).order(),
                            field.get(keySource));
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    logger.error("", e);
                    return;
                }
            }
        }
        generateIdempotentKey(idempotentEvent, keyMap.values().toArray());
    }

    /**
     * 生成幂等key
     * 
     * @param idempotentEvent
     * @param keyObj
     */
    private void generateIdempotentKey(EventIdempotent idempotentEvent, Object... keyObj) {
        if (keyObj.length == 0) {
            logger.info("idempotentkey is empty,{}", keyObj);
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (Object key : keyObj) {
            sb.append(key.toString()).append("|");
        }
        idempotentEvent.setRemark(sb.toString());
        idempotentEvent.setSourceId(Md5Util.md5(sb.toString()));
    }
}
