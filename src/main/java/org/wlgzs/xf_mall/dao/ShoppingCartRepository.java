package org.wlgzs.xf_mall.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.wlgzs.xf_mall.entity.ShoppingCart;

import java.util.List;

/**
 * @Auther: 阿杰
 * @Date: 2018/4/25 10:53
 * @Description:
 */
public interface ShoppingCartRepository  extends JpaRepository<ShoppingCart, Long>,JpaSpecificationExecutor<ShoppingCart> {
    ShoppingCart findById(long shoppingCartId);

    @Query(value = "SELECT o FROM ShoppingCart o WHERE o.userId=?1")
    List<ShoppingCart> findByUserIdCart(long userId);

    @Query(value = "SELECT o FROM ShoppingCart o WHERE o.userId=?1 AND o.productId=?2")
    ShoppingCart findByUserIdAndProductId(long userId,long productId);

    @Query("SELECT o FROM ShoppingCart o WHERE o.shoppingCartId in :Ids")
    List<ShoppingCart> findAllByIds(@Param(value = "Ids") long [] Ids);

    @Transactional
    @Modifying
    @Query("DELETE FROM ShoppingCart o WHERE o.shoppingCartId in :Ids")
    void deleteByIds(@Param(value = "Ids") long [] Ids);

}
