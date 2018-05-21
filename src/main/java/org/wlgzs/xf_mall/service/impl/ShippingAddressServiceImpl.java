package org.wlgzs.xf_mall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.wlgzs.xf_mall.dao.ShippingAddressRepository;
import org.wlgzs.xf_mall.entity.ShippingAddress;
import org.wlgzs.xf_mall.service.ShippingAddressService;

import java.util.List;

/**
 * @author:胡亚星
 * @createTime 2018-04-27 20:28
 * @description:
 **/
@Service
public class ShippingAddressServiceImpl implements ShippingAddressService {

    @Autowired
    ShippingAddressRepository shippingAddressRepository;

    @Override
    public void addShippingAddress(ShippingAddress shippingAddress) {
        shippingAddressRepository.save(shippingAddress);
    }

    @Override
    public List<ShippingAddress> getShippingAddressList(String user_name) {
        return shippingAddressRepository.findAllShippingAddress(user_name);
    }

    @Override
    public ShippingAddress findById(long userId) {
        return shippingAddressRepository.findById(userId);
    }

    @Override
    public void ModifyAddress(ShippingAddress shippingAddress) {
        shippingAddressRepository.saveAndFlush(shippingAddress);
    }

    @Override
    public void deleceAddress(long userId) {
        shippingAddressRepository.deleteById(userId);
    }

    @Override
    public void modifyState(int stateOne, int stateTwo){
        shippingAddressRepository.modifyState(stateOne,stateTwo);
    }

    @Override
    public void modifyName(String user_name1,String user_name2) {
        shippingAddressRepository.modifyName(user_name1,user_name2);
    }

    @Override
    public boolean findState() {
        ShippingAddress shippingAddress = shippingAddressRepository.findState();
        if(shippingAddress != null){
            return true;
        }
        return false;
    }
}
