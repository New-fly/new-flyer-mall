package org.wlgzs.xf_mall.controller.admin;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.wlgzs.xf_mall.entity.SearchShield;
import org.wlgzs.xf_mall.service.SearchShieldService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author:胡亚星
 * @createTime 2018-04-20 16:30
 * @description:
 **/
@Controller
@RequestMapping("SearchShieldController")
public class SearchShieldController {
    @Resource
    SearchShieldService searchShieldService;

    /**
     * @param
     * @return
     * @author 胡亚星
     * @date 2018/4/20 17:18
     * @Description:后台遍历敏感词汇
     */
    @RequestMapping("/toProductSensitive")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "limit", defaultValue = "10") int limit) {
        String search_keywords = "";
        if(page != 0) page--;
        Page<SearchShield> pages = searchShieldService.getSearchShieldListPage(search_keywords,page,limit);
        model.addAttribute("TotalPages", pages.getTotalPages());//查询的页数
        model.addAttribute("Number", pages.getNumber()+1);//查询的当前第几页
        List<SearchShield> searchShields = pages.getContent();
        model.addAttribute("searchShields", searchShields);//查询的当前页的集合
        return "admin/productSensitive";
    }
    /**
     * @param
     * @return
     * @author 胡亚星
     * @date 2018/4/20 16:52
     * @Description:后台添加敏感词汇
     */
    @RequestMapping("/addProductSensitive")
    public String addSearchShield(SearchShield searchShield) {
        searchShieldService.save(searchShield);
        return "redirect:/SearchShieldController/toProductSensitive";
    }

    /**
     * @param
     * @return
     * @author 胡亚星
     * @date 2018/4/20 17:01
     * @Description:后台删除敏感词汇
     */
    @RequestMapping("/deleteProductSensitive")
    public String deleteSearchShield(Long searchShieldId) {
        searchShieldService.delete(searchShieldId);
        return "redirect:/SearchShieldController/toProductSensitive";
    }

    /**
     * @param
     * @return
     * @author 胡亚星
     * @date 2018/4/20 19:50
     * @Description: 后台搜索敏感词汇
     */
    @RequestMapping("/findProductSensitive")
    public String findSearchShield(Model model,String search_keywords, @RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "limit", defaultValue = "10") int limit) {
        Page<SearchShield> pages = searchShieldService.getSearchShieldListPage(search_keywords,page,limit);
        model.addAttribute("TotalPages", pages.getTotalPages());//查询的页数
        model.addAttribute("Number", pages.getNumber());//查询的当前第几页
        List<SearchShield> searchShields = pages.getContent();

        model.addAttribute("searchShields", searchShields);//查询的当前页的集合
        model.addAttribute("search_keywords",search_keywords);
        return "admin/productSensitive";
    }

}


