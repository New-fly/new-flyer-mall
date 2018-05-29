package org.wlgzs.xf_mall.util;

import org.springframework.data.jpa.domain.Specification;
import org.wlgzs.xf_mall.entity.Product;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;


public class PageUtilTwo<Product> {
    /**
     * @author 阿杰
     * @param [strings]
     * @return org.springframework.data.jpa.domain.Specification<T>
     * @description 批量模糊分页（商品名）
     */
    public Specification<Product> getPage(String [] product_categories){
        return  new Specification<Product>() {
            /*
            * root就是我们要查询的类型
            * query添加查询条件
            * criteriaBuilder构建Predicate
            * */
            @Override
            public Predicate toPredicate(Root<Product> root,
                                         CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                for (String s:product_categories){
                    Path<String> $name = root.get("product_keywords");
                    Predicate _name = criteriaBuilder.like($name, "%" + s + "%");
                    predicates.add(_name);
                }
                return criteriaBuilder.or(predicates
                        .toArray(new Predicate[] {}));
            }
        };
    }

}
