package org.wlgzs.xf_mall.service;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import org.wlgzs.xf_mall.entity.Collection;
import org.wlgzs.xf_mall.entity.Product;
import org.wlgzs.xf_mall.entity.ProductCategory;
import org.wlgzs.xf_mall.entity.ShoppingCart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Auther: 阿杰
 * @Date: 2018/4/18 20:58
 * @Description:
 */
public interface ProductService {

    Page<Product> getProductListPage(String product_keywords, int page, int limit);

    List<Product> getProductList();

    void saveProduct(String product_details,MultipartFile[] myFileNames, HttpSession session, HttpServletRequest request);

    String[] uploadImg(MultipartFile myFileName, HttpSession session, HttpServletRequest request);

    String edit(long productId, String product_details, MultipartFile[] myFileNames, HttpSession session,
              HttpServletRequest request);

    String delete(long productId, HttpServletRequest request);

    Product findProductById(long productId);

    List<Product> findProductListById(long productId);

    List<ShoppingCart> findAllByIds(long[] Ids);

    Page getProductCategoryList(String category_name, int page, int limit);

    List<ProductCategory> findProductOneCategoryList();

    List<ProductCategory> findProductTwoCategoryList();

    List<ProductCategory> findProductByOneCategory(String category);

    List<Product> productByOneCategory(String category_name);

    Page<Product> findProductByTwoCategory(String product_category, int page, int limit);

    List<Product> findProductByCategory(String product_category);

    void save(ProductCategory productCategory);

    void saveOne(ProductCategory productCategory, MultipartFile myFileName, HttpSession session, HttpServletRequest request);

    void editCategory(ProductCategory productCategory, MultipartFile myFileName, HttpSession session, HttpServletRequest request);

    String deleteCategory(long categoryId);

    ProductCategory findProductCategoryById(long categoryId);

    void save(long userId,long productId,int shoppingCart_count,HttpServletRequest request);

    ShoppingCart findByUserIdAndProductId(long userId, long productId);

    void saveCollection(long userId,long productId,HttpServletRequest request);

    Collection findByCollectionUserIdAndProductId(long userId, long productId);

    List<ShoppingCart> findByUserIdCart(long userId);

    void moveToCollectionProduct(long shoppingCarId,long userId,long productId,HttpServletRequest request);

    void deleteShoppingCart(long shoppingCartId);

    void deleteShoppingCarts(String shoppingCartIds);

    void changeShoppingCarCount(long shopping_cart_id,int shopping_cart_count);

    List<Collection> findByUserIdCollection(long userId);

    void deleteCollection(long collectionId);

    void deleteCollections(String collectionId);

    List<Collection> findCollections(String product_keywords, long userId);

    List<ProductCategory> findProductByWord(String category_name);

    List<Product> findByProduct_isRedeemable();

    List<Product> recommendedByUserId(long userId);
}
