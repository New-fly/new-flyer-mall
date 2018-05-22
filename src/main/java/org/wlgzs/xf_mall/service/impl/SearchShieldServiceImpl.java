package org.wlgzs.xf_mall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.wlgzs.xf_mall.dao.SearchShieldRepository;
import org.wlgzs.xf_mall.entity.SearchShield;
import org.wlgzs.xf_mall.service.SearchShieldService;
import org.wlgzs.xf_mall.util.PageUtil;

import javax.swing.text.html.HTMLDocument;
import java.util.List;

/**
 * @author:胡亚星
 * @createTime 2018-04-20 16:43
 * @description:
 **/
@Service
public class SearchShieldServiceImpl implements SearchShieldService {

    @Autowired
    SearchShieldRepository searchShieldRepository;

    //遍历敏感词汇

    @Override
    public Page<SearchShield> getSearchShieldListPage(String search_keywords, int page, int limit) {
        Sort sort = new Sort(Sort.Direction.DESC,"searchShieldId");
        Pageable pageable = new PageRequest(page,limit,sort);
        Specification<SearchShield> specification = new PageUtil<SearchShield>(search_keywords).getPage("searchShield_Sensitive");
        Page pages = searchShieldRepository.findAll(specification,pageable);
        return pages;
    }

    //添加敏感词汇
    @Override
    public void save(SearchShield searchShield){
        searchShieldRepository.save(searchShield);
    }

    //删除敏感词汇
    @Override
    public void delete(long searchShield){
        SearchShield searchShield1 = searchShieldRepository.findById(searchShield);
        if(searchShield1 != null){
            searchShieldRepository.deleteById(searchShield);
        }
    }


    @Override
    public SearchShield findSearchShieldById(long searchShield){
        return searchShieldRepository.findById(searchShield);
    }

//    @Override
//    public List<SearchShield> findBySearchShieldSensitive(String search_shield_sensitive){
//        return searchShieldRepository.findBySearchShieldSensitive(search_shield_sensitive);
//    }


    @Override
    public boolean querySensitive(String searchShield_Sensitive) {
        SearchShield searchShield = searchShieldRepository.querySensitive(searchShield_Sensitive);
        if(searchShield != null){
            return false;
        }
        return true;
    }
}
