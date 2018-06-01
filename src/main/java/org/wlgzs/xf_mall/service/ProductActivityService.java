package org.wlgzs.xf_mall.service;

import org.springframework.data.domain.Page;
import org.wlgzs.xf_mall.entity.ProductActivity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Auther: 阿杰
 * @Date: 2018/5/2 00:28
 * @Description: 活动商品
 */
public interface ProductActivityService {

    List<ProductActivity> activityProductList(String activity_name);

    Page<ProductActivity> activityProductList(String product_keywords, int page, int limit);

    void adminAddActivity(long productId, HttpServletRequest request);

    ProductActivity findByActivity(long activityId);

    void editActivity(long activityId, HttpServletRequest request);

    void deleteActivity(long activityId);

    //批量添加活动商品
    void adminAddActivitys(String productId,HttpServletRequest request);

    //批量删除商品
    void adminDeleteActivitys(String activityId);
}
