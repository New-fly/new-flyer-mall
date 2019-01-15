package org.wlgzs.xf_mall.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.wlgzs.xf_mall.base.BaseController;
import org.wlgzs.xf_mall.entity.Footprint;
import org.wlgzs.xf_mall.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2018/5/5 11:24
 * @Description: 足迹
 */
@RequestMapping("FootprintController")
@RestController
public class FootprintController extends BaseController {

    /* *
     * @author 李晓珊
     * @param [request, userId, productId]
     * @return void
     * @description 增加足迹
     */
    /*@RequestMapping("/addFootprint")
    public void addFootprint(HttpServletRequest request, long userId, long productId){
        footprintService.save(request,userId,productId);
    }*/

    /**
     * @param [footprintId]
     * @return java.lang.String
     * @author 阿杰
     * @description 删除足迹
     */
    @RequestMapping("/deleteFootprint")
    public String delete(long footprintId) {
        footprintService.delete(footprintId);
        return "成功";
    }

    /**
     * @param [footprintId, userId]
     * @return org.springframework.web.servlet.ModelAndView
     * @author 阿杰
     * @description 批量删除足迹
     */
    @RequestMapping("/deleteFootprints")
    public ModelAndView deleteFootprints(@RequestParam(value = "footprintId", defaultValue = "466,467") String footprintId
            ,HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        long userId = user.getUserId();
        footprintService.deleteFootprints(footprintId);
        String url = "redirect:/FootprintController/userFootprint?userId=" + userId;
        return new ModelAndView(url);
    }

    /**
     * @param [model, product_keywords, userId]
     * @return org.springframework.web.servlet.ModelAndView
     * @author 阿杰
     * @description 搜索足迹商品
     */
    @RequestMapping("/findCollectionProducts")
    public ModelAndView findFootprintProducts(Model model, String product_keywords, long userId) {
        List<Footprint> footprints = footprintService.findFootprints(product_keywords, userId);
        model.addAttribute("footprints", footprints);
        String url = "redirect:/FootprintController/userFootprint?userId=" + userId;
        return new ModelAndView(url);
    }

    /**
     * @param [model, userId]
     * @return org.springframework.web.servlet.ModelAndView
     * @author 李晓珊
     * @description 遍历足迹
     */
    @RequestMapping("/userFootprint")
    public ModelAndView findFootprintByUserId(Model model, long userId) {
        List<Footprint> footprints = footprintService.findFootprintByUserId(userId);
        model.addAttribute("footprints", footprints);
        return new ModelAndView("footprint");
    }
}