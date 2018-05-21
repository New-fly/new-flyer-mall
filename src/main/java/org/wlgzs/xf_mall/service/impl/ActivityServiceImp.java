package org.wlgzs.xf_mall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.wlgzs.xf_mall.dao.ActivityRepository;
import org.wlgzs.xf_mall.entity.Activity;
import org.wlgzs.xf_mall.service.ActivityService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Auther: 阿杰
 * @Date: 2018/5/10 09:10
 * @Description:
 */
@Service
public class ActivityServiceImp  implements ActivityService {
    @Autowired
    private ActivityRepository activityRepository;

    //遍历所有活动
    @Override
    public List<Activity> getActivity() {
        return activityRepository.findAll();
    }
    //遍历所有活动名称
    @Override
    public List<String> getActivityName() {
        List<Activity> activities = activityRepository.findAll();
        List<String> activityList = new ArrayList<String>();
        for (int i = 0; i < activities.size(); i++) {
            activityList.add(activities.get(i).getActivity_name());
        }
        return activityList;
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
            String realPath = session.getServletContext().getRealPath("/upload");
            System.out.println(realPath);
            File uploadFile = new File(realPath, realName);
            try {
                myFileName.transferTo(uploadFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String str = request.getContextPath() + "/upload/" + realName;
        Activity activity = new Activity();
        activity.setActivity_name(request.getParameter("activity_name"));
        int amount = Integer.parseInt(request.getParameter("activity_discount"));
        activity.setActivity_discount(amount);
        Date data = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        activity.setActivity_time(data);
        activity.setActivity_picture(str);
        activityRepository.save(activity);
    }

    //通过id查找活动
    @Override
    public Activity findActivity(long activitySumId) {
        return activityRepository.findById(activitySumId);
    }
}
