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

    List<ProductActivity> activityProductList();

    Page<ProductActivity> activityProductList(String activity_name, int page, int limit);

    void adminAddActivity(long productId, HttpServletRequest request);

    ProductActivity findByActivity(long activityId);

    void editActivity(long activityId, HttpServletRequest request);

    void deleteActivity(long activityId);
}
