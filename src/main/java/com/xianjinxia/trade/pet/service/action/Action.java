package com.xianjinxia.trade.pet.service.action;

/**
 * Created by Zach on 2017/6/29.
 */
public interface Action<T, T1> {
   Long create(T1 t) throws Exception;
   void execute(Long actionId);
   void execute(T action);
}
