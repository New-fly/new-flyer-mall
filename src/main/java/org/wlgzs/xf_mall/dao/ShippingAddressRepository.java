package org.wlgzs.xf_mall.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.wlgzs.xf_mall.entity.ShippingAddress;

import java.util.List;

/**
 * @Auther: 阿杰
 * @Date: 2018/4/25 17:24
 * @Description:
 */
public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Long>,JpaSpecificationExecutor<ShippingAddress> {
    ShippingAddress findById(long id);

    //修改默认状态
    @Query("UPDATE ShippingAddress a SET a.address_is_default=?1 WHERE a.address_is_default=?2")
    @Modifying
    @Transactional
    void modifyState(int stateOne,int stateTwo);

    //查询某用户的所有收获地址
    @Query("SELECT s FROM ShippingAddress s WHERE s.user_name=?1")
    List<ShippingAddress> findAllShippingAddress(String user_name);

    //修改用户名.
    @Query("UPDATE ShippingAddress a SET a.user_name=?1 WHERE a.user_name=?2")
    @Modifying
    @Transactional
    void modifyName(String user_name1,String user_name2);

    //查询数据是否有默认的
    @Query("SELECT s FROM ShippingAddress s WHERE s.address_is_default=1")
    ShippingAddress findState();

}
