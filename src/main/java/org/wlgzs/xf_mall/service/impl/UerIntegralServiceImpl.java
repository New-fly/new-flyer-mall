package org.wlgzs.xf_mall.service.impl;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wlgzs.xf_mall.dao.UserIntegralRepository;
import org.wlgzs.xf_mall.entity.Product;
import org.wlgzs.xf_mall.entity.UserIntegral;
import org.wlgzs.xf_mall.service.UserIntegralService;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 李晓珊
 * @Date: 2018/4/23 09:02
 * @Description: 用户积分管理
 */
@Service
public class UerIntegralServiceImpl implements UserIntegralService {
    @Autowired
    private UserIntegralRepository userIntegralRepository;
    //通过积分id查询用户积分
    @Override
    public UserIntegral findUserIntegralById(long id){
        return userIntegralRepository.findById(id);
    }

    //通过用户id查询用户积分明细
    @Override
    public List<UserIntegral> getUserIntegral(long userId){
        return userIntegralRepository.findByUserId(userId);
    }

    //收入积分明细
    @Override
    public List<UserIntegral> getUserIntegralIncome(long userId) {
        return userIntegralRepository.findByIncome(userId);
    }

    //支出积分明细
    @Override
    public List<UserIntegral> getUserIntegralExpend(long userId){
        return userIntegralRepository.findByExpend(userId);
    }

    //在购买商品时自动添加积分明细
    @Override
    public void save(long userId,HttpServletRequest request){
        Map<String, String[]> properties = request.getParameterMap();
        UserIntegral userIntegral = new UserIntegral();
        Product product = new Product();
        try {
            BeanUtils.populate(userIntegral, properties);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        Date data = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        userIntegral.setOrder_purchaseTime(data);
        userIntegral.setUserId(userId);
        userIntegral.setProduct_keyword(product.getProduct_keywords());
        String img = null;
        if (product.getProduct_picture().contains(",")){
            img = product.getProduct_picture();
            img = img.substring(0,img.indexOf(","));
        }
        userIntegral.setProduct_picture(img);
        userIntegral.setUserIntegral_vary(product.getProduct_getPoints());
        userIntegralRepository.save(userIntegral);
    }

    //删除积分记录
    @Override
    public void delete(Long userIntegralId){
        userIntegralRepository.deleteById(userIntegralId);
    }
}
