package org.wlgzs.xf_mall.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.wlgzs.xf_mall.entity.Authorization;

/**
 * @author:胡亚星
 * @createTime 2018-05-11 21:56
 * @description:
 **/
public interface AuthorizationRepository extends JpaRepository<Authorization,Long>,JpaSpecificationExecutor<Authorization> {

    @Query(value = "select a from Authorization a where a.githubId=?1")
    Authorization isBinding(long githubId);

}
