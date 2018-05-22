package org.wlgzs.xf_mall.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.wlgzs.xf_mall.entity.ProductActivity;

import java.util.List;

/**
 * @Auther: 阿杰
 * @Date: 2018/5/2 00:24
 * @Description:
 */
public interface ProductActivityRepository extends JpaRepository<ProductActivity, Long>,JpaSpecificationExecutor<ProductActivity> {

    ProductActivity findById(long activityId);

    @Query(value = "SELECT * FROM product_activity WHERE activity_name = ? ",nativeQuery = true)
    List<ProductActivity> findByActivityName(@Param("activity_name") String activity_name);
}
