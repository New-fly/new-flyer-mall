package org.wlgzs.xf_mall.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author:胡亚星
 * @createTime 2018-05-11 21:23
 * @description:
 **/
@Entity
@Data
public class Authorization {
    @Id
    @GeneratedValue()
    private long authorizationId;//授权id
    private long userId;//用户id
    private long githubId;//githubId
}
