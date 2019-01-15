package org.wlgzs.xf_mall.service.impl;

import org.springframework.stereotype.Service;
import org.wlgzs.xf_mall.dao.FootprintRepository;
import org.wlgzs.xf_mall.dao.ProductRepository;
import org.wlgzs.xf_mall.entity.Footprint;
import org.wlgzs.xf_mall.entity.Product;
import org.wlgzs.xf_mall.service.FootprintService;
import org.wlgzs.xf_mall.util.IdsUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2018/5/5 11:27
 * @Description:
 */
@Service
public class FootprintServiceImpl implements FootprintService {
    @Resource
    FootprintRepository footprintRepository;
    @Resource
    private ProductRepository productRepository;

    //添加足迹
    @Override
    public void save(HttpServletRequest request, long userId, long productId) {
        Footprint footprintOne = footprintRepository.findByUserIdAndFootprintId(userId, productId);
        if(footprintOne != null){
            footprintRepository.deleteById(footprintOne.getFootprintId());
            footprintRepository.save(footprintOne);
        }
        if (footprintOne == null) {
            Product product = productRepository.findById(productId);
            Footprint footprint = new Footprint();
            Date date = new Date();
            footprint.setFootprint_time(date);
            footprint.setUserId(userId);
            footprint.setProductId(productId);
            footprint.setProduct_keywords(product.getProduct_keywords());
            String img = null;
            if (product.getProduct_picture().contains(",")){
                img = product.getProduct_picture();
                img = img.substring(0,img.indexOf(","));
            }
            footprint.setProduct_mallPrice(product.getProduct_mallPrice());
            footprint.setProduct_picture(img);
            footprintRepository.save(footprint);
        }
    }

   //删除足迹
    @Override
    public void delete(long footprintId){
       footprintRepository.deleteById(footprintId);
   }

   //批量删除足迹
   @Override
    public void deleteFootprints(String footprintId) {
        long[] Ids = IdsUtil.IdsUtils(footprintId);
        footprintRepository.deleteByIds(Ids);
    }

    //搜索足迹商品
    @Override
    public List<Footprint> findFootprints(String product_keywords, long userId) {
        return footprintRepository.findFootprints(product_keywords,userId);
    }

    //遍历用户足迹商品
    @Override
    public List<Footprint> findFootprintByUserId(long userId) {
        return footprintRepository.findByUserId(userId);
    }

}
