package org.wlgzs.xf_mall.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @Auther: 李晓珊
 * @Date: 2018/4/15 08:38
 * @Description: 评价
 */
@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ProductEstimate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long estimateId;  //评价id
    private long productId;   //商品id
    private long userId;  //用户id
    @Column(nullable = false,length = 30)
    private String user_name;  //用户名
    @Column(nullable = false)
    private int estimate_isNameless;  //是否匿名
    @Column(nullable = false,length = 50)
    private String product_specification;  //商品规格信息
    private Date estimate_time;   //评价时间
    private int estimate_grade;  //评价等级
    @Column(nullable = false,length = 200)
    private String estimate_content;   //评价内容
    @Column(nullable = false,length = 200)
    private String estimate_img;   //买家秀
    private long orderId;
    @Column(nullable = false,length = 200)
    private String product_picture;//商品图片
    @Column(nullable = false)
    private float product_mallPrice;//商城价格
    @Column(nullable = false,length = 50)
    private String product_keywords;//商品关键字
}

