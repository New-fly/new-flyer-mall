package org.wlgzs.xf_mall.service;

import org.wlgzs.xf_mall.entity.UserIntegral;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Auther: 李晓珊
 * @Date: 2018/4/23 09:04
 * @Description:  用户积分管理
 */
public interface UserIntegralService {

    List<UserIntegral> getUserIntegral(long userId);

    List<UserIntegral> getUserIntegralIncome(long userId);

    List<UserIntegral> getUserIntegralExpend(long userId);

    UserIntegral findUserIntegralById(long id);

    void delete(Long userIntegralId);

    void save(long userId,HttpServletRequest request);
}
