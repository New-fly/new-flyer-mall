package org.wlgzs.xf_mall.controller;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.wlgzs.xf_mall.base.BaseController;
import org.wlgzs.xf_mall.entity.Activity;
import org.wlgzs.xf_mall.entity.Authorization;
import org.wlgzs.xf_mall.entity.ProductActivity;
import org.wlgzs.xf_mall.entity.User;
import org.wlgzs.xf_mall.util.AuthUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author:胡亚星
 * @createTime 2018-04-16 21:26
 * @description:用户查询控制层
 **/

@Controller
public class LoginController extends BaseController {

//    @RequestMapping("/registeredMail")
//    public String register(HttpServletRequest request, Model model, User user, String code) {
//        //判断用户是否合法
//        if (logUserService.validationUser(request, code)) {
//            userService.save(user);
//            return "redirect:/toRegistered";
//        } else {
//            model.addAttribute("msg", "验证码错误");
//            return "redirect:/toRegistered";
//        }
//    }

    @RequestMapping("/")
    public String toIndex() {
        return "redirect:/HomeController/homeProduct";
    }
    //去注册
    @RequestMapping("/toRegistered")
    public String toRegister() {
        return "sign-up1";
    }

    //去登陆
    @RequestMapping("/toLogin")
    public ModelAndView toLogin(Model model,@RequestParam(value = "activity_name", defaultValue = "登录页面") String activity_name) {
        List<Activity> activities = activityService.findByActivity(activity_name);
        model.addAttribute("activities",activities);
        return new ModelAndView("login");
    }

    @RequestMapping("/registered")
    public String register(Model model, HttpServletRequest request) {
        User user = new User();
        user = logUserService.registered(user,request);
        if(user.getUser_role() != null){
            userService.save(user);
            return "sign-up3";
        }else{
            model.addAttribute("mag","请确定您的邮箱是正确的");
            return "redirect:/toRegistered";
        }
    }

    /**
     * @param
     * @return 用户实现登陆
     * @author 胡亚星
     * @date 2018/4/19 21:20
     */
    @RequestMapping("/login")
    public String login(HttpServletRequest request, Model model, String user_name, String user_password) {
        HttpSession session = request.getSession(true);
        User user = logUserService.login(request, user_name, user_password);
        System.out.println(user);
        System.out.println("session==="+session.getAttribute("user"));
        System.out.println("adminSession==="+session.getAttribute("adminUser"));
        if (user != null) {
            if("管理员".equals(user.getUser_role())) {
                return "adminIndex";
            }
            if("超级管理员".equals(user.getUser_role())) {
                return "adminIndex";
            } else{
                return "redirect:/HomeController/homeProduct";
            }
        } else {
            model.addAttribute("msg", "账号或密码错误");
            List<Activity> activities = activityService.findByActivity("登录页面");
            System.out.println(activities);
            model.addAttribute("activities",activities);
            return "login";
        }
    }

    //github登陆
    @GetMapping(value = "/callback")
    public String claaback(HttpServletRequest request,Model model, String code) {
        String me = "";
        JSONObject res = null;
        try {
            me = AuthUtils.doGetStr
                    ("https://github.com/login/oauth/access_token?client_id=" + AuthUtils.CLIENT_ID +
                            "&client_secret=" + AuthUtils.CLIENT_SECRET + "&code=" + code);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String atoke = me.split("&")[0];
        try {
            res = AuthUtils.doGetJson("https://api.github.com/user?"+atoke+"");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(res);
        Object o = res.get("id");
        long githubId = Long.valueOf(String.valueOf(o)).longValue();
        //查询该id是否已存在，并返回该数据
        Authorization authorization = authorizationService.isBinding(githubId);
        if(authorization == null){//首次登陆
            model.addAttribute("githubId",githubId);
            return "addGithub";
        }else{//登陆已有账号
            long userId = authorization.getUserId();
            logUserService.loginId(request,userId);
            return "redirect:/HomeController/homeProduct";
        }
    }

    //github注册新用户
    @RequestMapping("githubRegistered")
    public String githubRegistered(HttpServletRequest request,Model model,String user_mail,String user_password){
        return logUserService.githubRegistered(request,user_mail,user_password);
    }


    //github绑定老用户
    @RequestMapping("bindingRegistered")
    public String bindingRegistered(HttpServletRequest request,Model model,String user_name,String user_password){
        User user = logUserService.login(request, user_name, user_password);
        if(user != null){//存入登陆授权表
            long githubId = Long.parseLong(request.getParameter("githubId"));
            long userId = user.getUserId();
            Authorization authorization = new Authorization();
            authorization.setUserId(userId);
            authorization.setGithubId(githubId);
            authorizationService.save(authorization);
            return "Index";
        }else{
            model.addAttribute("msg", "账号或密码错误");
            return "addGithub";
        }
    }


    //用户退出
    @RequestMapping("cancellation")
    public String cancellation(HttpServletRequest request) {
        logUserService.cancellation(request);
        return "redirect:/toLogin";
    }

    //管理员退出
    @RequestMapping("adminCancellation")
    public String adminCancellation(HttpServletRequest request){
        logUserService.adminCancellation(request);
        return "redirect:/toLogin";
    }
    /**
     * @param
     * @return
     * @author 胡亚星
     * @date 2018/4/21 19:45
     * @Description: 向邮箱发送验证码
     */
    @ResponseBody
    @RequestMapping(value = "sendEmail", method = RequestMethod.POST)
    public void sendEmail(Model model, HttpServletRequest request, HttpServletResponse response) {
        String user_mail = request.getParameter("user_mail");
        System.out.println(user_mail);
        if (logUserService.selectEmail(user_mail)) {
            System.out.println("out---------------");
            model.addAttribute("mag", "该邮箱已存在");
            String result = "该邮箱已存在";
            PrintWriter out = null;
            try {
                out = response.getWriter();
            } catch (IOException e) {
                e.printStackTrace();
            }
            out.write(result.toString()); //将数据传到前台
        } else {
            System.out.println("in-----------------");
            try {
                logUserService.sendEmail(request, user_mail);//发送
            } catch (Exception e) {
                e.printStackTrace();
            }
            model.addAttribute("ok", 200);
        }
    }

    /**
     * @param
     * @return
     * @author 胡亚星
     * @date 2018/4/22 15:36
     * @Description: 验证用户验证码是否正确
     */
    @RequestMapping("contrastCode")
    public String contrastCode(Model model, HttpServletRequest request) {
        String user_mail = request.getParameter("user_mail");
        String sessionMail = "";
        String usercode = "";
        String sessioncode = "";
        if (logUserService.contrastCode(request, user_mail, sessionMail, usercode, sessioncode)) { //对比两个code是否正确
            model.addAttribute("user_mail", user_mail);
            model.addAttribute("mag", "验证通过");
            System.out.println(user_mail);
            System.out.println("验证码正确");
            return "sign-up2";
        } else {
            model.addAttribute("mag", "请检查您的验证码或邮箱是否正确");
            System.out.println("验证码错误");
            return "sign-up1";
        }
    }

}
