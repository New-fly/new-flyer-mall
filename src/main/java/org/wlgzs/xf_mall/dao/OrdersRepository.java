package org.wlgzs.xf_mall.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.wlgzs.xf_mall.entity.Orders;

import java.util.List;


/**
 * @Auther: 李晓珊
 * @Date: 2018/4/20 20:04
 * @Description:订单类接口
 */
public interface OrdersRepository extends JpaRepository<Orders, Long>,JpaSpecificationExecutor<Orders> {

    //通过id获得订单信息
    Orders findById(long id);

 /*  @Query(value = "SELECT * FROM orders WHERE product_keywords like %:product_keywords%", nativeQuery = true)
    List<Orders> findByProductKeywords(@Param("product_keywords") String product_keywords);

    @Query(value = "SELECT * FROM orders WHERE user_name like %:user_name%", nativeQuery = true)
    List<Orders> findByUserName(@Param("user_name") String user_name);
*/

    // 通过订单号查找订单
    @Query(value = "SELECT * FROM orders WHERE order_number like %:order_number%", nativeQuery = true)
    List<Orders> findByOrderNumber(@Param("order_number") String order_number);

    //通过用户名查询订单
    @Query(value = "SELECT * FROM orders WHERE user_name = ?",nativeQuery = true)
    List<Orders> findByUserName(@Param("user_name") String user_name);

    //用户遍历订单
    @Query(value = "SELECT * FROM orders WHERE user_id = ?",nativeQuery = true)
    List<Orders> userOrderList(@Param("userId") long userId);

    @Query(value = "SELECT * FROM orders WHERE user_id = ? and order_status = '未收货'",nativeQuery = true)
    List<Orders> userUnacceptedOrder(long userId);

    @Query(value = "SELECT * FROM orders WHERE user_id = ? and order_status = '未评价'",nativeQuery = true)
    List<Orders> unEstimateOrder(long userId);
}
