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
        List<Orders> orders = pages.getContent();
        String img;
        for(int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getProduct_picture().contains(",")){
                img = orders.get(i).getProduct_picture();
                img = img.substring(0,img.indexOf(","));
                System.out.println("后台列表");
                orders.get(i).setProduct_picture(img);
            }
        }
        model.addAttribute("orders", pages.getContent());
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
        List<Orders> orders = pages.getContent();
        String img;
        for(int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getProduct_picture().contains(",")){
                img = orders.get(i).getProduct_picture();
                img = img.substring(0,img.indexOf(","));
                System.out.println("后台搜索订单");
                orders.get(i).setProduct_picture(img);
            }
        }
        model.addAttribute("order_number",order_number);
        model.addAttribute("orders", pages.getContent());
        return "admin/adminOrdersList";
    }
    //跳转至修改订单页面
    @RequestMapping("/toChangeProductOrders")
    public ModelAndView toEdit(Model model, Long id) {
        Orders order=ordersService.findOrdersById(id);
        model.addAttribute("order", order);
        return new ModelAndView("admin/adminEditOrders");
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
    public String edit(long orderId, HttpServletRequest request) {
        ordersService.edit(orderId, request);
        return "redirect:/OrderController/allProductOrdersLists";
    }
    //后台删除订单
    @RequestMapping("/deleteOrder")
    public ModelAndView delete(Long id){
        ordersService.delete(id);
        return new ModelAndView("redirect:/OrderController/allProductOrdersLists");
    }
    //根据用户名查询订单
    @RequestMapping("/findUserOrder")
    public ModelAndView UserOrders(Model model,String user_name){
        List<Orders> orders = ordersService.findOrdersByUserName(user_name);
        model.addAttribute("orders",orders);
        return new ModelAndView("userOrdersList");
    }

    //订单详情   前后台
    @RequestMapping("/orderDetails")
    public ModelAndView orderInfo(Model model, Long id) {
        Orders order=ordersService.findOrdersById(id);
        model.addAttribute("order", order);
        return new ModelAndView("admin/adminOrderInfo");
    }

    //后台多条件查询订单 分页
    @RequestMapping("/findOrders")
    public ModelAndView findOrders(Model model, String order_word, @RequestParam(value = "page",defaultValue = "0")int page,
                                   @RequestParam(value = "limit",defaultValue = "6")int limit){
        if(page != 0) page--;
        Page<Orders> pages = ordersService.searchOrder(order_word,page,limit);
        model.addAttribute("TotalPages", pages.getTotalPages());//查询的页数
        model.addAttribute("Number", pages.getNumber()+1);//查询的当前第几页
        List<Orders> orders = pages.getContent();
        model.addAttribute("orders",orders);
        return new ModelAndView("userOrdersList");
    }


}
