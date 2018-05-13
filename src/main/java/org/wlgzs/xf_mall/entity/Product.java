package org.wlgzs.xf_mall.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author:胡亚星
 * @createTime 2018-04-14 16:43
 * @description:商品表
 **/
@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long productId;//商品id
    @Column(nullable = false)
    private int product_isShelf;//是否上架
    @Column(nullable = false,length = 50)
    private String product_keywords;//商品关键字
    @Column(nullable = false)
    private float product_counterPrice;//专柜价格
    @Column(nullable = false)
    private float product_mallPrice;//商城价格
    @Column(nullable = false,length = 50)
    private String product_specification;//商品规格信息
    @Column(nullable = false,length = 200)
    private String product_picture;//商品图片
    @Column(nullable = false,length = 100)
    private String product_details;//商品详情
    @Column(nullable = false,length = 100)
    private String product_serviceType;//服务类型
    @Column(nullable = false,length = 10)
    private String product_category;//商品类别
    @Column(nullable = false,length = 20)
    private String product_activity;//活动状态
    @Column(nullable = false)
    private int product_isTop;//是否热门
    @Column(nullable = false)
    private int product_isRedeemable;//是否可用积分兑换
    @Column(nullable = false)
    private int product_getPoints;//购买得到多少积分
    @Column(nullable = false)
    private int product_needPoints;//需要多少积分才能兑换
    @Column(nullable = false)
    private int product_inventory;//库存
}

