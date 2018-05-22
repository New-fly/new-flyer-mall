package org.wlgzs.xf_mall.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.wlgzs.xf_mall.base.BaseController;
import org.wlgzs.xf_mall.entity.Activity;
import org.wlgzs.xf_mall.entity.Product;
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
        //商品展示
        List<Product> products = productService.homeProductList(productOneCategories);
        model.addAttribute("products",products);
        //推荐商品
        HttpSession session = request.getSession();
        if(session.getAttribute("user")!=null){
            long userId = (long) session.getAttribute("userId");
            List<Product> recommendedProducts = productService.recommendedByUserId(userId);
            model.addAttribute("recommendedProducts", recommendedProducts);
        }
        return new ModelAndView("Index");
    }
}
