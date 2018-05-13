package org.wlgzs.xf_mall.service;

import org.springframework.web.multipart.MultipartFile;
import org.wlgzs.xf_mall.entity.ProductEstimate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2018/4/23 13:18
 * @Description: 商品评价
 */
public interface ProductEstimateService {

    void save(HttpServletRequest request, MultipartFile[] myFileNames, HttpSession session, long orderId);

    List<ProductEstimate> findEstimateById(long userId);

    List<ProductEstimate> findEstimateByproductId(long productId);

    void changeEstimate(long estimateId);
}
