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
        List<Object> activityPictureList = new ArrayList<Object>();
        for (int i = 0; i < activities.size(); i++) {
            activityPictureList.add(activities.get(i).getActivity_picture());
        }
        model.addAttribute("activities",activities);
        model.addAttribute("activityPictureList",activityPictureList);
        //商品展示
        List<Product> productsOne = productService.productByOneCategory(productOneCategories.get(0).getCategory_name());
        model.addAttribute("productsOne",productsOne);//主页部分第一分类商品
        List<Product> productsTwo = productService.productByOneCategory(productOneCategories.get(1).getCategory_name());
        model.addAttribute("productsTwo",productsTwo);//主页部分第二分类商品
        List<Product> productsThree = productService.productByOneCategory(productOneCategories.get(2).getCategory_name());
        model.addAttribute("productsThree",productsThree);//主页部分第三分类商品
        List<Product> productsFour = productService.productByOneCategory(productOneCategories.get(3).getCategory_name());
        model.addAttribute("productsFour",productsFour);//主页部分第四分类商品
        List<Product> productsFive = productService.productByOneCategory(productOneCategories.get(4).getCategory_name());
        model.addAttribute("productsFive",productsFive);//主页部分第五分类商品
        //推荐商品
        HttpSession session = request.getSession();
        if(session!=null){
            long userId = (long) session.getAttribute("userId");
            List<Product> recommendedProducts = productService.recommendedByUserId(userId);
            model.addAttribute("recommendedProducts", recommendedProducts);
        }
        return new ModelAndView("Index");
    }
}
