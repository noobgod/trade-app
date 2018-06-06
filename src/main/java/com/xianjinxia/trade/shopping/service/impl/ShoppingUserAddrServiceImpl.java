package com.xianjinxia.trade.shopping.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xianjinxia.trade.shared.domain.TrdShoppingUserAddr;
import com.xianjinxia.trade.shared.mapper.TrdShoppingUserAddrMapper;
import com.xianjinxia.trade.shopping.dto.UserReceiveAddressDto;
import com.xianjinxia.trade.shopping.service.IShoppingUserAddrService;

@Service
public class ShoppingUserAddrServiceImpl implements IShoppingUserAddrService {

    @Autowired
    private TrdShoppingUserAddrMapper trdShoppingUserAddrMapper;

    @Override
    @Transactional
    public void updateToDefault(Long userId, Long id) {
        List<TrdShoppingUserAddr> trdShoppingUserAddrs = trdShoppingUserAddrMapper.selectByUserId(userId);
        for (Iterator<TrdShoppingUserAddr> iterator = trdShoppingUserAddrs.iterator(); iterator.hasNext(); ) {
            TrdShoppingUserAddr trdShoppingUserAddr = iterator.next();
            if (trdShoppingUserAddr.getId().longValue() == id.longValue()){
                trdShoppingUserAddr.setIsDefault(true);
            }else{
                trdShoppingUserAddr.setIsDefault(false);
            }
            trdShoppingUserAddrMapper.updateByPrimaryKeySelective(trdShoppingUserAddr);
        }
    }

    @Override
    @Transactional
    public void updateById(TrdShoppingUserAddr trdShoppingUserAddr) {
        trdShoppingUserAddrMapper.updateByPrimaryKeySelective(trdShoppingUserAddr);
    }

    @Override
    @Transactional
    public Long add(TrdShoppingUserAddr trdShoppingUserAddr) {
    	List<TrdShoppingUserAddr> trdShoppingUserAddrs =  trdShoppingUserAddrMapper.selectByUserId(trdShoppingUserAddr.getUserId());
    	if(CollectionUtils.isEmpty(trdShoppingUserAddrs)){
    		trdShoppingUserAddr.setIsDefault(Boolean.TRUE);
    	}
        trdShoppingUserAddrMapper.insert(trdShoppingUserAddr);
        Long id = trdShoppingUserAddr.getId();
        return id;
    }

    @Override
    @Transactional
    public void del(Long id) {
        TrdShoppingUserAddr trdShoppingUserAddr = new TrdShoppingUserAddr();
        trdShoppingUserAddr.setId(id);
        trdShoppingUserAddr.setDataValid(false);
        trdShoppingUserAddrMapper.updateByPrimaryKeySelective(trdShoppingUserAddr);
    }

    @Override
    public UserReceiveAddressDto getById(Long id) {
    	TrdShoppingUserAddr trdShoppingUserAddr =  trdShoppingUserAddrMapper.selectByPrimaryKey(id);
    	UserReceiveAddressDto userReceiveAddressDto = new UserReceiveAddressDto();
    	BeanUtils.copyProperties(trdShoppingUserAddr, userReceiveAddressDto);
		userReceiveAddressDto.setId(trdShoppingUserAddr.getId());
		return userReceiveAddressDto;
    }

    @Override
    public List<UserReceiveAddressDto> getByUserId(Long userId) {
    	List<TrdShoppingUserAddr> trdShoppingUserAddrs =  trdShoppingUserAddrMapper.selectByUserId(userId);
    	if(CollectionUtils.isEmpty(trdShoppingUserAddrs)){
    		return new ArrayList<>();
    	}
    	List<UserReceiveAddressDto> list = new ArrayList<UserReceiveAddressDto>();
    	for (TrdShoppingUserAddr trdShoppingUserAddr : trdShoppingUserAddrs) {
    		UserReceiveAddressDto userReceiveAddressDto = new UserReceiveAddressDto();
    		BeanUtils.copyProperties(trdShoppingUserAddr, userReceiveAddressDto);
    		userReceiveAddressDto.setId(trdShoppingUserAddr.getId());
    		list.add(userReceiveAddressDto);
		}
    	return list;
    }
}
