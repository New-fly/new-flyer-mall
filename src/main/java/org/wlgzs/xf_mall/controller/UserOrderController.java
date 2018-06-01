package org.wlgzs.xf_mall.controller;

import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.wlgzs.xf_mall.base.BaseController;
import org.wlgzs.xf_mall.entity.Orders;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author:胡亚星
 * @createTime 2018-05-12 19:35
 * @description:
 **/
@RestController
@RequestMapping("UserOrderController")
public class UserOrderController extends BaseController {

    //订单详情
    @RequestMapping("/orderDetails")
    public ModelAndView orderInfo(Model model, Long id) {
        Orders order=ordersService.findOrdersById(id);
        model.addAttribute("order", order);
        return new ModelAndView("admin/adminOrderInfo");
    }
    /**
     * @author 阿杰
     * @param [model, userId]
     * @return org.springframework.web.servlet.ModelAndView
     * @description 用户订单 全部  未收货 未评价
     */
    @RequestMapping("/userOrderList")
    public ModelAndView userOrderList(Model model,long userId){
        List<Orders> orders = ordersService.userOrderList(userId);

        String [] orderTwo = new String[100];
        for (int i = 0; i < orderTwo.length; i++) {
            orderTwo[i] = "批量购买"+i;
        }
        Map<String, List> map = new HashMap<String, List>();
        for (int i = 0; i < orders.size(); i++) {
            for (Orders order : orders) {
                if (orders.get(i).getOrder_number() == order.getOrder_number()) {
                    List<Orders> ordersList = new ArrayList<>();
                    ordersList.add(orders.get(i));
                    ordersList.add(order);
                    map.put(orderTwo[i], ordersList);
                }
            }
        }
        map.get(orderTwo[0]);

        model.addAttribute("orders",orders);
        List<Orders> unacceptedOrder = ordersService.userUnacceptedOrder(userId);
        model.addAttribute("unacceptedOrder",unacceptedOrder);
        List<Orders> unEstimateOrder = ordersService.userUnEstimateOrder(userId);
        model.addAttribute("unEstimateOrder",unEstimateOrder);
        return new ModelAndView("userOrder");
    }
    /**
     * @author 阿杰
     * @param [orderId]
     * @return org.springframework.web.servlet.ModelAndView
     * @description 收货
     */
    @RequestMapping("/userAccepted")
    public ModelAndView userAccepted(long orderId, HttpServletRequest request){
        HttpSession session = request.getSession();
        long userId = 0;
        if(session.getAttribute("user")!=null){
            userId = (long) session.getAttribute("userId");
        }
        ordersService.userAccepted(orderId);
        String url="redirect:/UserOrderController/userOrderList?userId="+userId;
        return new ModelAndView(url);
    }
    
    /**     
     * @author 胡亚星
     * @date 2018/5/17 11:00  
     * @param   
     * @return   
     *@Description:订单前台搜索（分页）
     */  
    @RequestMapping("searchOrder")
    public ModelAndView searchOrder(Model model, String order_word, @RequestParam(value = "page",defaultValue = "0")int page,
                                    @RequestParam(value = "limit",defaultValue = "6")int limit,long userId){
        if(page != 0) page--;
        Page<Orders> pages = ordersService.searchOrder(order_word,page,limit,userId);
        model.addAttribute("TotalPages", pages.getTotalPages());//查询的页数
        model.addAttribute("Number", pages.getNumber()+1);//查询的当前第几页
        List<Orders> orders = pages.getContent();
        model.addAttribute("orders",orders);
        return new ModelAndView("userOrder");
    }

}
