package org.wlgzs.xf_mall.controller.admin;

import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.wlgzs.xf_mall.entity.Product;
import org.wlgzs.xf_mall.entity.ProductCategory;
import org.wlgzs.xf_mall.entity.Result;
import org.wlgzs.xf_mall.service.ProductService;
import org.wlgzs.xf_mall.util.ResultUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * @Auther: 阿杰
 * @Date: 2018/4/18 20:51
 * @Description: 后台商品管理
 */
@RequestMapping("AdminProductController")
@RestController
public class ProductController {
    @Resource
    ProductService productService;
    /**
     * @author 阿杰
     * @param [model]
     * @return java.lang.String
     * @description 后台商品列表
     */
    @RequestMapping(value = "/adminProductList")
    public ModelAndView list(Model model, @RequestParam(value = "page",defaultValue = "0") int page,
                             @RequestParam(value = "limit",defaultValue = "10") int limit) {
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
                products.get(i).setProduct_picture(img);
            }
        }
        model.addAttribute("products", products);//查询的当前页的集合
        System.out.println(products);
        return new ModelAndView("admin/adminProductList");
    }
    /**
     * @author 阿杰
     * @param [model, product_keywords]
     * @return java.lang.String
     * @description 搜索商品
     */
    @RequestMapping("/adminFindProduct")
    public  ModelAndView findProduct(Model model, String product_keywords, @RequestParam(value = "page",defaultValue = "0") int page,
                                     @RequestParam(value = "limit",defaultValue = "10") int limit){
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
                System.out.println("  ");
                products.get(i).setProduct_picture(img);
            }
        }
        model.addAttribute("products", products);//查询的当前页的集合
        model.addAttribute("product_keywords",product_keywords);
        return new ModelAndView("admin/adminProductList");
    }
    /**
     * @author 阿杰
     * @return java.lang.String
     * @description 跳转到添加商品
     */
    @RequestMapping(value = "/toAdminAddProduct")
    public ModelAndView toAdd(Model model){
        List<ProductCategory> productCategories = productService.findProductTwoCategoryList();
        model.addAttribute("productCategories",productCategories);
        return new ModelAndView("admin/adminAddProduct");
    }
    /**
     * @author 阿杰
     * @param [myFileName, session, request]
     * @return org.wlgzs.xf_mall.entity.Result
     * @description 富文本图片上传
     */
    @RequestMapping("/upload")
    public Result uploadImg(MultipartFile myFileName, HttpSession session, HttpServletRequest request) {
        String[] str = productService.uploadImg(myFileName,session,request);
        return ResultUtil.success(str);
    }
    /**
     * @author 阿杰
     * @param [product]
     * @return java.lang.String
     * @description 添加商品
     */
    @RequestMapping("/adminAddProduct")
    public ModelAndView add(String product_details, @RequestParam("file") MultipartFile[] myFileNames, HttpSession session,
                            Model model, HttpServletRequest request){
        productService.saveProduct(product_details,myFileNames,session,request);
        model.addAttribute("product_details",product_details);
        return new ModelAndView("redirect:/AdminProductController/adminProductList");
    }
    /**
     * @author 阿杰
     * @param [model, id]
     * @return java.lang.String
     * @description 跳转至修改商品页面
     */
    @RequestMapping("/toAdminEditProduct")
    public ModelAndView toEdit(Model model, long productId) {
        Product product=productService.findProductById(productId);
        model.addAttribute("product", product);
        return new ModelAndView("admin/adminEditProduct");
    }
    /**
     * @author 阿杰
     * @param [product]
     * @return java.lang.String
     * @description 修改商品
     */
    @RequestMapping("/adminEditProduct")
    public ModelAndView edit(Product product,String product_details, @RequestParam("file") MultipartFile[] myFileNames, HttpSession session,
                             HttpServletRequest request) {
        productService.edit(product,product_details,myFileNames,session,request);
        return new ModelAndView("redirect:/AdminProductController/adminProductList");
    }
    /**
     * @author 阿杰
     * @param [id]
     * @return java.lang.String
     * @description 删除商品
     */
    @RequestMapping("/adminDeleteProduct")
    public ModelAndView delete(long productId) {
        productService.delete(productId);
        return new ModelAndView("redirect:/AdminProductController/adminProductList");
    }
    /**
     * @author 阿杰
     * @param [model]
     * @return java.lang.String
     * @description 遍历所有分类
     */
    @RequestMapping("/productCategoryList")
    public ModelAndView category(Model model, @RequestParam(value = "page",defaultValue = "0") int page,
                                 @RequestParam(value = "limit",defaultValue = "10") int limit){
        String category_name ="";
        if(page != 0) page--;
        Page pages =  productService.getProductCategoryList(category_name,page,limit);
        model.addAttribute("TotalPages", pages.getTotalPages());//查询的页数
        model.addAttribute("Number", pages.getNumber()+1);//查询的当前第几页
        List<ProductCategory> productCategories = pages.getContent();
        model.addAttribute("productCategories", productCategories);//查询的当前页的集合
        return new ModelAndView("admin/productCategorylists");
    }
    /**
     * @author 阿杰
     * @param []
     * @return java.lang.String
     * @description 跳转至添加一级分类页面
     */
    @RequestMapping("/toAddProductOneCategory")
    public ModelAndView toAddOneCategory(){
        return new ModelAndView("admin/addProductOneCategory");
    }
    /**
     * @author 阿杰
     * @param [productCategory]
     * @return java.lang.String
     * @description 添加一级分类
     */
    @RequestMapping("/addProductOneCategory")
    public ModelAndView addOneCategory(ProductCategory productCategory){
        productService.saveOne(productCategory);
        return new ModelAndView("redirect:/AdminProductController/productCategoryList");
    }
    /**
     * @author 阿杰
     * @param []
     * @return java.lang.String
     * @description 跳转至添加二级分类页面
     */
    @RequestMapping("/toAddProductCategory")
    public ModelAndView toAddCategory(Model model){
        List<ProductCategory> productCategories = productService.findProductOneCategoryList();
        model.addAttribute("productCategories",productCategories);
        return new ModelAndView("admin/addProductCategory");
    }
    /**
     * @author 阿杰
     * @param [productCategory]
     * @return java.lang.String
     * @description 添加二级分类
     */
    @RequestMapping("/addProductCategory")
    public ModelAndView addCategory(ProductCategory productCategory){
        productService.save(productCategory);
        return new ModelAndView("redirect:/AdminProductController/productCategoryList");
    }
    /**
     * @author 阿杰
     * @param [id]
     * @return java.lang.String
     * @description 删除分类
     */
    @RequestMapping("/deleteProductCategory")
    public ModelAndView deleteCategory(Long categoryId) {
        productService.deleteCategory(categoryId);
        return new ModelAndView("redirect:/AdminProductController/productCategoryList");
    }
    /**
     * @author 阿杰
     * @param [model, id]
     * @return java.lang.String
     * @description 跳转至修改分类页面
     */
    @RequestMapping("/toAdminEditProductCategory")
    public ModelAndView toEditCategory(Model model, long categoryId) {
        List<ProductCategory> productCategories = productService.findProductOneCategoryList();
        model.addAttribute("productCategories",productCategories);
        ProductCategory productCategory = productService.findProductCategoryById(categoryId);
        model.addAttribute("productCategory", productCategory);
        return new ModelAndView("admin/adminEditProductCategory");
    }
    /**
     * @author 阿杰
     * @param [productCategory]
     * @return java.lang.String
     * @description 修改分类
     */
    @RequestMapping("/adminEditProductCategory")
    public ModelAndView editCategory(ProductCategory productCategory) {
        productService.editCategory(productCategory);
        return new ModelAndView("redirect:/AdminProductController/productCategoryList");
    }
    /**
     * @author 阿杰
     * @param [model, category_name]
     * @return java.lang.String
     * @description 搜索分类
     */
    @RequestMapping("/findProductCategory")
    public  ModelAndView findCategory(Model model,String category_name, @RequestParam(value = "page",defaultValue = "0") int page,
                                      @RequestParam(value = "limit",defaultValue = "10") int limit){
        if(page != 0) page--;
        Page pages =  productService.getProductCategoryList(category_name,page,limit);
        model.addAttribute("TotalPages", pages.getTotalPages());//查询的页数
        model.addAttribute("Number", pages.getNumber()+1);//查询的当前第几页
        List<ProductCategory> productCategories = pages.getContent();
        model.addAttribute("productCategories", productCategories);//查询的当前页的集合
        model.addAttribute("category_name",category_name);
        return new ModelAndView("admin/productCategorylists");
    }

}
