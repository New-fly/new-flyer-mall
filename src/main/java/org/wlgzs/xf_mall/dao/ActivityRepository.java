package org.wlgzs.xf_mall.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.wlgzs.xf_mall.entity.Activity;

/**
 * @Auther: 阿杰
 * @Date: 2018/5/10 09:06
 * @Description:
 */
public interface ActivityRepository extends JpaRepository<Activity, Long>,JpaSpecificationExecutor<Activity> {
    Activity findById(long activitySumId);
}
