package org.wlgzs.xf_mall.service.impl;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.test.context.junit4.SpringRunner;
import org.wlgzs.xf_mall.dao.ProductRepository;
import org.wlgzs.xf_mall.entity.Product;

import java.util.List;

/**
 * @Auther: 阿杰
 * @Date: 2018/5/28 22:17
 * @Description:
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServicerImplTest {

    @Autowired
    private  ProductRepository  productRepository;

    @Test
    public void  searchProductTest(){
        Pageable pageable = new PageRequest(0, 14);

        System.out.println(pageable);

        List<Product> all = productRepository.findAll();

        Page<Product> pages = new PageImpl<>(all,pageable,all.size());
        int i=1;
        for (Product product :pages){
            System.out.println("第"+i+"条"+product);
            i++;
        }

        System.out.println(pages.getContent());
        System.out.println(pages.getTotalPages());
        System.out.println(pages.getTotalElements());
        System.out.println(pages.getNumber());
    }
}
