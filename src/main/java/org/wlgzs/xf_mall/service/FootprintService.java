package org.wlgzs.xf_mall.service;

import org.wlgzs.xf_mall.entity.Footprint;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2018/5/5 11:28
 * @Description:
 */
public interface FootprintService {

    void save(HttpServletRequest request,long userId,long productId);

    void delete(long footprintId);

    void deleteFootprints(String footprintId);

    List<Footprint> findFootprints(String product_keywords, long userId);

    List<Footprint> findFootprintByUserId(long userId);

}
