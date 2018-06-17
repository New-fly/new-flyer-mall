package org.wlgzs.xf_mall.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.wlgzs.xf_mall.entity.Orders;

import java.util.List;


/**
 * @Auther: 李晓珊
 * @Date: 2018/4/20 20:04
 * @Description:订单类接口
 */
public interface OrdersRepository extends JpaRepository<Orders, Long>,JpaSpecificationExecutor<Orders> {

    //通过id获得订单信息
    Orders findById(long orderId);

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
    @Query(value = "SELECT * FROM orders WHERE user_id = ? order by order_id desc",nativeQuery = true)
    List<Orders> userOrderList(@Param("userId") long userId);

    @Query(value = "SELECT * FROM orders WHERE user_id = ? and order_status = '待收货'",nativeQuery = true)
    List<Orders> userUnacceptedOrder(long userId);

    @Query(value = "SELECT * FROM orders WHERE user_id = ? and order_status = '待评价'",nativeQuery = true)
    List<Orders> unEstimateOrder(long userId);

    @Query(value = "select count(*) from orders where product_id=?",nativeQuery = true)
    long count(@Param("productId") long productId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Orders o WHERE o.orderId in :Ids")
    void deleteByIds(@Param(value = "Ids") long [] Ids);

    @Query(value = "SELECT * FROM orders WHERE order_number = ?",nativeQuery = true)
    List<Orders> findByNumber(@Param("order_number") String order_number);

    @Query(value = "select distinct order_number from orders where user_id=?1 order by order_id desc",nativeQuery = true)
    List<String> findOrderNumbers(@Param("userId") long userId);

    @Query(value = "select * from orders where user_id=?1 order by order_id desc",nativeQuery = true)
    List<Orders> findOrders(@Param("userId") long userId);

    @Query(value = "select distinct product_paid_price from orders where user_id=?",nativeQuery = true)
    List<String> findOrderPay(@Param("userId") long userId);
}
