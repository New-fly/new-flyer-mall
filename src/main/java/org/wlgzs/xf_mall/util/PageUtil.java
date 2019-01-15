package org.wlgzs.xf_mall.util;

import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.wlgzs.xf_mall.filter.DemoFilter;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;


public class PageUtil<T> {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(DemoFilter.class);

    private String searchKeywords;//模糊搜索关键字

    public PageUtil(String searchKeywords) {
        this.searchKeywords=searchKeywords;
    }

    public Specification<T> getPage(String...strings){
        return  new Specification<T>() {
            /**
            * root就是我们要查询的类型
            * query添加查询条件
            * criteriaBuilder构建Predicate
            * */
            @Override
            public Predicate toPredicate(Root<T> root,
                                         CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(searchKeywords==null){ //不模糊查询直接返回
                    return null;
                }
                if(searchKeywords.equals("")){ //不模糊查询直接返回
                    return null;
                }
                List<Predicate> predicates = new ArrayList<>();
                for (String s:strings){
                    Predicate _name;
                    Path<String> $name = root.get(s);
                    if(s.equals("order_number") || s.equals("user_name")){
                        logger.info("精确查询");
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
     * @description 批量模糊搜索
     */
    public Specification<T> getPages(String userId, String...strings){
        return (Specification<T>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Path<Long> pathId = root.get(userId);
            if(searchKeywords!=null&& !searchKeywords.equals("")) {
                for (String s : strings) {
                    Path<String> $name = root.get(s);
                    Predicate _name = criteriaBuilder.like($name, "%" + searchKeywords + "%");
                    predicates.add(_name);
                }
            } else {
                return criteriaBuilder.equal(pathId,userId);
            }
            Predicate predicate = criteriaBuilder.or(criteriaBuilder.or(predicates
                    .toArray(new Predicate[] {})));
            return criteriaBuilder.and(predicate,criteriaBuilder.equal(pathId,userId));
        };
    }
}
