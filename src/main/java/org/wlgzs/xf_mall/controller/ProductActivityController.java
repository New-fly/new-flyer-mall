package org.wlgzs.xf_mall.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.wlgzs.xf_mall.base.BaseController;
import org.wlgzs.xf_mall.entity.Activity;
import org.wlgzs.xf_mall.entity.Product;
import org.wlgzs.xf_mall.entity.ProductActivity;
import org.wlgzs.xf_mall.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @Auther: 阿杰
 * @Date: 2018/5/2 00:26
 * @Description: 活动商品
 */
@RequestMapping("ActivityController")
@RestController
public class ProductActivityController extends BaseController {

    /**
     * @author 阿杰
     * @param []
     * @return org.springframework.web.servlet.ModelAndView
     * @description 跳转至活动页面
     */
    @RequestMapping("/activityProducts")
    public ModelAndView activityProductList(Model model, String activity_name, HttpServletRequest request) throws IOException {
        List<ProductActivity> productActivities = productActivityService.activityProductList(activity_name);
        model.addAttribute("productActivities",productActivities);
        Activity activity = activityService.findByActivityName(activity_name);
        String time = String.valueOf(activity.getActivity_time());
        time = time.substring(0,time.length()-5);
        model.addAttribute("time",time);
        model.addAttribute("activityTime",activity.getActivity_time());
        model.addAttribute("activity",activity);
        if(productActivities.size() == 0){
            model.addAttribute("mag","该活动暂时没有商品");
        }
        //推荐商品
        HttpSession session = request.getSession();
        if(session.getAttribute("user")!=null){
            User user = (User) session.getAttribute("user");
            long userId = user.getUserId();
            List<Product> recommendedProducts = productService.recommendedByUserId(userId,request);
            model.addAttribute("recommendedProducts", recommendedProducts);
        }
        return new ModelAndView("productActivityList");
    }
}
