package com.xianjinxia.trade.shared.mapper;

import com.xianjinxia.trade.shared.domain.TrdShoppingUserAddr;

import java.util.List;

public interface TrdShoppingUserAddrMapper {

    int insert(TrdShoppingUserAddr record);

    TrdShoppingUserAddr selectByPrimaryKey(Long id);

    List<TrdShoppingUserAddr> selectByUserId(Long userId);

    int updateByPrimaryKeySelective(TrdShoppingUserAddr record);

}