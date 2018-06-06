package com.xianjinxia.trade.shopping.service;

import java.util.List;

import com.xianjinxia.trade.shared.domain.TrdShoppingUserAddr;
import com.xianjinxia.trade.shopping.dto.UserReceiveAddressDto;

public interface IShoppingUserAddrService {
    void updateToDefault(Long userId, Long id);
    void updateById(TrdShoppingUserAddr trdShoppingUserAddr);
    Long add(TrdShoppingUserAddr trdShoppingUserAddr);
    void del(Long id);
    UserReceiveAddressDto getById(Long id);
    List<UserReceiveAddressDto> getByUserId(Long userId);
}
