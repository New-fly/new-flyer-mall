package org.wlgzs.xf_mall.controller;

import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.wlgzs.xf_mall.base.BaseController;
import org.wlgzs.xf_mall.entity.Activity;
import org.wlgzs.xf_mall.entity.Product;
import org.wlgzs.xf_mall.entity.ProductActivity;
import org.wlgzs.xf_mall.service.ActivityService;
import org.wlgzs.xf_mall.service.ProductActivityService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    public ModelAndView activityProductList(Model model, HttpServletRequest request, long activitySumId, @RequestParam(value = "page",defaultValue = "0") int page,
                                            @RequestParam(value = "limit",defaultValue = "10") int limit){
        Activity activity = activityService.findActivity(activitySumId);
        model.addAttribute("activity",activity);
        String activity_name = activity.getActivity_name();
        if(page != 0) page--;
        Page activityPages =  productActivityService.activityProductList(activity_name,page,limit);
        model.addAttribute("ActivityTotalPages", activityPages.getTotalPages());//查询的页数
        model.addAttribute("ActivityNumber", activityPages.getNumber()+1);//查询的当前第几页
        List<ProductActivity> activities = activityPages.getContent();
        model.addAttribute("activities", activities);//查询的当前页的集合
        //推荐商品
        HttpSession session = request.getSession();
        if(session!=null){
            long userId = (long) session.getAttribute("userId");
            List<Product> recommendedProducts = productService.recommendedByUserId(userId);
            model.addAttribute("recommendedProducts", recommendedProducts);
        }
        return new ModelAndView("productActivityList");
    }
}
