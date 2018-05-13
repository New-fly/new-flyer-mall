package org.wlgzs.xf_mall.controller;

import com.google.gson.Gson;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.wlgzs.xf_mall.entity.Collection;
import org.wlgzs.xf_mall.entity.Product;
import org.wlgzs.xf_mall.entity.ProductCategory;
import org.wlgzs.xf_mall.entity.ShoppingCart;
import org.wlgzs.xf_mall.service.FootprintService;
import org.wlgzs.xf_mall.service.ProductService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 阿杰
 * @Date: 2018/4/24 16:44
 * @Description: 有关商品  商品分类的操作
 */
@RequestMapping("ProductListController")
@RestController
public class ProductListController {
    @Resource
    ProductService productService;
    @Resource
    FootprintService footprintService;
    /**
     * @author 阿杰
     * @param [model]
     * @return org.springframework.web.servlet.ModelAndView
     * @description 跳转至商品列表页面
     */
    @RequestMapping("/toProductList")
    public ModelAndView toProductList(Model model, @RequestParam(value = "page",defaultValue = "0") int page,
                                      @RequestParam(value = "limit",defaultValue = "12") int limit){
        String product_keywords ="";
        if(page != 0) page--;
        Page pages =  productService.getProductListPage(product_keywords,page,limit);
        model.addAttribute("TotalPages", pages.getTotalPages());//查询的页数
        model.addAttribute("Number", pages.getNumber()+1);//查询的当前第几页
        List<Product> products = pages.getContent();
        String img;
        for(int i = 0; i < products.size(); i++) {
            if (products.get(i).getProduct_picture().contains(",")){
                img = products.get(i).getProduct_picture();
                img = img.substring(0,img.indexOf(","));
                System.out.println();
                products.get(i).setProduct_picture(img);
            }
        }
        model.addAttribute("products", products);//查询的当前页的集合
        return new ModelAndView("productList");
    }
    /**
     * @author 阿杰
     * @param [model, id]
     * @return org.springframework.web.servlet.ModelAndView
     * @description 跳转至商品详情页面
     */
    @RequestMapping("/toProduct")
    public ModelAndView toProduct(Model model,long userId, long productId,HttpServletRequest request) {
        Product product = productService.findProductById(productId);
        model.addAttribute("product", product);
        Collection collection = productService.findByCollectionUserIdAndProductId(userId,productId);
        model.addAttribute("collection",collection);
        footprintService.save(request,userId,productId);
        return new ModelAndView("productDetails");
    }
    /**
     * @author 阿杰
     * @param [model, userId, productId, request]
     * @return org.springframework.web.servlet.ModelAndView
     * @description 添加购物车
     */
    @RequestMapping("/addShoppingProduct")
    public  ModelAndView addShoppingProduct(long userId,long productId,HttpServletRequest request){
        productService.save(userId,productId,request);
        String url="redirect:/ProductListController/toProduct?productId="+productId+"&userId="+userId;
        return new ModelAndView(url);
    }
    /**
     * @author 阿杰
     * @param [model, userId, productId, request]
     * @return org.springframework.web.servlet.ModelAndView
     * @description 添加收藏
     */
    @RequestMapping("/addCollectionProduct")
    public  ModelAndView addCollectionProduct(long userId,long productId,HttpServletRequest request){
        productService.saveCollection(userId,productId,request);
        String url="redirect:/ProductListController/toProduct?productId="+productId+"&userId="+userId;
        return new ModelAndView(url);
    }
    /**
     * @author 阿杰
     * @param [model, userId]
     * @return org.springframework.web.servlet.ModelAndView
     * @description 跳转至购物车
     */
    @RequestMapping("/shoppingCart")
    public ModelAndView toShoppingCart(Model model,long userId){
        List<ShoppingCart> shoppingCarts = productService.findByUserIdCart(userId);
        model.addAttribute("shoppingCarts",shoppingCarts);
        return new ModelAndView("shoppingCart");
    }
    /**
     * @author 阿杰
     * @param [shoppingCart_id, userId, productId, request]
     * @return org.springframework.web.servlet.ModelAndView
     * @description 购物车移至收藏
     */
    @RequestMapping("/moveToCollectionProduct")
    public  ModelAndView moveToCollectionProduct(long shoppingCartId,long userId,long productId,HttpServletRequest request){
        productService.moveToCollectionProduct(shoppingCartId,userId,productId,request);
        String url="redirect:/ProductListController/shoppingCart?userId="+userId;
        return new ModelAndView(url);
    }
    /**
     * @author 阿杰
     * @param [shoppingCartId, userId]
     * @return org.springframework.web.servlet.ModelAndView
     * @description 删除购物车
     */
    @RequestMapping("/deleteShoppingProduct")
    public  ModelAndView deleteShoppingProduct(long shoppingCartId,long userId){
        productService.deleteShoppingCart(shoppingCartId);
        String url="redirect:/ProductListController/shoppingCart?userId="+userId;
        return new ModelAndView(url);
    }
    /**
     * @author 阿杰
     * @param [shoppingCartId]
     * @return org.springframework.web.servlet.ModelAndView
     * @description 批量删除购物车
     */
    @RequestMapping("/deleteShoppingProducts")
    public ModelAndView deleteShoppingProducts(@RequestParam(value = "shoppingCartId",defaultValue = "435,438") String shoppingCartId,long userId){
        productService.deleteShoppingCarts(shoppingCartId);
        String url="redirect:/ProductListController/shoppingCart?userId="+userId;
        return new ModelAndView(url);
    }
    /**
     * @author 阿杰
     * @param [shopping_cart_id, shopping_cart_count]
     * @return java.lang.String
     * @description 修改购物车数量
     */
    @RequestMapping("/changeShoppingCarCount")
    public String changeShoppingCarCount(long shopping_cart_id,int shopping_cart_count){
        productService.changeShoppingCarCount(shopping_cart_id,shopping_cart_count);
        return "成功";
    }
    /**
     * @author 阿杰
     * @param [model, userId, collection_id]
     * @return org.springframework.web.servlet.ModelAndView
     * @description 跳转至用户的收藏
     */
    @RequestMapping("/collectionProduct")
    public ModelAndView toCollection(Model model,long userId){
        List<Collection> collections = productService.findByUserIdCollection(userId);
        model.addAttribute("collections",collections);
        return new ModelAndView("collection");
    }
    /**
     * @author 阿杰
     * @param [collection_id, userId]
     * @return org.springframework.web.servlet.ModelAndView
     * @description 删除收藏
     */
    @RequestMapping("/deleteCollectionProduct")
    public ModelAndView deleteCollectionProduct(long collectionId,long userId){
        productService.deleteCollection(collectionId);
        String url="redirect:/ProductListController/collectionProduct?userId="+userId;
        return new ModelAndView(url);
    }
    /**
     * @author 阿杰
     * @param [collectionId]
     * @return org.springframework.web.servlet.ModelAndView
     * @description 批量删除收藏
     */
    @RequestMapping("/deleteCollectionProducts")
    public ModelAndView deleteCollectionProducts(@RequestParam(value = "collectionId",defaultValue = "439,449") String collectionId,long userId){
        productService.deleteCollections(collectionId);
        String url="redirect:/ProductListController/collectionProduct?userId="+userId;
        return new ModelAndView(url);
    }
    /**
     * @author 阿杰
     * @param [model, product_keywords, userId]
     * @return org.springframework.web.servlet.ModelAndView
     * @description 搜索收藏商品
     */
    @RequestMapping("/findCollectionProducts")
    public ModelAndView findCollectionProducts(Model model,String product_keywords,long userId){
        List<Collection> collections = productService.findCollections(product_keywords,userId);
        model.addAttribute("collections",collections);
        System.out.println(collections);
        String url="redirect:/ProductListController/collectionProduct?userId="+userId;
        return new ModelAndView(url);
    }
    /**
     * @author 阿杰
     * @param [request, response]
     * @return void
     * @description 搜索提示
     */
    @RequestMapping("/searchWord")
    public void searchWord(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String product_keywords = request.getParameter("product_keywords");
        //product_keywords = "2";
        List<Product> products = productService.findProductByWord(product_keywords);

        List<Object> productKeywordList = new ArrayList<Object>();
        for (int i = 0; i < products.size(); i++) {
            productKeywordList.add(products.get(i).getProduct_keywords());
        }
        Gson gson = new Gson();
        String json = gson.toJson(productKeywordList);
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(json);
    }
    /**
     * @author 阿杰
     * @param [model, product_keywords, page, limit]
     * @return org.springframework.web.servlet.ModelAndView
     * @description 前台模糊搜索商品
     */
    @RequestMapping("/searchProductList")
    public ModelAndView searchProductList(Model model, String product_keywords, @RequestParam(value = "page",defaultValue = "0") int page,
                                          @RequestParam(value = "limit",defaultValue = "12") int limit){
        if(page != 0) page--;
        Page pages =  productService.getProductListPage(product_keywords,page,limit);
        model.addAttribute("TotalPages", pages.getTotalPages());//查询的页数
        model.addAttribute("Number", pages.getNumber()+1);//查询的当前第几页
        List<Product> products = pages.getContent();
        String img;
        for(int i = 0; i < products.size(); i++) {
            if (products.get(i).getProduct_picture().contains(",")){
                img = products.get(i).getProduct_picture();
                img = img.substring(0,img.indexOf(","));
                System.out.println("前台模糊搜索商品");
                products.get(i).setProduct_picture(img);
            }
        }
        model.addAttribute("products", products);//查询的当前页的集合
        model.addAttribute("product_keywords",product_keywords);
        //遍历一级二级分类
        List<ProductCategory> productOneCategories = productService.findProductOneCategoryList();
        model.addAttribute("productOneCategories", productOneCategories);
        List<ProductCategory> productTwoCategories = productService.findProductTwoCategoryList();
        model.addAttribute("productTwoCategories", productTwoCategories);
        return new ModelAndView("productList");
    }

    //积分商品展示
    @RequestMapping("/integralProduct")
    public ModelAndView findByProduct_isRedeemable(Model model) {
        List<Product> products = productService.findByProduct_isRedeemable();
        model.addAttribute("products", products);
        return new ModelAndView("integralProductList");
    }

}
