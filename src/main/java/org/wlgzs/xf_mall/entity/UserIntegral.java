package org.wlgzs.xf_mall.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * @Auther: 三三~~~
 * @Date: 2018/4/15 08:43
 * @Description:用户积分表
 * id  用户id  积分变化（正负）  商品关键字  图片
 */
@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class UserIntegral {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userIntegralId;   //积分id
    @Column(nullable = false)
    private long userId;   //用户id
    @Column(nullable = false)
    private int  userIntegral_vary;   //积分变化(正负)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date order_purchaseTime;//下单时间
    @Column(nullable = false,length = 50)
    private String product_keyword; //商品关键字
    @Column(nullable = false,length = 100)
    private String product_picture; //商品图片

}
