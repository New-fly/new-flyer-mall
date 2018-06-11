package org.wlgzs.xf_mall.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
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
import org.wlgzs.xf_mall.util.RandonNumberUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

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
        //System.out.println("购买商品id：" + productId);
        model.addAttribute("shoppingCount", 1);
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("user");
        String user_name = user.getUser_name();
        Activity activity = activityService.findByActivityName(shoppingCarts.get(0).getProduct_activity());
        model.addAttribute("activity", activity);
        Date date = new Date();
        int is = activity.getActivity_time().compareTo(date);
        model.addAttribute("is",is);
        if(is<0){
            model.addAttribute("mag","抢购活动时间已经结束");
            return new ModelAndView("redirect:/HomeController/homeProduct");
        }
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
    @RequestMapping("ali")
    public ModelAndView ali(HttpServletResponse response, HttpServletRequest request) throws IOException, AlipayApiException {
        ordersService.ali(response,request);
        return new ModelAndView("aliPay");
    }
    /**
     * @param [model, response, request]
     * @return org.springframework.web.servlet.ModelAndView
     * @author 阿杰
     * @description 付款后跳转
     */
    @RequestMapping("aliPaySum")
    public ModelAndView aliPay(Model model,@RequestParam(value = "productId", defaultValue = "825") String productId,
                               @RequestParam(value = "shoppingCount", defaultValue = "1") String shoppingCount,
                               HttpServletResponse response, HttpServletRequest request) throws IOException, AlipayApiException {
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("user");
        long userId = user.getUserId();
        if(ordersService.findOrdersByOrderNumber((String) session.getAttribute("orderNumber")).size()!=0){
            model.addAttribute("mag","该订单已经存在");
            return new ModelAndView("redirect:/UserOrderController/userOrderList?userId=" + userId);
        }
        ordersService.save(request, response, productId, shoppingCount);
        return new ModelAndView("redirect:/UserOrderController/userOrderList?userId=" + userId);
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
    public ModelAndView estimatePay(Model model,@RequestParam(value = "productId", defaultValue = "233") long productId,
                                    HttpServletRequest request) {
        List<Product> shoppingCarts = productService.findProductListById(productId);
        model.addAttribute("shoppingCarts", shoppingCarts);
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("user");
        if (user.getUserIntegral() < shoppingCarts.get(0).getProduct_needPoints()) {
            model.addAttribute("mag","您的积分不足");
            return new ModelAndView("redirect:/ProductListController/integralProduct");
        }
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
    /*@RequestMapping("return")
    public String returnUrl(HttpServletRequest request, HttpServletResponse response) throws AlipayApiException {
        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        //切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
        //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset,AlipayConfig.sign_type);
        if(signVerified) {
            //商户订单号
            String orderNumber = RandonNumberUtils.getOrderIdByUUId();
            String out_trade_no = new String(orderNumber);
            //支付宝交易号
            String trade_no = new String(RandonNumberUtils.getOrderIdByUUId());
            //付款金额
            String total_amount = new String(request.getParameter("WIDtotal_amount"));
            request.setAttribute("out_trade_no", out_trade_no);
            request.setAttribute("trade_no", trade_no);
            request.setAttribute("total_amount", total_amount);


            System.out.println("订单处理：系统订单号" + out_trade_no + "支付宝交易号：" + trade_no);
            //系统处理根据支付宝回调更改订单状态或者其他关联表的数据
            List<Orders> order = ordersService.findOrdersByOrderNumber(out_trade_no);
           // OrderInfo order = payService.findOneByTradeCode(out_trade_no);
            if(order == null){
                signVerified = false;
                request.setAttribute("signVerified", signVerified);
                request.setAttribute("reason", "商户订单号不存在");
                System.out.println("系统订单："+ out_trade_no + "不存在。");
            }else{
                int b = Integer.valueOf(total_amount);
                System.out.println(b+"订单金额--------");
                if(order.get(0).getProduct_PaidPrice() == b){
                    signVerified = false;
                    request.setAttribute("signVerified", signVerified);
                    request.setAttribute("reason", "付款金额不对");
                    return "notify_url";
                }
                if(order.get(0).getOrder_status().equals("待收货")){//判断当前订单是否已处理，避免重复处理
                    System.out.println("系统订单："+ out_trade_no + "无需重复处理。");
                }else{
                    System.out.println("系统订单："+ out_trade_no + "成功支付。");
                }
            }
        }else{
            request.setAttribute("reason", "验签失败");
        }
        request.setAttribute("signVerified", signVerified);
        return "return_url";
    }*/
}
