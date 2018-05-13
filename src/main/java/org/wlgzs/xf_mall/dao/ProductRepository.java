package org.wlgzs.xf_mall.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.wlgzs.xf_mall.entity.Product;

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

    @Query(value = "SELECT p FROM Product p WHERE p.product_keywords like %?1%")
    List<Product> findByProductKeywords(String product_keywords);

    @Query(value = "SELECT * FROM product WHERE product_category = ?",nativeQuery = true)
    List<Product> findByCategory(String product_category);

    @Query(value = "SELECT * FROM product WHERE product_keywords like %?% limit 0,8",nativeQuery = true)
    List<Product> findProductByWord(String product_keywords);

    @Query(value = "SELECT * FROM product WHERE product_is_redeemable = 1 ",nativeQuery = true)
    List<Product> findByProduct_isRedeemable();

}
