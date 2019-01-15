package org.wlgzs.xf_mall.service.impl;

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
import org.wlgzs.xf_mall.util.IdsUtil;
import org.wlgzs.xf_mall.util.PageUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 阿杰
 * @Date: 2018/5/2 00:28
 * @Description: 活动商品
 */
@Service
public class ProductActivityServiceImp implements ProductActivityService {
    @Resource
    private ProductActivityRepository productActivityRepository;
    @Resource
    private ActivityRepository activityRepository;
    @Resource
    private ProductRepository productRepository;

    //前台活动商品展示（不分页）
    @Override
    public List<ProductActivity> activityProductList(String activity_name) {
        return productActivityRepository.findByActivityName(activity_name);
    }

    //活动页面分页
    @Override
    public Page<ProductActivity> activityProductList(String product_keywords, int page, int limit) {
        Sort sort = new Sort(Sort.Direction.DESC, "activityId");
        Pageable pageable = PageRequest.of(page,limit, sort);
        Specification<ProductActivity> specification = new PageUtil<ProductActivity>(product_keywords).getPage("product_keywords","activity_name");
        return productActivityRepository.findAll(specification,pageable);
    }

    //添加商品为活动商品
    @Override
    public void adminAddActivity(long productId, HttpServletRequest request) {
        Product product = productRepository.findById(productId);

        ProductActivity productActivity = new ProductActivity();
        Activity activity = activityRepository.findByActivityName(request.getParameter("activity_name"));
        productActivity.setActivity_name(activity.getActivity_name());
        product.setProduct_activity(activity.getActivity_name());
        productRepository.save(product);

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

    @Override
    public void adminAddActivitys(String productId, HttpServletRequest request) {
        long[] productIds = IdsUtil.IdsUtils(productId);
        List<Product> products = productRepository.findProductByProductId(productIds);
        Activity activity = activityRepository.findByActivityName(request.getParameter("activity_name"));
        List<ProductActivity> productActivities = new ArrayList<>();
        for(int i = 0;i < products.size();i++){
            ProductActivity productActivity = new ProductActivity();
            productActivity.setActivity_name(activity.getActivity_name());

            productActivity.setActivity_discount((int) activity.getActivity_discount());
            productActivity.setProductId(productIds[i]);
            String img = null;
            if (products.get(i).getProduct_picture().contains(",")){
                img = products.get(i).getProduct_picture();
                img = img.substring(0,img.indexOf(","));
            }
            productActivity.setProduct_picture(img);
            productActivity.setProduct_counterPrice(products.get(i).getProduct_counterPrice());
            productActivity.setProduct_mallPrice(products.get(i).getProduct_mallPrice());
            productActivity.setProduct_keywords(products.get(i).getProduct_keywords());
            productActivities.add(productActivity);
        }
        productActivityRepository.saveAll(productActivities);
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
        ProductActivity productActivity = productActivityRepository.findById(activityId);
        Product product = productRepository.findById(productActivity.getProductId());
        product.setProduct_activity("无");
        productRepository.save(product);
        productActivityRepository.deleteById(activityId);
    }

    //批量删除活动商品
    @Override
    public void adminDeleteActivitys(String activityId) {
        long[] ids = IdsUtil.IdsUtils(activityId);
        productActivityRepository.deleteByIds(ids);
    }
}
