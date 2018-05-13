package org.wlgzs.xf_mall.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.wlgzs.xf_mall.entity.User;

/**
 * @author:胡亚星
 * @createTime 2018-04-16 21:40
 * @description:
 **/
public interface LogUserRepository extends JpaRepository<User, Long>,JpaSpecificationExecutor<User> {

    @Query("FROM User u WHERE (u.user_name=?1 or u.user_phone=?1 or u.user_mail=?1) AND u.user_password=?2")
    User Login(String user_name, String user_password);

    @Query("FROM User u WHERE u.user_name=?1")
    User selectName(String user_name);

    @Query("FROM User u WHERE u.user_mail=?1")
    User selectEmail(String user_mail);
}
