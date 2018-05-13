package org.wlgzs.xf_mall.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author:胡亚星
 * @createTime 2018-04-14 17:47
 * @description:用户表
 **/
@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class User {
    @Id
    @GeneratedValue()
    private long userId;//用户id
    @Column(length = 20)
    private String user_avatar;//用户头像
    @Column(nullable = false,length = 30)
    private String user_name;//用户名
    @Column(length = 10)
    private String user_role;//角色
    @Column(nullable = false,length = 11)
    private String user_phone;//电话
    @Column(nullable = false,length = 30)
    private String user_password;//密码
    @Column(nullable = false,length = 20)
    private String user_mail;//邮箱
    private int userIntegral = 0;//用户总积分
}
