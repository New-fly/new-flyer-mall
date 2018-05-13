package org.wlgzs.xf_mall.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.wlgzs.xf_mall.entity.ProductEstimate;

import java.util.List;

/**
 * @Auther: 李晓珊
 * @Date: 2018/4/23 13:14
 * @Description:
 */
public interface ProductEstimateRepository extends JpaRepository<ProductEstimate,Long>,JpaSpecificationExecutor<ProductEstimate> {
    ProductEstimate findById(long estimateId);

    @Query(value = "SELECT * FROM product_estimate WHERE user_id = ?",nativeQuery = true)
    List<ProductEstimate> findByUserId(@Param("userId") long userId);

    @Query(value = "SELECT * FROM product_estimate WHERE product_id = ?",nativeQuery = true)
    List<ProductEstimate> findByProductId(long productId);
}
