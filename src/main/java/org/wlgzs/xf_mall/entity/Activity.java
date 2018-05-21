package org.wlgzs.xf_mall.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * @Auther: 阿杰
 * @Date: 2018/5/10 08:54
 * @Description: 活动结束时间 大图片
 */
@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long activitySumId;   //活动id
    @Column(nullable = false,length = 30)
    private String activity_name;   //活动名称
    @Column(nullable = false)
    private float activity_discount;   //活动折扣
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date activity_time;   //活动结束时间
    @Column(nullable = false,length = 100)
    private String activity_picture;   //活动图片
}
