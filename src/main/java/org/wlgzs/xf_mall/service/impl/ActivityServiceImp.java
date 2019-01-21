package org.wlgzs.xf_mall.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.wlgzs.xf_mall.dao.ActivityRepository;
import org.wlgzs.xf_mall.entity.Activity;
import org.wlgzs.xf_mall.service.ActivityService;
import org.wlgzs.xf_mall.util.IdsUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Auther: 阿杰
 * @Date: 2018/5/10 09:10
 * @Description:
 */
@Service
public class ActivityServiceImp implements ActivityService {
    @Resource
    private ActivityRepository activityRepository;

    //遍历所有活动
    @Override
    public List<Activity> getActivity() {
        return activityRepository.findAll();
    }

    //添加活动
    @Override
    public void addActivity(MultipartFile myFileName, HttpSession session, HttpServletRequest request) {
        String realName = "";
        String realPath = "";
        if (myFileName != null) {
            String fileName = myFileName.getOriginalFilename();
            assert fileName != null;
            String fileNameExtension = fileName.substring(fileName.indexOf("."));
            // 生成实际存储的真实文件名
            realName = UUID.randomUUID().toString() + fileNameExtension;
            realPath = "/upload/activity/" + realName;
            File uploadFile = new File("." + realPath);
            IdsUtil.writeFile(myFileName, uploadFile);
        }
        String str = "/api/path" + realPath;
        Activity activity = new Activity();
        activity.setActivity_name(request.getParameter("activity_name"));
        int amount = Integer.parseInt(request.getParameter("activity_discount"));
        activity.setActivity_discount(amount);
        String time = request.getParameter("activity_time");
        time = time.substring(0,time.length()-3);
        DateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = dateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        activity.setActivity_time(date);
        activity.setActivity_picture(str);
        activityRepository.save(activity);
    }

    //通过id查找活动
    @Override
    public Activity findActivity(long activitySumId) {
        return activityRepository.findById(activitySumId);
    }

    //修改活动
    @Override
    public void editActivity(long activitySumId, MultipartFile myFileName, HttpSession session, HttpServletRequest request) {
        Activity activity = activityRepository.findById(activitySumId);
        String realName = "";
        String realPath = "";
        if (myFileName != null) {
            String fileName = myFileName.getOriginalFilename();
            assert fileName != null;
            String fileNameExtension = fileName.substring(fileName.indexOf("."));
            // 生成实际存储的真实文件名
            realName = UUID.randomUUID().toString() + fileNameExtension;
            realPath = "/upload/activity/" + realName;
            File uploadFile = new File("." + realPath);
            IdsUtil.writeFile(myFileName, uploadFile);
        }
        String str = "/api/path" + realPath;
        boolean b = new IdsUtil().deleteFile(activity.getActivity_picture().substring(9));
        activity.setActivity_name(request.getParameter("activity_name"));
        int amount = Integer.parseInt(request.getParameter("activity_discount"));
        activity.setActivity_discount(amount);
        String time = request.getParameter("activity_time");
        time = time.substring(0,time.length()-3);
        DateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = dateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        activity.setActivity_time(date);
        activity.setActivity_picture(str);
        activityRepository.save(activity);
    }

    //删除活动
    @Override
    public void deleteActivity(long activityId) {
        Activity activity = activityRepository.findById(activityId);
        boolean b = new IdsUtil().deleteFile(activity.getActivity_picture().substring(9));
        activityRepository.deleteById(activityId);
    }

    //活动页面
    @Override
    public Activity findByActivityName(String activity_name) {
        return activityRepository.findByActivityName(activity_name);
    }

    //检验抢购活动是否存在
    @Override
    public boolean booleanByActivityName(String activity_name) {
        Activity activity = activityRepository.selectActivity(activity_name);
        return activity != null;
    }

    @Override
    public List<Activity> findByActivity(String activity_name) {
        return activityRepository.findActivity(activity_name);
    }
}
