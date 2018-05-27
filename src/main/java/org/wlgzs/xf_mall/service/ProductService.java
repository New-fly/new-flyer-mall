package org.wlgzs.xf_mall.service;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import org.wlgzs.xf_mall.entity.Collection;
import org.wlgzs.xf_mall.entity.Product;
import org.wlgzs.xf_mall.entity.ProductCategory;
import org.wlgzs.xf_mall.entity.ShoppingCart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @Auther: 阿杰
 * @Date: 2018/4/18 20:58
 * @Description:
 */
public interface ProductService {
    /**
     * @author 阿杰
     * @param [product_keywords, page, limit]
     * @return org.springframework.data.domain.Page<org.wlgzs.xf_mall.entity.Product>
     * @description 分页遍历商品  搜索商品
     */
    Page<Product> getProductListPage(String product_keywords, int page, int limit);
    /**
     * @author 阿杰
     * @param [product_details, myFileNames, session, request]
     * @return void
     * @description 添加商品
     */
    void saveProduct(String product_details,MultipartFile[] myFileNames, HttpSession session, HttpServletRequest request);
    /**
     * @author 阿杰
     * @param [myFileName, session, request]
     * @return java.lang.String[]
     * @description 富文本添加图片
     */
    String[] uploadImg(MultipartFile myFileName, HttpSession session, HttpServletRequest request);
    /**
     * @author 阿杰
     * @param [productId, product_details, myFileNames, session, request]
     * @return void
     * @description 修改商品
     */
    String edit(long productId, String product_details, MultipartFile[] myFileNames, HttpSession session,
              HttpServletRequest request);
    /**
     * @author 阿杰
     * @param [productId, request]
     * @return void
     * @description 删除商品
     */
    String delete(long productId, HttpServletRequest request);
    /**
     * @author 阿杰
     * @param [model, id]
     * @return org.springframework.web.servlet.ModelAndView
     * @description 通过id查找商品
     */
    Product findProductById(long productId);
    /**
     * @author 阿杰
     * @param [productId]
     * @return java.util.List<org.wlgzs.xf_mall.entity.Product>
     * @description 通过id查询商品  返回集合
     */
    List<Product> findProductListById(long productId);
    /**
     * @author 阿杰
     * @param [Ids]
     * @return java.util.List<org.wlgzs.xf_mall.entity.ShoppingCart>
     * @description 结算商品
     */
    List<ShoppingCart> findAllByIds(long[] Ids);
    /**
     * @author 阿杰
     * @param [category_name, page, limit]
     * @return org.springframework.data.domain.Page
     * @description 遍历所有分类  搜索分类
     */
    Page getProductCategoryList(String category_name, int page, int limit);
    /**
     * @author 阿杰
     * @param []
     * @return java.util.List<org.wlgzs.xf_mall.entity.ProductCategory>
     * @description 遍历一级分类  不分页
     */
    List<ProductCategory> findProductOneCategoryList();
    /**
     * @author 阿杰
     * @param []
     * @return java.util.List<org.wlgzs.xf_mall.entity.ProductCategory>
     * @description 遍历二级分类  不分页
     */
    List<ProductCategory> findProductTwoCategoryList();
    /**
     * @author 阿杰
     * @param [category]
     * @return java.util.List<org.wlgzs.xf_mall.entity.ProductCategory>
     * @description 通过一级分类查找分类名
     */
    List<ProductCategory> findProductByOneCategory(String category);
    /**
     * @author 阿杰
     * @param [category_name]
     * @return java.util.List<org.wlgzs.xf_mall.entity.Product>
     * @description 通过二级分类查找商品
     */
    List<Product> productByOneCategory(String category_name);
    /**
     * @author 阿杰
     * @param [product_category, page, limit]
     * @return org.springframework.data.domain.Page<org.wlgzs.xf_mall.entity.Product>
     * @description 通过二级分类查找商品  分页
     */
    Page<Product> findProductByTwoCategory(String product_category, int page, int limit);
    /**
     * @author 阿杰
     * @param [product_category]
     * @return java.util.List<org.wlgzs.xf_mall.entity.Product>
     * @description 通过二级分类查找商品  不分页
     */
    List<Product> findProductByCategory(String product_category);
    /**
     * @author 阿杰
     * @param [productCategory]
     * @return void
     * @description 增加二级分类
     */
    void save(ProductCategory productCategory);
    /**
     * @author 阿杰
     * @param [productCategory]
     * @return void
     * @description 增加二级分类配件
     */
    void saveTwo(ProductCategory productCategory);
    /**
     * @author 阿杰
     * @param [productCategory, myFileName, session, request]
     * @return void
     * @description 增加一级分类
     */
    void saveOne(ProductCategory productCategory, MultipartFile myFileName, HttpSession session, HttpServletRequest request);
    /**
     * @author 阿杰
     * @param [productCategory, myFileName, session, request]
     * @return void
     * @description 修改分类
     */
    void editCategory(long categoryId, MultipartFile myFileName, HttpSession session, HttpServletRequest request);
    /**
     * @author 阿杰
     * @param [categoryId]
     * @return void
     * @description 删除分类
     */
    String deleteCategory(long categoryId);
    /**
     * @author 阿杰
     * @param [categoryId]
     * @return org.wlgzs.xf_mall.entity.ProductCategory
     * @description 按id查找类别
     */
    ProductCategory findProductCategoryById(long categoryId);
    /**
     * @author 阿杰
     * @param [userId, productId, shoppingCart_count, request]
     * @return void
     * @description 添加购物车
     */
    void save(long userId,long productId,int shoppingCart_count,HttpServletRequest request);
    /**
     * @author 阿杰
     * @param [userId, productId, request]
     * @return void
     * @description 添加收藏
     */
    void saveCollection(long userId,long productId,HttpServletRequest request);
    /**
     * @author 阿杰
     * @param [userId, productId]
     * @return org.wlgzs.xf_mall.entity.Collection
     * @description 查找用户收藏是否存在
     */
    Collection findByCollectionUserIdAndProductId(long userId, long productId);
    /**
     * @author 阿杰
     * @param [userId]
     * @return java.util.List<org.wlgzs.xf_mall.entity.ShoppingCart>
     * @description 用户的购物车
     */
    List<ShoppingCart> findByUserIdCart(long userId);
    /**
     * @author 阿杰
     * @param [shoppingCarId, userId, productId, request]
     * @return void
     * @description 购物车移至收藏
     */
    void moveToCollectionProduct(long shoppingCarId,long userId,long productId,HttpServletRequest request);
    /**
     * @author 阿杰
     * @param [shoppingCartId]
     * @return void
     * @description 删除购物车
     */
    void deleteShoppingCart(long shoppingCartId);
    /**
     * @author 阿杰
     * @param [shoppingCartIds]
     * @return void
     * @description 批量删除购物车
     */
    void deleteShoppingCarts(String shoppingCartIds);
    /**
     * @author 阿杰
     * @param [shopping_cart_id, shopping_cart_count]
     * @return void
     * @description 修改购物车数量
     */
    void changeShoppingCarCount(long shopping_cart_id,int shopping_cart_count);
    /**
     * @author 阿杰
     * @param [userId]
     * @return java.util.List<org.wlgzs.xf_mall.entity.Collection>
     * @description 用户的收藏
     */
    List<Collection> findByUserIdCollection(long userId);
    /**
     * @author 阿杰
     * @param [collectionId]
     * @return void
     * @description 删除收藏
     */
    void deleteCollection(long collectionId);
    /**
     * @author 阿杰
     * @param [collectionId]
     * @return void
     * @description 批量删除收藏
     */
    void deleteCollections(String collectionId);
    /**
     * @author 阿杰
     * @param [product_keywords, userId]
     * @return java.util.List<org.wlgzs.xf_mall.entity.Collection>
     * @description 搜索收藏商品
     */
    List<Collection> findCollections(String product_keywords, long userId);
    /**
     * @author 阿杰
     * @param [category_name]
     * @return java.util.List<org.wlgzs.xf_mall.entity.ProductCategory>
     * @description 搜索提示
     */
    List<ProductCategory> findProductByWord(String category_name);
    /**
     * @author 阿杰
     * @param []
     * @param product_isRedeemable
     * @param page
     * @param limit
     * @return java.util.List<org.wlgzs.xf_mall.entity.Product>
     * @description 积分商品展示
     */
    Page<Product> findByProduct_isRedeemable(int product_isRedeemable, int page, int limit);
    /**
     * @author 阿杰
     * @param [userId]
     * @return java.util.List<org.wlgzs.xf_mall.entity.Product>
     * @description 商品推荐
     */
    List<Product> recommendedByUserId(long userId);
    /**
     * @author 阿杰
     * @param [productOneCategories]
     * @return java.util.List<org.wlgzs.xf_mall.entity.Product>
     * @description 主页商品
     */
    List<Product> homeProductList(List<ProductCategory> productOneCategories);

    /**
     * @author 阿杰
     * @param []
     * @return void
     * @description 主页最新商品
     */
    List<Product> getProductList();

    //按价格查询
    Page<Product> findByPrice(String product_mallPrice,int page, int limit);

    Page<Product> searchProduct(HttpServletRequest request, String product_category, int page, int limit) throws IOException;
}
