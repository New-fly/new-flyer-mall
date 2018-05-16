package org.wlgzs.xf_mall.controller;

import com.alipay.api.AlipayApiException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.wlgzs.xf_mall.base.BaseController;
import org.wlgzs.xf_mall.entity.Product;
import org.wlgzs.xf_mall.entity.ShippingAddress;
import org.wlgzs.xf_mall.entity.ShoppingCart;
import org.wlgzs.xf_mall.service.OrdersService;
import org.wlgzs.xf_mall.service.ProductService;
import org.wlgzs.xf_mall.service.ShippingAddressService;
import org.wlgzs.xf_mall.util.IdsUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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
     * @author 阿杰
     * @param [model, shoppingCartId]
     * @return org.springframework.web.servlet.ModelAndView
     * @description 准备付款
     */
    @RequestMapping("toPay")
    public ModelAndView toPay(Model model, @RequestParam(value = "shoppingCartId",defaultValue = "630,637") String shoppingCartId,
                              String user_name,HttpServletRequest request){
        IdsUtil idsUtil = new IdsUtil();
        long[] Ids = idsUtil.IdsUtils(shoppingCartId);
        List<ShoppingCart> shoppingCarts = productService.findAllByIds(Ids);
        model.addAttribute("shoppingCarts",shoppingCarts);
        model.addAttribute("shoppingCount",0);
        if(user_name == null){
            HttpSession session = request.getSession(true);
            user_name = (String) session.getAttribute("name");
        }
        List<ShippingAddress> shippingAddressList = shippingAddressService.getShippingAddressList(user_name);
        model.addAttribute("shippingAddressList", shippingAddressList);
        return new ModelAndView("indent");
    }
    /**
     * @author 阿杰
     * @param [model, shoppingCartId]
     * @return org.springframework.web.servlet.ModelAndView
     * @description 单个商品  准备直接付款
     */
    @RequestMapping("oneToPay")
    public ModelAndView oneToPay(Model model, @RequestParam(value = "productId",defaultValue = "494") long productId,
                                 @RequestParam(value = "shoppingCart_count",defaultValue = "3") int shoppingCart_count,
                                 String user_name,HttpServletRequest request){
        List<Product> shoppingCarts = productService.findProductListById(productId);
        model.addAttribute("shoppingCarts",shoppingCarts);
        System.out.println("购买商品id："+productId);
        System.out.println("单个购买数量："+shoppingCart_count);
        model.addAttribute("shoppingCount",shoppingCart_count);
        if(user_name == null){
            HttpSession session = request.getSession(true);
            user_name = (String) session.getAttribute("name");
        }
        List<ShippingAddress> shippingAddressList = shippingAddressService.getShippingAddressList(user_name);
        model.addAttribute("shippingAddressList", shippingAddressList);
        return new ModelAndView("indent");
    }
    /**
     * @author 阿杰
     * @param []
     * @return org.springframework.web.servlet.ModelAndView
     * @description 跳转至准备支付页面
     */
    @RequestMapping("toAliPay")
    public ModelAndView toAliPay(){
        return new ModelAndView("aliPay");
    }
    /**
     * @author 阿杰
     * @param [model, response, request]
     * @return org.springframework.web.servlet.ModelAndView
     * @description 付款
     */
    @RequestMapping("aliPaySum")
    public ModelAndView aliPay(@RequestParam(value = "productId",defaultValue = "494,495") String productId,
                               @RequestParam(value = "userId",defaultValue = "46") long userId,
                               @RequestParam(value = "shoppingCount",defaultValue = "3,4") String shoppingCount,
                               HttpServletResponse response, HttpServletRequest request) throws IOException, AlipayApiException {
        System.out.println("付款商品id："+productId);
        System.out.println("购买商品数量："+shoppingCount);
        ordersService.save(request,response,productId,userId,shoppingCount);
        return new ModelAndView("aliPay");
    }
    /**
     * @author 阿杰
     * @param [productId, userId, request]
     * @return org.springframework.web.servlet.ModelAndView
     * @description 积分兑换
     */
    @RequestMapping("estimatePay")
    public ModelAndView estimatePay(@RequestParam(value = "productId",defaultValue = "233") long productId,
                               @RequestParam(value = "userId",defaultValue = "46") long userId,
                               HttpServletRequest request) {
        ordersService.estimatePaySave(request,productId,userId);
        return new ModelAndView("redirect:/ProductListController/toProductList");
    }
    /**
     * @author 阿杰
     * @param []
     * @return org.springframework.web.servlet.ModelAndView
     * @description 结算后跳转
     */
    @RequestMapping("aliReturn")
    public ModelAndView aliReturn() {
        //ordersService.aliReturn(response,request);
        return new ModelAndView("redirect:/ProductListController/toProductList");
    }
    /**
     * @author 阿杰
     * @param [model, response, request]
     * @return org.springframework.web.servlet.ModelAndView
     * @description 退款
     *//*
    @RequestMapping("refund")
    public ModelAndView refund(Model model, HttpServletResponse response, HttpServletRequest request) throws AlipayApiException, IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
        //设置请求参数
        AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();

        //商户订单号，商户网站订单系统中唯一订单号
        String out_trade_no = new String(request.getParameter("WIDTRout_trade_no"));
        //支付宝交易号
        String trade_no = new String(request.getParameter("WIDTRtrade_no"));
        //请二选一设置
        //需要退款的金额，该金额不能大于订单金额，必填
        String refund_amount = new String(request.getParameter("WIDTRrefund_amount"));
        //退款的原因说明
        String refund_reason = new String(request.getParameter("WIDTRrefund_reason"));
        //标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传
        String out_request_no = new String(request.getParameter("WIDTRout_request_no"));

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"trade_no\":\""+ trade_no +"\","
                + "\"refund_amount\":\""+ refund_amount +"\","
                + "\"refund_reason\":\""+ refund_reason +"\","
                + "\"out_request_no\":\""+ out_request_no +"\"}");

        //请求
        String result = alipayClient.execute(alipayRequest).getBody();
        //输出
        out.println(result);
        return new ModelAndView("aliPayRefund");
    }*/
}
