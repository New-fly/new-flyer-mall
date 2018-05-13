package org.wlgzs.xf_mall.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @Auther: 李晓珊
 * @Date: 2018/4/15 08:36
 * @Description:
 */
@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ProductActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long activityId;   //活动id
    private long productId;    //商品id
    @Column(nullable = false,length = 50)
    private String product_keywords;//商品关键字
    @Column(nullable = false)
    private float product_counterPrice;//专柜价格
    @Column(nullable = false)
    private float product_mallPrice;//商城价格
    @Column(nullable = false,length = 200)
    private String product_picture;//商品图片
    @Column(nullable = false,length = 30)
    private String activity_name;   //活动名称
    @Column(nullable = false)
    private int activity_discount;   //活动折扣
}
