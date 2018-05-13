package org.wlgzs.xf_mall.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @Auther: 阿杰
 * @Date: 2018/4/15 08:20
 * @Description: 搜索屏蔽
 */
@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class SearchShield {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long searchShieldId; //搜索屏蔽id
    @Column(nullable = false,length = 20)
    private String searchShield_Sensitive; //敏感词

}
