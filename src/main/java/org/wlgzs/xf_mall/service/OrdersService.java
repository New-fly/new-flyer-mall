package org.wlgzs.xf_mall.service;

import com.alipay.api.AlipayApiException;
import org.springframework.data.domain.Page;
import org.wlgzs.xf_mall.entity.Orders;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Auther: 李晓珊
 * @Date: 2018/4/20 20:52
 * @Description: 订单管理
 */
public interface OrdersService {
    Page<Orders> getOrdersList(String order_number, int page, int limit);

    String edit(long orderId,HttpServletRequest request);

    List<Orders> findOrdersByOrderNumber(String order_number);

    Orders findOrdersById(long orderId);

    String delete(long orderId);

    List<Orders> findOrdersByUserName(String user_name);

    void save(HttpServletRequest request, HttpServletResponse response, String productId,String shoppingCount) throws AlipayApiException, IOException;

    List<Orders> userOrderList(long userId);

    List<Orders> userUnacceptedOrder(long userId);

    List<Orders> userUnEstimateOrder(long userId);

    void userAccepted(long orderId);

    void estimatePaySave(HttpServletRequest request, long productId);

    //void aliReturn(HttpServletResponse response, HttpServletRequest request) throws IOException, AlipayApiException;

    //后台多条件查询
    Page<Orders> adminSearchOrder(String order_word,int page,int limit);


    //前台按用户查询,多条件,分页
    List<Orders> searchOrder(String order_word,long userId);

    long searchProductCount(long productId);

}
