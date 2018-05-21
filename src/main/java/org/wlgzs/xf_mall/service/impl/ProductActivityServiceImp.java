package org.wlgzs.xf_mall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.wlgzs.xf_mall.dao.ActivityRepository;
import org.wlgzs.xf_mall.dao.ProductActivityRepository;
import org.wlgzs.xf_mall.dao.ProductRepository;
import org.wlgzs.xf_mall.entity.Activity;
import org.wlgzs.xf_mall.entity.Product;
import org.wlgzs.xf_mall.entity.ProductActivity;
import org.wlgzs.xf_mall.service.ProductActivityService;
import org.wlgzs.xf_mall.util.PageUtil;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 阿杰
 * @Date: 2018/5/2 00:28
 * @Description: 活动商品
 */
@Service
public class ProductActivityServiceImp implements ProductActivityService {
    @Autowired
    private ProductActivityRepository productActivityRepository;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private ProductRepository productRepository;

    //前台活动商品展示（不分页）
    @Override
    public List<ProductActivity> activityProductList() {
        return productActivityRepository.findAll();
    }

    //活动页面分页
    @Override
    public Page<ProductActivity> activityProductList(String product_keywords, int page, int limit) {
        Sort sort = new Sort(Sort.Direction.DESC, "activityId");
        Pageable pageable = new PageRequest(page,limit, sort);
        Specification<ProductActivity> specification = new PageUtil<ProductActivity>(product_keywords).getPage("product_keywords");
        Page pages = productActivityRepository.findAll(specification,pageable);
        return pages;
    }

    //添加商品为活动商品
    @Override
    public void adminAddActivity(long productId, HttpServletRequest request) {
        Product product = productRepository.findById(productId);

        ProductActivity productActivity = new ProductActivity();
        Activity activity = activityRepository.findByActivityName(request.getParameter("activity_name"));
        productActivity.setActivity_name(activity.getActivity_name());

        productActivity.setActivity_discount((int) activity.getActivity_discount());
        productActivity.setProductId(productId);
        String img = null;
        if (product.getProduct_picture().contains(",")){
            img = product.getProduct_picture();
            img = img.substring(0,img.indexOf(","));
        }
        productActivity.setProduct_picture(img);
        productActivity.setProduct_counterPrice(product.getProduct_counterPrice());
        productActivity.setProduct_mallPrice(product.getProduct_mallPrice());
        productActivity.setProduct_keywords(product.getProduct_keywords());
        productActivityRepository.save(productActivity);
    }

    //通过id查找活动商品
    @Override
    public ProductActivity findByActivity(long activityId) {
        return productActivityRepository.findById(activityId);
    }

    //修改活动商品
    @Override
    public void editActivity(long activityId, HttpServletRequest request) {
        ProductActivity productActivity = productActivityRepository.findById(activityId);

        Activity activity = activityRepository.findByActivityName(request.getParameter("activity_name"));
        productActivity.setActivity_name(activity.getActivity_name());
        productActivity.setActivity_discount((int) activity.getActivity_discount());
        productActivity.setProductId(productActivity.getProductId());
        productActivity.setProduct_picture(productActivity.getProduct_picture());
        productActivity.setProduct_counterPrice(productActivity.getProduct_counterPrice());
        productActivity.setProduct_mallPrice(productActivity.getProduct_mallPrice());
        productActivity.setProduct_keywords(productActivity.getProduct_keywords());
        productActivityRepository.save(productActivity);
    }

    //删除活动商品
    @Override
    public void deleteActivity(long activityId) {
        productActivityRepository.deleteById(activityId);
    }
}
