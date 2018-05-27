package org.wlgzs.xf_mall.dao;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.wlgzs.xf_mall.entity.Product;
import org.wlgzs.xf_mall.entity.ShoppingCart;

import java.util.List;

/**
 * @Auther: 阿杰
 * @Date: 2018/4/18 20:59
 * @Description:
 */
public interface ProductRepository extends JpaRepository<Product, Long>,JpaSpecificationExecutor<Product> {

    Product findById(long productId);

    @Query(value = "SELECT * FROM product WHERE product_id = ? ",nativeQuery = true)
    List<Product> findByIdReturnOne(long productId);

    @Query(value = "SELECT * FROM product WHERE product_category = ?",nativeQuery = true)
    List<Product> findByCategory(String product_category);

    @Query(value = "SELECT * FROM product WHERE product_keywords like %?% limit 0,8",nativeQuery = true)
    List<Product> findProductByWord(String product_keywords);

    @Query(value = "SELECT * FROM product WHERE product_is_redeemable = 1 ",nativeQuery = true)
    List<Product> findByProduct_isRedeemable();

    @Query("SELECT o FROM Product o WHERE o.product_category in :product_categories")
    List<Product> findProductByTwoCategory(@Param(value = "product_categories") String [] product_categories);

    @Query("SELECT o FROM Product o WHERE o.productId in :productIds")
    List<Product> findProductByProductId(@Param(value = "productIds") long [] productIds);

    @Query("SELECT o FROM Product o ")
    List<Product> getProductList(Sort sort);

<<<<<<< HEAD
    @Query("SELECT o FROM Product o WHERE o.product_mallPrice BETWEEN ?1 AND ?2")
    List<Product> findByPrice(float minPrice,float maxPrice);

    @Query("SELECT o FROM Product o WHERE o.product_mallPrice < ?1")
    List<Product> findByMinPrice(float minPrice);

    @Query("SELECT o FROM Product o WHERE o.product_mallPrice > ?1")
    List<Product> findByMaxPrice(float maxPrice);
=======
    @Query("SELECT o FROM Product o WHERE o.product_keywords like %:product_keywords%")
    List<Product> findProductByProductKeywords(@Param(value = "product_keywords") String product_keywords);

>>>>>>> 9ef55565f75c32a1d518047f2702265b58705908
}
