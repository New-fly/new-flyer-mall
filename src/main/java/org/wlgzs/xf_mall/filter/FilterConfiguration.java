package org.wlgzs.xf_mall.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author:胡亚星
 * @createTime 2018-05-12 11:30
 * @description:
 **/
@Configuration
public class FilterConfiguration {
    @Bean
    public FilterRegistrationBean filterDemoRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        //注入过滤器
        registration.setFilter(new DemoFilter());
        //拦截规则
        registration.addUrlPatterns("/AdminActivityController/*");
        registration.addUrlPatterns("/OrderController/*");
        registration.addUrlPatterns("/AdminProductController/*");
        registration.addUrlPatterns("/findProductSensitive/*");
        registration.addUrlPatterns("/AdminUserController/*");
        //过滤器名称
        registration.setName("DemoFilter");
        //是否自动注册 false 取消Filter的自动注册
        registration.setEnabled(true);
        //过滤器顺序
        registration.setOrder(Integer.MAX_VALUE - 1);
        return registration;
    }

    @Bean
    public FilterRegistrationBean filterLoginRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        //注入过滤器
        registration.setFilter(new LoginFilter());
        //拦截规则
        registration.addUrlPatterns("/pay/aliPay/*");
        registration.addUrlPatterns("/FootprintController/*");
        registration.addUrlPatterns("/IntegralController/*");
        registration.addUrlPatterns("/ActivityController/*");
        registration.addUrlPatterns("/EstimateController/*");
        registration.addUrlPatterns("/ProductListController/*");
        registration.addUrlPatterns("/UserAddressController/*");
        registration.addUrlPatterns("/UserManagementController/*");
        registration.addUrlPatterns("/UserOrderController/*");


        //过滤器名称
        registration.setName("LoginFilter");
        //是否自动注册 false 取消Filter的自动注册
        registration.setEnabled(true);
        //过滤器顺序
        registration.setOrder(Integer.MAX_VALUE);
        return registration;
    }

}