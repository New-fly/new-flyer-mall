package org.wlgzs.xf_mall.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.wlgzs.xf_mall.entity.Footprint;
import org.wlgzs.xf_mall.service.FootprintService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2018/5/5 11:24
 * @Description: 足迹
 */
@RequestMapping("FootprintController")
@RestController
public class FootprintController {
    @Resource
    FootprintService footprintService;

    /**
     * @author 李晓珊
     * @param [request, userId, productId]
     * @return void
     * @description 增加足迹
     */
    @RequestMapping("/addFootprint")
    public void addFootprint(HttpServletRequest request, long userId, long productId){
        footprintService.save(request,userId,productId);
    }
    /**
     * @author 阿杰
     * @param [footprintId]
     * @return java.lang.String
     * @description 删除足迹
     */
    @RequestMapping("/deleteFootprint")
    public String delete(long footprintId){
        footprintService.delete(footprintId);
        return "成功";
    }
    /**
     * @author 阿杰
     * @param [footprintId, userId]
     * @return org.springframework.web.servlet.ModelAndView
     * @description 批量删除足迹
     */
    @RequestMapping("/deleteFootprints")
    public ModelAndView deleteFootprints(@RequestParam(value = "footprintId",defaultValue = "466,467") String footprintId, long userId){
        footprintService.deleteFootprints(footprintId);
        String url="redirect:/FootprintController/userFootprint?userId="+userId;
        return new ModelAndView(url);
    }
    /**
     * @author 阿杰
     * @param [model, product_keywords, userId]
     * @return org.springframework.web.servlet.ModelAndView
     * @description 搜索足迹商品
     */
    @RequestMapping("/findCollectionProducts")
    public ModelAndView findFootprintProducts(Model model,String product_keywords,long userId){
        List<Footprint> footprints = footprintService.findFootprints(product_keywords,userId);
        model.addAttribute("footprints",footprints);
        System.out.println(footprints);
        String url="redirect:/FootprintController/userFootprint?userId="+userId;
        return new ModelAndView(url);
    }
    /**
     * @author 李晓珊
     * @param [model, userId]
     * @return org.springframework.web.servlet.ModelAndView
     * @description 遍历足迹
     */
    @RequestMapping("/userFootprint")
    public ModelAndView findFootprintByUserId(Model model,long userId){
       List<Footprint> footprints =  footprintService.findFootprintByUserId(userId);
       model.addAttribute("footprints",footprints);
       return new ModelAndView("footprint");
    }
}