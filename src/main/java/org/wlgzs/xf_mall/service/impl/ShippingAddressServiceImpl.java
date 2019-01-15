package org.wlgzs.xf_mall.service.impl;

import org.springframework.stereotype.Service;
import org.wlgzs.xf_mall.dao.ShippingAddressRepository;
import org.wlgzs.xf_mall.entity.ShippingAddress;
import org.wlgzs.xf_mall.service.ShippingAddressService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author:胡亚星
 * @createTime 2018-04-27 20:28
 * @description:
 **/
@Service
public class ShippingAddressServiceImpl implements ShippingAddressService {

    @Resource
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
    public boolean findState(String user_name) {
        ShippingAddress shippingAddress = shippingAddressRepository.findState(user_name);
        return shippingAddress != null;
    }

    @Override
    public void setDefault(long addressId, String user_name) {
        //查询该地址是否为默认
        int a = shippingAddressRepository.State(addressId);
        if(a == 0){//不为默认
            //修改1为0，修改0为1
            shippingAddressRepository.modifyState(0,1);
            shippingAddressRepository.modifyDefault(addressId);
        }
    }
}
