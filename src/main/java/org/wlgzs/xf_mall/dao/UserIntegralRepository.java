package org.wlgzs.xf_mall.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.wlgzs.xf_mall.entity.UserIntegral;

import java.util.Date;
import java.util.List;

/**
 * @Auther: 李晓珊
 * @Date: 2018/4/23 08:42
 * @Description:
 */
public interface UserIntegralRepository extends JpaRepository<UserIntegral,Long>,JpaSpecificationExecutor<UserIntegral> {
    UserIntegral findById(long id);

    //通过用户id查询用户积分
    @Query(value = "SELECT * FROM user_integral WHERE user_id = ?",nativeQuery = true)
    List<UserIntegral> findByUserId(@Param("user_id") long id);

    //遍历积分收入
    @Query(value = "SELECT * FROM user_integral WHERE user_integral_vary > 0 and user_id = ?",nativeQuery = true)
    List<UserIntegral>  findByIncome(@Param("user_id") long userId);

    //遍历积分支出
    @Query(value = "SELECT * FROM user_integral WHERE user_integral_vary < 0 and user_id = ?",nativeQuery = true)
    List<UserIntegral>  findByExpend(@Param("user_id") long userId);

    //查询一条积分记录
    @Query(value = "SELECT * FROM user_integral WHERE user_id = ? and order_purchase_time = ?",nativeQuery = true)
    UserIntegral findByUserIdAndProductId(@Param("user_id") long userId,@Param("order_purchase_time") Date order_purchase_time);
}
