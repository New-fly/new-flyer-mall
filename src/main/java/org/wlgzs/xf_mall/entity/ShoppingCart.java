package org.wlgzs.xf_mall.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @Auther: 阿杰
 * @Date: 2018/4/15 08:01
 * @Description: 购物车
 */
@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long shoppingCartId;//购物车id
    private long productId;//商品id
    private long userId;//用户id
    @Column(nullable = false,length = 200)
    private String product_picture;//图片
    @Column(nullable = false,length = 50)
    private String product_keywords;//商品关键字
    @Column(nullable = false,length = 50)
    private String product_specification;//商品规格信息
    @Column(nullable = false)
    private float product_counterPrice;//专柜价
    @Column(nullable = false)
    private float product_mallPrice;//商城价
    @Column(nullable = false)
    private int shoppingCart_count;//购物车数量

}
