package org.wlgzs.xf_mall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import org.wlgzs.xf_mall.dao.ActivityRepository;
import org.wlgzs.xf_mall.entity.Activity;
import org.wlgzs.xf_mall.service.ActivityService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
    @Autowired
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
        if (myFileName != null) {
            String fileName = myFileName.getOriginalFilename();
            String fileNameExtension = fileName.substring(fileName.indexOf("."), fileName.length());
            // 生成实际存储的真实文件名
            realName = UUID.randomUUID().toString() + fileNameExtension;
            // "/upload"是你自己定义的上传目录
            String realPath = session.getServletContext().getRealPath("/activity");

            String s = "";
            try {
                s = ResourceUtils.getURL("classpath:").getPath();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            System.out.println("s："+s.substring(5,s.length()-24)+"/META-INF/resources/activity"+realName);
            s = s.substring(5,s.length()-20)+"/META-INF/resources/activity"+realName;
            //s = "xf_mall-0.0.1-SNAPSHOT/META-INF/resources/activity"+realName;
            File path = new File(s);
            System.out.println(path.getAbsolutePath());
            File upload = new File(path.getAbsolutePath());
            System.out.println("uploadUrl:"+upload.getAbsolutePath());

            System.out.println("后台添加活动");
            //File uploadFile = new File(realPath, realName);
            try {
                myFileName.transferTo(upload);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String str = request.getContextPath() + "/activity/" + realName;
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
        if (myFileName != null) {
            String fileName = myFileName.getOriginalFilename();
            String fileNameExtension = fileName.substring(fileName.indexOf("."), fileName.length());
            // 生成实际存储的真实文件名
            realName = UUID.randomUUID().toString() + fileNameExtension;
            // "/upload"是你自己定义的上传目录
            String realPath = session.getServletContext().getRealPath("/activity");
            System.out.println("后台修改活动");
            File uploadFile = new File(realPath, realName);
            try {
                myFileName.transferTo(uploadFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String str = request.getContextPath() + "/activity/" + realName;
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
