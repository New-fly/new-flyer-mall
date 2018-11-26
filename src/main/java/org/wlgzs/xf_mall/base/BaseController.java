package org.wlgzs.xf_mall.base;



import org.wlgzs.xf_mall.service.*;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * @author 胡亚星
 * @description 简化控制层代码
 */
public abstract class BaseController implements Serializable {

    //自动注入业务层
    @Resource
    protected ActivityService activityService;
    @Resource
    protected AuthorizationService authorizationService;
    @Resource
    protected FootprintService footprintService;
    @Resource
    protected LogUserService logUserService;
    @Resource
    protected OrdersService ordersService;
    @Resource
    protected ProductActivityService productActivityService;
    @Resource
    protected ProductEstimateService productEstimateService;
    @Resource
    protected ProductService productService;
    @Resource
    protected SearchShieldService searchShieldService;
    @Resource
    protected ShippingAddressService shippingAddressService;
    @Resource
    protected UserIntegralService userIntegralService;
    @Resource
    protected UserService userService;
}
