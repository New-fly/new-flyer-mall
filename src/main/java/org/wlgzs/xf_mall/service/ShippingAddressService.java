package org.wlgzs.xf_mall.service;

import org.wlgzs.xf_mall.entity.ShippingAddress;

import java.util.List;

/**
 * @author:胡亚星
 * @createTime 2018-04-27 20:27
 * @description:
 **/
public interface ShippingAddressService {
    //添加新的收货地址
    void addShippingAddress(ShippingAddress shippingAddress);
    //遍历收货地址
    List<ShippingAddress> getShippingAddressList(String user_name);
    //按id查询
    ShippingAddress findById(long userId);
    //修改收货地址
    void ModifyAddress(ShippingAddress shippingAddress);
    //按id删除
    void deleceAddress(long userId);
    //将当前默认状态为是的改为否
    void modifyState(int stateOne,int stateTwo);
    //修改用户名
    void modifyName(String user_name1,String user_name2);
    //查询数据是否有默认的
    boolean findState(String user_name);
    //将收货地址设为默认
    void setDefault(long addressId,String user_name);
}
