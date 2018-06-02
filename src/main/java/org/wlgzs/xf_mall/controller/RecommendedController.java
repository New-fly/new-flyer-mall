package org.wlgzs.xf_mall.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.wlgzs.xf_mall.base.BaseController;
import org.wlgzs.xf_mall.entity.Product;
import org.wlgzs.xf_mall.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @Auther: 阿杰
 * @Date: 2018/5/19 08:37
 * @Description: 商品推荐
 */
@RestController
@RequestMapping("RecommendedController")
public class RecommendedController extends BaseController {
    @RequestMapping("/recommended")
    public ModelAndView orderInfo(Model model, HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession();
        if(session!=null){
            User user = (User) session.getAttribute("user");
            long userId = user.getUserId();
            List<Product> recommendedProducts = productService.recommendedByUserId(userId, request);
            model.addAttribute("products", recommendedProducts);
        }
        return new ModelAndView("productList");
    }
}
