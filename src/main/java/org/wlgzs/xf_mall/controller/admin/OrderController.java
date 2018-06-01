package org.wlgzs.xf_mall.controller.admin;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.wlgzs.xf_mall.entity.Orders;
import org.wlgzs.xf_mall.service.OrdersService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Auther: 李晓珊
 * @Date: 2018/4/20 20:46
 * @Description:
 */
@Controller
@RequestMapping("OrderController")
public class OrderController {
    @Resource
    OrdersService ordersService;

    //后台订单列表
    @RequestMapping("/allProductOrdersLists")
    public String list(Model model,@RequestParam(value = "page", defaultValue = "0") int page,
    @RequestParam(value = "limit",defaultValue = "10") int limit) {
        String order_number ="";
        if(page != 0) page--;
        Page pages =  ordersService.getOrdersList(order_number,page,limit);
        model.addAttribute("TotalPages", pages.getTotalPages());//查询的页数
        model.addAttribute("Number", pages.getNumber()+1);//查询的当前第几页
        model.addAttribute("orders", pages.getContent());
        System.out.println(pages.getContent());
        return "admin/adminOrdersList";
    }
    //后台订单列表  搜索商品
    @RequestMapping("/userFindOrder")
    public String userFindOrders(Model model,String order_number,@RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "limit",defaultValue = "10") int limit) {
        if(page != 0) page--;
        Page pages =  ordersService.getOrdersList(order_number,page,limit);
        model.addAttribute("TotalPages", pages.getTotalPages());//查询的页数
        model.addAttribute("Number", pages.getNumber()+1);//查询的当前第几页
        model.addAttribute("order_number",order_number);
        model.addAttribute("orders", pages.getContent());
        return "admin/adminOrdersList";
    }
    //跳转至修改订单页面
    @RequestMapping("/toChangeProductOrders")
    public ModelAndView toEdit(Model model, Long id) {
        Orders order=ordersService.findOrdersById(id);
        if(order != null){
            model.addAttribute("order", order);
            return new ModelAndView("admin/adminEditOrders");
        }else{
            model.addAttribute("mag","该数据不存在");
            return new ModelAndView("redirect:/OrderController/allProductOrdersLists");
        }
    }
    //后台通过订单号查询订单
    @RequestMapping("/findOrder")
    public ModelAndView findOrders(Model model,String order_number){
        List<Orders> orders = ordersService.findOrdersByOrderNumber(order_number);
        model.addAttribute("orders",orders);
        model.addAttribute("order_number",order_number);
        return new ModelAndView("admin/adminOrdersList");
    }
    //后台修改订单信息
    @RequestMapping("/changeProductOrder")
    public ModelAndView edit(Model model,long orderId, HttpServletRequest request) {
        String mag = ordersService.edit(orderId, request);
        model.addAttribute("mag",mag);
        return new ModelAndView("redirect:/OrderController/allProductOrdersLists");
    }
    //后台删除订单
    @RequestMapping("/deleteOrder")
    public ModelAndView delete(Model model,long orderId){
        String mag = ordersService.delete(orderId);
        model.addAttribute("mag",mag);
        return new ModelAndView("redirect:/OrderController/allProductOrdersLists");
    }
    //后台批量删除订单
    @RequestMapping("/deleteOrders")
    public ModelAndView deleteOrders(Model model,String orderId){
        ordersService.deleteOrders(orderId);
        return new ModelAndView("redirect:/OrderController/allProductOrdersLists");
    }

    //订单详情
    @RequestMapping("/orderDetails")
    public ModelAndView orderInfo(Model model, Long id) {
        Orders order=ordersService.findOrdersById(id);
        if(order != null){
            model.addAttribute("order", order);
            return new ModelAndView("admin/adminOrderInfo");
        }else{
            model.addAttribute("mag","该订单不存在");
            return new ModelAndView("redirect:/OrderController/allProductOrdersLists");
        }
    }

    //后台多条件查询订单 分页
    @RequestMapping("/findOrders")
    public ModelAndView findOrders(Model model, String order_word, @RequestParam(value = "page",defaultValue = "0")int page,
                                   @RequestParam(value = "limit",defaultValue = "6")int limit){
        if(page != 0) page--;
        Page<Orders> pages = ordersService.adminSearchOrder(order_word,page,limit);
        model.addAttribute("TotalPages", pages.getTotalPages());//查询的页数
        model.addAttribute("Number", pages.getNumber()+1);//查询的当前第几页
        model.addAttribute("order_word",order_word);
        List<Orders> orders = pages.getContent();
        model.addAttribute("orders",orders);
        return new ModelAndView("admin/adminOrdersList");
    }


}
