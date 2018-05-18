package org.wlgzs.xf_mall.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.wlgzs.xf_mall.base.BaseController;
import org.wlgzs.xf_mall.entity.Orders;
import org.wlgzs.xf_mall.service.OrdersService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author:胡亚星
 * @createTime 2018-05-12 19:35
 * @description:
 **/
@RestController
@RequestMapping("UserOrderController")
public class UserOrderController extends BaseController {

    //订单详情   前后台
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
    public ModelAndView userAccepted(long orderId){
            ordersService.userAccepted(orderId);
            return new ModelAndView();
    }

}
