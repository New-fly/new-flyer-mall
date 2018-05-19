package org.wlgzs.xf_mall.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.wlgzs.xf_mall.entity.ProductCategory;

import java.util.List;

/**
 * @Auther: 阿杰
 * @Date: 2018/4/20 13:06
 * @Description:
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long>,JpaSpecificationExecutor<ProductCategory> {

    ProductCategory findById(long id);

    @Query(value = "SELECT o FROM ProductCategory o WHERE o.parent_name='0'")
    List<ProductCategory> findOneAll();

    @Query(value = "SELECT * FROM product_category WHERE parent_name != '0'",nativeQuery = true)
    List<ProductCategory> findTwoAll();

    @Query(value = "SELECT o FROM ProductCategory o WHERE o.parent_name = ?1")
    List<ProductCategory> findByCategoryParentName(String parent_name);

    @Query(value = "SELECT o FROM ProductCategory o WHERE o.category_name like %?1%")
    List<ProductCategory> findByCategoryName(String category_name);

    @Query(value = "SELECT * FROM product_category WHERE category_name like %?% limit 0,8",nativeQuery = true)
    List<ProductCategory> findProductByWord(String category_name);

    @Transactional
    @Modifying
    @Query("DELETE FROM ProductCategory p WHERE p.categoryId in :Ids")
    void deleteByIds(@Param(value = "Ids") long [] Ids);

    @Query("SELECT p FROM ProductCategory p WHERE p.category_name in :category_names")
    List<ProductCategory> findOneCategoryByCategoryName(@Param(value = "category_names") String [] category_names);

    @Query("SELECT p FROM ProductCategory p WHERE p.parent_name in :parent_names")
    List<ProductCategory> findCategoryByParentName(@Param(value = "parent_names") String [] parent_names);
}
