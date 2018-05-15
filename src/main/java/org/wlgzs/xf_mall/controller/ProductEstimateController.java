package org.wlgzs.xf_mall.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.wlgzs.xf_mall.base.BaseController;
import org.wlgzs.xf_mall.entity.Orders;
import org.wlgzs.xf_mall.entity.ProductEstimate;
import org.wlgzs.xf_mall.service.OrdersService;
import org.wlgzs.xf_mall.service.ProductEstimateService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Auther: 李晓珊
 * @Date: 2018/4/23 13:11
 * @Description:
 */
@RequestMapping("/EstimateController")
@RestController
public class ProductEstimateController extends BaseController {

    //通过订单id跳转到评论界面
    @RequestMapping("/toAddEstimate")
    public ModelAndView toAdd(Model model, Long id) {
        Orders order = ordersService.findOrdersById(id);
        model.addAttribute("order", order);
        return new ModelAndView("addEstimate");
    }
    /**
     * @author 阿杰
     * @param [request, myFileNames, session, orderId]
     * @return org.springframework.web.servlet.ModelAndView
     * @description 添加评论
     */
    @RequestMapping("/AddEstimate")
    public ModelAndView AddEstimate(HttpServletRequest request, @RequestParam("file") MultipartFile[] myFileNames, HttpSession session, long orderId){
        productEstimateService.save(request,myFileNames,session,orderId);
        return new ModelAndView("redirect:/OrderController/findUserOrder");
    }
    /**
     * @author 阿杰
     * @param [model, userId]
     * @return org.springframework.web.servlet.ModelAndView
     * @description 个人评价展示
     */
    @RequestMapping("/userEstimate")
    public ModelAndView userEstimate(Model model, long userId) {
        List<ProductEstimate> productEstimates = productEstimateService.findEstimateById(userId);
        model.addAttribute("productEstimates", productEstimates);
        return new ModelAndView("userEstimate");
    }
    /**
     * @author 阿杰
     * @param [model, productId]
     * @return org.springframework.web.servlet.ModelAndView
     * @description 商品评价展示
     */
    @RequestMapping("/productEstimate")
    public ModelAndView productEstimate(Model model, long productId) {
        List<ProductEstimate> productEstimates = productEstimateService.findEstimateByproductId(productId);
        model.addAttribute("productEstimates", productEstimates);
        return new ModelAndView("productDetails") ;
    }
    /**
     * @author 阿杰
     * @param [estimateId, userId]
     * @return org.springframework.web.servlet.ModelAndView
     * @description 修改为匿名评价
     */
    @RequestMapping("/changeEstimate")
    public ModelAndView changeEstimate(long estimateId, long userId) {
        productEstimateService.changeEstimate(estimateId);
        return new ModelAndView("redirect:/EstimateController/userEstimate?userId="+userId);
    }

}
