package org.wlgzs.xf_mall.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.wlgzs.xf_mall.base.BaseController;
import org.wlgzs.xf_mall.entity.Activity;
import org.wlgzs.xf_mall.entity.Product;
import org.wlgzs.xf_mall.entity.ProductActivity;
import org.wlgzs.xf_mall.entity.ProductCategory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 阿杰
 * @Date: 2018/5/9 20:52
 * @Description: 主页
 */
@RequestMapping("HomeController")
@RestController
public class HomeController extends BaseController {

    /**
     * @author 阿杰
     * @param [model]
     * @return org.springframework.web.servlet.ModelAndView
     * @description 主页商品数据
     */
    @RequestMapping("/homeProduct")
    public ModelAndView homeProduct(Model model, HttpServletRequest request){
        List<ProductCategory> productOneCategories = productService.findProductOneCategoryList();
        model.addAttribute("productOneCategories", productOneCategories);
        List<ProductCategory> productTwoCategories = productService.findProductTwoCategoryList();
        model.addAttribute("productTwoCategories", productTwoCategories);
        //活动商品  轮播图
        List<Activity> activities = activityService.getActivity();
        model.addAttribute("activities",activities);
        //抢购商品
        String activity_name = "520狂欢";
        List<ProductActivity> productActivities = productActivityService.activityProductList(activity_name);
        model.addAttribute("productActivities",productActivities);
        Activity activity = activityService.findByActivityName(activity_name);
        String time = String.valueOf(activity.getActivity_time());
        time = time.substring(0,time.length()-5);
        model.addAttribute("time",time);
        model.addAttribute("activity",activity.getActivity_time());
        //商品展示
        List<Product> products = productService.homeProductList(productOneCategories);
        model.addAttribute("products",products);
        //新品商品
        List<Product> productList = productService.getProductList();
        model.addAttribute("productList",productList);
        return new ModelAndView("Index");
    }
}
