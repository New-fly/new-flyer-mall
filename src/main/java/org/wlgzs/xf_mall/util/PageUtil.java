package org.wlgzs.xf_mall.util;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;


public class PageUtil<T> {

    private String searchKeywords;//模糊搜索关键字

    public PageUtil(String searchKeywords) {
        this.searchKeywords=searchKeywords;
    }

    /**
     * @author 阿杰
     * @param [strings]
     * @return org.springframework.data.jpa.domain.Specification<T>
     * @description
     */
    public Specification<T> getPage(String...strings){
        return  new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root,
                                         CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(searchKeywords==null){ //不模糊查询直接返回
                    return null;
                }
                if(searchKeywords.equals("")){ //不模糊查询直接返回
                    return null;
                }
                List<Predicate> predicates = new ArrayList<Predicate>();
                for (String s:strings){
                    Predicate _name = null;
                    Path<String> $name = root.get(s);
                    if(s.equals("order_number") || s.equals("user_name")){
                        System.out.println("精确查询");
                        _name = criteriaBuilder.equal($name,searchKeywords);
                    }else{
                        _name = criteriaBuilder.like($name, "%" + searchKeywords + "%");
                    }
                    predicates.add(_name);
                }
                return criteriaBuilder.or(predicates
                        .toArray(new Predicate[] {}));
            }
        };
    }

    /**
     * @author 阿杰
     * @param [attribute, userId, strings]
     * @return org.springframework.data.jpa.domain.Specification<T>
     * @description
     *//*
    public Specification<T> getPages(String userId, String...strings){
        return  new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                Path<Integer> pathId = root.get(userId);
                if(searchKeywords!=null&&searchKeywords!="") { //没有查询条件
                    for (String s : strings) {
                        Path<String> $name = root.get(s);
                        Predicate _name = criteriaBuilder.like($name, "%" + searchKeywords + "%");
                        predicates.add(_name);
                    }
                } else {
                    return criteriaBuilder.equal(pathId,userId);//没有模糊查询
                }
                Predicate predicate = criteriaBuilder.or(criteriaBuilder.or(predicates
                        .toArray(new Predicate[] {})));
                return criteriaBuilder.and(predicate,criteriaBuilder.equal(pathId,userId));
            }
        };
    }*/
}
