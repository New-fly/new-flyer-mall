package org.wlgzs.xf_mall.filter;

import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author:胡亚星
 * @createTime 2018-05-12 9:52
 * @description:
 **/
public class DemoFilter implements Filter {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(DemoFilter.class);

//    protected static List<Pattern> patterns = new ArrayList<Pattern>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("后台过滤器启动");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        logger.info("后台过滤");
        String url = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
        if (url.startsWith("/") && url.length() > 1) {
            url = url.substring(1);
        }
        System.out.println(url);
        HttpSession session = httpRequest.getSession();
        String adminName = (String) session.getAttribute("adminName");
        if (adminName != null) {
            System.out.println("后台通过");
            // session存在
            chain.doFilter(httpRequest, httpResponse);
            return;
        } else {
            System.out.println("后台未通过");
            // session不存在 准备跳转失败
            httpResponse.sendRedirect("../toLogin");
            return;
        }
    }


    @Override
    public void destroy() {

    }

//    private boolean isInclude(String url) {
//        for (Pattern pattern : patterns) {
//            Matcher matcher = pattern.matcher(url);
//            if (matcher.matches()) {
//                return true;
//            }
//        }
//        return false;
//    }

//    @Bean
//    public FilterRegistrationBean someFilterRegistration() {
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        registration.setFilter(sessionFilter());
//        registration.addUrlPatterns("/UserAddressController/*");
//        registration.addInitParameter("paramName", "paramValue");
//        registration.setName("sessionFilter");
//        return registration;
//    }
//
//    @Bean(name = "sessionFilter")
//    public Filter sessionFilter() {
//        return new DemoFilter();
//    }
}
