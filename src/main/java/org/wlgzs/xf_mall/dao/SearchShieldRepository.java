package org.wlgzs.xf_mall.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.wlgzs.xf_mall.entity.SearchShield;

import java.util.List;

/**
 * @author:胡亚星
 * @createTime 2018-04-20 16:39
 * @description:
 **/
public interface SearchShieldRepository extends JpaRepository<SearchShield, Long>,JpaSpecificationExecutor<SearchShield> {
    SearchShield findById(long SearchShield);

    @Query(value = "SELECT * FROM search_shield WHERE search_shield_sensitive like %:search_shield_sensitive%", nativeQuery = true)
    List<SearchShield> findBySearchShieldSensitive(@Param("search_shield_sensitive") String search_shield_sensitive);

    @Query("FROM SearchShield s WHERE s.searchShield_Sensitive=?1")
    SearchShield querySensitive(String searchShield_Sensitive);
}
