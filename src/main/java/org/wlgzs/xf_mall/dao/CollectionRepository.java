package org.wlgzs.xf_mall.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.wlgzs.xf_mall.entity.Collection;

import java.util.List;

/**
 * @Auther: 阿杰
 * @Date: 2018/4/25 10:55
 * @Description:
 */
public interface CollectionRepository extends JpaRepository<Collection, Long>,JpaSpecificationExecutor<Collection> {

    @Query(value = "SELECT o FROM Collection o WHERE o.userId=?1")
    List<Collection> findByUserIdCollection(long userId);

    @Query(value = "SELECT o FROM Collection o WHERE o.userId=?1 AND o.productId=?2")
    Collection findByCollectionUserIdAndProductId(long userId, long productId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Collection o WHERE o.collectionId in :Ids")
    void deleteByIds(@Param(value = "Ids") long [] Ids);

    @Query(value = "SELECT o FROM Collection o WHERE o.product_keywords like %?1% AND o.userId=?2")
    List<Collection> findCollections(String product_keywords,long userId);
}
