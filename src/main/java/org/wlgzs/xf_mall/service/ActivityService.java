package org.wlgzs.xf_mall.service;

import org.springframework.web.multipart.MultipartFile;
import org.wlgzs.xf_mall.entity.Activity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Auther: 阿杰
 * @Date: 2018/5/10 09:09
 * @Description: 活动
 */
public interface ActivityService {
    List<Activity> getActivity();

    List<String> getActivityName();

    void addActivity(MultipartFile myFileName, HttpSession session, HttpServletRequest request);

    Activity findActivity(long activitySumId);
}
