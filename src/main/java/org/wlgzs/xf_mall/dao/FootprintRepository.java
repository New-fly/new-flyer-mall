package org.wlgzs.xf_mall.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.wlgzs.xf_mall.entity.Footprint;

import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2018/5/5 11:26
 * @Description:
 */
public interface FootprintRepository extends JpaRepository<Footprint, Long>,JpaSpecificationExecutor<Footprint> {

    @Query(value = "SELECT * FROM footprint WHERE user_id = ?",nativeQuery = true)
    List<Footprint> findByUserId(@Param("userId")long userId);

    @Query(value = "SELECT * FROM footprint WHERE user_id = ? and product_id = ?",nativeQuery = true)
    Footprint findByUserIdAndFootprintId(@Param("userId") long userId,@Param("productId") long productId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Footprint f WHERE f.footprintId in :Ids")
    void deleteByIds(@Param(value = "Ids") long [] Ids);

    @Query(value = "SELECT f FROM Footprint f WHERE f.product_keywords like %?1% AND f.userId=?2")
    List<Footprint> findFootprints(String product_keywords, long userId);
}
