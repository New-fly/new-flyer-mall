package org.wlgzs.xf_mall.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeRefundRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.wlgzs.xf_mall.base.BaseController;
import org.wlgzs.xf_mall.entity.*;
import org.wlgzs.xf_mall.service.OrdersService;
import org.wlgzs.xf_mall.service.ProductService;
import org.wlgzs.xf_mall.service.ShippingAddressService;
import org.wlgzs.xf_mall.util.AlipayConfig;
import org.wlgzs.xf_mall.util.IdsUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @Auther: 阿杰
 * @Date: 2018/4/30 10:20
 * @Description: 支付  退款
 */
@RestController
@RequestMapping("aliPay")
public class AliPayController extends BaseController {

    /**
     * @param [model, shoppingCartId]
     * @return org.springframework.web.servlet.ModelAndView
     * @author 阿杰
     * @description 准备付款
     */
    @RequestMapping("toPay")
    public ModelAndView toPay(Model model, @RequestParam(value = "shoppingCartId", defaultValue = "630,637") String shoppingCartId,
                              String user_name, HttpServletRequest request) {
        IdsUtil idsUtil = new IdsUtil();
        long[] Ids = idsUtil.IdsUtils(shoppingCartId);
        List<ShoppingCart> shoppingCarts = productService.findAllByIds(Ids);
        model.addAttribute("shoppingCarts", shoppingCarts);
        model.addAttribute("shoppingCount", 0);
        if (user_name == null) {
            HttpSession session = request.getSession(true);
            User user = (User) session.getAttribute("user");
            user_name = user.getUser_name();
        }
        List<ShippingAddress> shippingAddressList = shippingAddressService.getShippingAddressList(user_name);
        model.addAttribute("shippingAddressList", shippingAddressList);
        return new ModelAndView("indent");
    }

    /**
     * @param [model, shoppingCartId]
     * @return org.springframework.web.servlet.ModelAndView
     * @author 阿杰
     * @description 单个商品  准备直接付款
     */
    @RequestMapping("oneToPay")
    public ModelAndView oneToPay(Model model, @RequestParam(value = "productId", defaultValue = "494") long productId,
                                 @RequestParam(value = "shoppingCart_count", defaultValue = "1") int shoppingCart_count,
                                 String user_name, HttpServletRequest request) {
        List<Product> shoppingCarts = productService.findProductListById(productId);
        model.addAttribute("shoppingCarts", shoppingCarts);
        System.out.println("购买商品id：" + productId);
        System.out.println("单个购买数量：" + shoppingCart_count);
        model.addAttribute("shoppingCount", shoppingCart_count);
        if (user_name == null) {
            HttpSession session = request.getSession(true);
            User user = (User) session.getAttribute("user");
            user_name = user.getUser_name();
        }
        List<ShippingAddress> shippingAddressList = shippingAddressService.getShippingAddressList(user_name);
        model.addAttribute("shippingAddressList", shippingAddressList);
        return new ModelAndView("indent");
    }

    /**
     * @param [model, productId]
     * @return org.springframework.web.servlet.ModelAndView
     * @author 阿杰
     * @description 活动商品  准备直接付款
     */
    @RequestMapping("oneActivityPay")
    public ModelAndView oneActivityPay(Model model, @RequestParam(value = "productId", defaultValue = "494") long productId,
                                       HttpServletRequest request) {
        List<Product> shoppingCarts = productService.findProductListById(productId);
        model.addAttribute("shoppingCarts", shoppingCarts);
        System.out.println("购买商品id：" + productId);
        model.addAttribute("shoppingCount", 1);
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("user");
        String user_name = user.getUser_name();
        Activity activity = activityService.findByActivityName(shoppingCarts.get(0).getProduct_activity());
        model.addAttribute("activity", activity);
        List<ShippingAddress> shippingAddressList = shippingAddressService.getShippingAddressList(user_name);
        model.addAttribute("shippingAddressList", shippingAddressList);
        return new ModelAndView("indentActivity");
    }

    /**
     * @param []
     * @return org.springframework.web.servlet.ModelAndView
     * @author 阿杰
     * @description 跳转至准备支付页面
     */
    @RequestMapping("toAliPay")
    public ModelAndView toAliPay() {
        return new ModelAndView("aliPay");
    }

    /**
     * @param [model, response, request]
     * @return org.springframework.web.servlet.ModelAndView
     * @author 阿杰
     * @description 付款
     */
    @RequestMapping("aliPaySum")
    public ModelAndView aliPay(@RequestParam(value = "productId", defaultValue = "494,495") String productId,
                               @RequestParam(value = "shoppingCount", defaultValue = "3,4") String shoppingCount,
                               HttpServletResponse response, HttpServletRequest request) throws IOException, AlipayApiException {
        System.out.println("付款商品id：" + productId);
        System.out.println("购买商品数量：" + shoppingCount);
        ordersService.save(request, response, productId, shoppingCount);
        return new ModelAndView("aliPay");
    }

    /**
     * @param [model, productId, shoppingCart_count, user_name, request]
     * @return org.springframework.web.servlet.ModelAndView
     * @author 阿杰
     * @description 跳转至准备兑换页面
     */
    @RequestMapping("toChange")
    public ModelAndView toChange(Model model, @RequestParam(value = "productId", defaultValue = "494") long productId,
                                 @RequestParam(value = "shoppingCart_count", defaultValue = "1") int shoppingCart_count,
                                 HttpServletRequest request) {
        List<Product> shoppingCarts = productService.findProductListById(productId);
        model.addAttribute("shoppingCarts", shoppingCarts);
        model.addAttribute("shoppingCount", shoppingCart_count);
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("user");
        String user_name = user.getUser_name();
        if (user.getUserIntegral() < shoppingCarts.get(0).getProduct_needPoints()) {
            model.addAttribute("mag","您的积分不足");
            return new ModelAndView("redirect:/ProductListController/integralProduct");
        }
        List<ShippingAddress> shippingAddressList = shippingAddressService.getShippingAddressList(user_name);
        model.addAttribute("shippingAddressList", shippingAddressList);
        return new ModelAndView("change");
    }

    /**
     * @param [productId, userId, request]
     * @return org.springframework.web.servlet.ModelAndView
     * @author 阿杰
     * @description 积分兑换
     */
    @RequestMapping("estimatePay")
    public ModelAndView estimatePay(@RequestParam(value = "productId", defaultValue = "233") long productId,
                                    HttpServletRequest request) {
        ordersService.estimatePaySave(request, productId);
        return new ModelAndView("redirect:/ProductListController/integralProduct");
    }

    /**
     * @param []
     * @return org.springframework.web.servlet.ModelAndView
     * @author 阿杰
     * @description 结算后跳转
     */
    @RequestMapping("aliReturn")
    public ModelAndView aliReturn(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("user");
        long userId = user.getUserId();
        return new ModelAndView("redirect:/UserOrderController/userOrderList?userId=" + userId);
    }

    /**
     * @param [model, response, request]
     * @return org.springframework.web.servlet.ModelAndView
     * @author 阿杰
     * @description 退款
     */
    @RequestMapping("refund")
    public ModelAndView refund(long orderId, HttpServletResponse response, HttpSession session) throws AlipayApiException, IOException {
        ordersService.refund(orderId, response, session);
        User user = (User) session.getAttribute("user");
        long userId = user.getUserId();
        return new ModelAndView("redirect:/UserOrderController/userOrderList?userId=" + userId);
    }
}
