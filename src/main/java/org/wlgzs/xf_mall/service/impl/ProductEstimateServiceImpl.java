package org.wlgzs.xf_mall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.wlgzs.xf_mall.dao.OrdersRepository;
import org.wlgzs.xf_mall.dao.ProductEstimateRepository;
import org.wlgzs.xf_mall.entity.Orders;
import org.wlgzs.xf_mall.entity.ProductEstimate;
import org.wlgzs.xf_mall.service.ProductEstimateService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Auther: 李晓珊
 * @Date: 2018/4/23 13:16
 * @Description: 商品评价
 */
@Service
public class ProductEstimateServiceImpl implements ProductEstimateService {
    @Autowired
    private ProductEstimateRepository productEstimateRepository;
    @Autowired
    private OrdersRepository ordersRepository;

    //添加评论
    @Override
    public void save(HttpServletRequest request, MultipartFile[] myFileNames, HttpSession session, long orderId){
        String realName = "";
        String[] str = new String[myFileNames.length];
        for (int i = 0; i < myFileNames.length; i++) {
            if (!myFileNames[i].getOriginalFilename().equals("")) {
                String fileName = myFileNames[i].getOriginalFilename();
                String fileNameExtension = fileName.substring(fileName.indexOf("."), fileName.length());
                // 生成实际存储的真实文件名
                realName = UUID.randomUUID().toString() + fileNameExtension;
                // "/upload"是你自己定义的上传目录
                String realPath = session.getServletContext().getRealPath("/upload");
                File uploadFile = new File(realPath, realName);
                System.out.println("评价图片");
                try {
                    myFileNames[i].transferTo(uploadFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (!myFileNames[i].getOriginalFilename().equals("")) {
                str[i] = request.getContextPath() + "/upload/" + realName;
            }
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < str.length; i++) {
            if (!myFileNames[i].getOriginalFilename().equals("")) {
                stringBuffer.append(str[i] + ",");
            }
        }
        System.out.println(stringBuffer);
        String estimate_img = stringBuffer.substring(0, stringBuffer.length() - 1);

        ProductEstimate productEstimate = new ProductEstimate();
        productEstimate.setEstimate_content(request.getParameter("estimate_content"));
        int estimate_grade = Integer.parseInt(request.getParameter("estimate_grade"));
        productEstimate.setEstimate_grade(estimate_grade);
        productEstimate.setEstimate_img(estimate_img);
        int estimate_is_nameless = Integer.parseInt(request.getParameter("estimate_is_nameless"));
        productEstimate.setEstimate_isNameless(estimate_is_nameless);
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        productEstimate.setEstimate_time(date);
        Orders orders = ordersRepository.findById(orderId);
        productEstimate.setUserId(orders.getUserId());
        productEstimate.setUser_name(orders.getUser_name());
        productEstimate.setOrderId(orderId);
        productEstimate.setProductId(orders.getProductId());
        productEstimate.setProduct_keywords(orders.getProduct_keywords());
        productEstimate.setProduct_mallPrice(orders.getProduct_mallPrice());
        productEstimate.setProduct_picture(orders.getProduct_picture());
        productEstimate.setProduct_specification(orders.getProduct_specification());
        productEstimateRepository.save(productEstimate);
        orders.setOrder_status("未评价");
        ordersRepository.save(orders);
    }

    //个人评价展示
    @Override
    public List<ProductEstimate> findEstimateById(long userId) {
        return productEstimateRepository.findByUserId(userId);
    }

    //商品评价展示
    @Override
    public List<ProductEstimate> findEstimateByproductId(long productId) {
        return productEstimateRepository.findByProductId(productId);
    }

    //修改为匿名评价
    @Override
    public void changeEstimate(long estimateId) {
        ProductEstimate productEstimate = productEstimateRepository.findById(estimateId);
        productEstimate.setEstimate_isNameless(1);
        productEstimateRepository.save(productEstimate);
    }

    //查询商品评价数
    @Override
    public long findEstimateCount(long productId) {
        return productEstimateRepository.findCount(productId);
    }
}
