package org.wlgzs.xf_mall.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.wlgzs.xf_mall.entity.Authorization;

/**
 * @author:胡亚星
 * @createTime 2018-05-11 21:56
 * @description:
 **/
public interface AuthorizationRepository extends JpaRepository<Authorization,Long>,JpaSpecificationExecutor<Authorization> {


}
