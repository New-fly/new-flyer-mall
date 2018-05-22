package org.wlgzs.xf_mall.service;

import org.springframework.data.domain.Page;
import org.wlgzs.xf_mall.entity.SearchShield;

import java.util.List;

/**
 * @author:胡亚星
 * @createTime 2018-04-20 16:30
 * @description:
 **/
public interface SearchShieldService {
    Page<SearchShield> getSearchShieldListPage(String search_keywords,int page,int limit);

    SearchShield findSearchShieldById(long searchShield);

    void save(SearchShield searchShield);

    void delete(long searchShield);

    boolean querySensitive(String searchShield_Sensitive);
//    List<SearchShield> findBySearchShieldSensitive(String search_shield_sensitive);
}
