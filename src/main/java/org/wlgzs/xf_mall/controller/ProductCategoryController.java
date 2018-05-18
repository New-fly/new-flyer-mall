package org.wlgzs.xf_mall.controller;

import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.wlgzs.xf_mall.base.BaseController;
import org.wlgzs.xf_mall.entity.Product;
import org.wlgzs.xf_mall.entity.ProductCategory;
import org.wlgzs.xf_mall.service.ProductService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 阿杰
 * @Date: 2018/5/8 13:40
 * @Description: 分类Controller
 */
@RequestMapping("ProductCategoryController")
@RestController
public class ProductCategoryController extends BaseController {

    /**
     * @param [model]
     * @return org.springframework.web.servlet.ModelAndView
     * @author 阿杰
     * @description 遍历一级分类 二级分类(搜索页面)
     */
    @RequestMapping("/findCategory")
    public ModelAndView findCategory(Model model) {
        List<ProductCategory> productOneCategories = productService.findProductOneCategoryList();
        model.addAttribute("productOneCategories", productOneCategories);
        List<ProductCategory> productTwoCategories = productService.findProductTwoCategoryList();
        model.addAttribute("productTwoCategories", productTwoCategories);
        return new ModelAndView("productList");
    }

    /**
     * @param [model]
     * @return org.springframework.web.servlet.ModelAndView
     * @author 阿杰
     * @description 遍历一级分类
     */
    @RequestMapping("/findCategoryOne")
    public ModelAndView findCategoryOne(Model model) {
        List<ProductCategory> productOneCategories = productService.findProductOneCategoryList();
        model.addAttribute("productOneCategories", productOneCategories);
        return new ModelAndView("productList");
    }

    /**
     * @param [model]
     * @return org.springframework.web.servlet.ModelAndView
     * @author 阿杰
     * @description 遍历二级分类
     */
    @RequestMapping("/findCategoryTwo")
    public ModelAndView findCategoryTwo(Model model) {
        List<ProductCategory> productTwoCategories = productService.findProductTwoCategoryList();
        model.addAttribute("productTwoCategories", productTwoCategories);
        return new ModelAndView("productList");
    }

    /**
     * @author 阿杰
     * @param [model, category, page, limit]
     * @return org.springframework.web.servlet.ModelAndView
     * @description 通过一级分类查找商品 分页
     */
    @RequestMapping("/byOneCategoryFindProduct")
    public ModelAndView byOneCategoryFindProduct(Model model, String category, @RequestParam(value = "page", defaultValue = "0") int page,
                                                 @RequestParam(value = "limit", defaultValue = "12") int limit) {
        if(page != 0) page--;
        List<ProductCategory> productCategories = productService.findProductByOneCategory(category);

        ProductCategory[] toBeStored = productCategories.toArray(new ProductCategory[productCategories.size()]);
        String[] strs = new String[toBeStored.length];
        int TotalPages = 0;
        int Number = 0;
        List<Product> products = new ArrayList<Product>();
        for (int i = 0; i < toBeStored.length; i++) {
            strs[i] = toBeStored[i].getCategory_name();
            Page<Product> pages = productService.findProductByTwoCategory(strs[i], page, limit);
            TotalPages = TotalPages + pages.getTotalPages();  //查询的页数
            Number = Number + pages.getNumber() + 1;//查询的当前第几页
            products.addAll(pages.getContent());//查询的当前页的集合
        }
        model.addAttribute("TotalPages",TotalPages);//查询的页数
        model.addAttribute("Number",Number);//查询的当前第几页
        model.addAttribute("products",products);//查询的当前页的集合
        return new ModelAndView("productList");
    }

    /**
     * @param [model, product_category]
     * @return org.springframework.web.servlet.ModelAndView
     * @author 阿杰
     * @description 通过二级分类查找商品 分页
     */
    @RequestMapping("/byTwoCategoryFindProduct")
    public ModelAndView byTwoCategoryFindProduct(Model model, String product_category, @RequestParam(value = "page", defaultValue = "0") int page,
                                                 @RequestParam(value = "limit", defaultValue = "12") int limit) {
        if(page != 0) page--;
        Page<Product> pages = productService.findProductByTwoCategory(product_category, page, limit);
        model.addAttribute("TotalPages", pages.getTotalPages());//查询的页数
        model.addAttribute("Number", pages.getNumber() + 1);//查询的当前第几页
        model.addAttribute("products", pages.getContent());//查询的当前页的集合
        System.out.println(pages.getContent());
        return new ModelAndView("productList");
    }
    /**
     * @author 阿杰
     * @param [model, product_category]  
     * @return org.springframework.web.servlet.ModelAndView  
     * @description 通过二级分类查找商品 不分页（主页）
     */ 
    @RequestMapping("/byCategoryFindProduct")
    public ModelAndView byCategoryFindProduct(Model model, String product_category) {
        List<Product> products = productService.findProductByCategory(product_category);
        System.out.println(products);
        model.addAttribute("products", products);
        return new ModelAndView("productList");
    }
}
