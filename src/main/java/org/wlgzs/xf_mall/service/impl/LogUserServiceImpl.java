package org.wlgzs.xf_mall.service.impl;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.wlgzs.xf_mall.dao.LogUserRepository;
import org.wlgzs.xf_mall.entity.User;

import org.wlgzs.xf_mall.service.LogUserService;
import org.wlgzs.xf_mall.util.RandonNumberUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author:胡亚星
 * @createTime 2018-04-16 21:36
 * @description:登陆注册用户查询方法实现
 **/
@Service
public class LogUserServiceImpl implements LogUserService {

    @Autowired
    private LogUserRepository logUserRepository;

   @Autowired
    private JavaMailSender mailSender;

    //实现登陆的方法
    @Override
    public User login(HttpServletRequest request,String user_name, String user_password){
        HttpSession session = request.getSession(true);
            User user = logUserRepository.Login(user_name,user_password);
            if(user != null){
                if(user.getUser_role().equals("普通用户")){
                    session.setMaxInactiveInterval(60 * 20);
                    session.setAttribute("name", user.getUser_name());//之后用过滤器实现
                    session.setAttribute("userId", user.getUserId());
                    session.setAttribute("user",user);
                }else{
                    session.setMaxInactiveInterval(60 * 20);
                    session.setAttribute("adminName", user.getUser_name());//之后用过滤器实现
                    session.setAttribute("adminUser",user);
                }
            }
            return user;
    }

    @Override
    public void loginId(HttpServletRequest request,long userId) {
        HttpSession session = request.getSession(true);
        User user = logUserRepository.loginId(userId);
        session.setMaxInactiveInterval(60 * 20);
        session.setAttribute("name", user.getUser_name());//之后用过滤器实现
        session.setAttribute("userId", user.getUserId());
        session.setAttribute("user",user);
    }

    @Override
    public void cancellation(HttpServletRequest request){
        HttpSession session = request.getSession(true);
        session.invalidate();
    }

    //注册
    @Override
    public User registered(User user,HttpServletRequest request) {
        Map<String, String[]> properties = request.getParameterMap();
        try {
            BeanUtils.populate(user, properties);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(user.getUser_mail());
        HttpSession session = request.getSession();
        String SessionMail = (String)session.getAttribute("user_mail");
        if(SessionMail.equals(user.getUser_mail())){
            if(m.matches() && user.getUser_mail() != null && !user.getUser_mail().equals("")){
                RandonNumberUtils randonNumberUtils = new RandonNumberUtils();
                String name = randonNumberUtils.getNumber(8);
                String user_name = "XF_" + name;
                String user_avatar = request.getContextPath() + "/headPortrait/morende.jpg";
                System.out.println("====="+user.getUser_role());
                user.setUser_name(user_name);
                user.setUser_avatar(user_avatar);
                user.setUser_role("普通用户");
            }
        }
        return user;
    }

    @Override
    public long getUserId(String user_mail) {
        User user = logUserRepository.selectEmail(user_mail);
        return user.getUserId();
    }

    //发送邮箱
    @Override
    public void sendEmail(HttpServletRequest request,String user_mail){
        HttpSession session = request.getSession(true);
        SimpleMailMessage mainMessage = new SimpleMailMessage();
        RandonNumberUtils randonNumberUtils = new RandonNumberUtils();
        String authCode = randonNumberUtils.getRandonString(6);
        System.out.println(authCode);
        session.setMaxInactiveInterval(60 * 2);
        session.setAttribute("authCode", authCode);
        session.setAttribute("user_mail", user_mail);
        //发送者
        mainMessage.setFrom("huystar@126.com");
        //接收者
        mainMessage.setTo(user_mail);
        //发送的标题
        mainMessage.setSubject("注册新飞商城");
        //发送的内容
        mainMessage.setText("验证码："+authCode+"您正在注册新飞商城，请输入您的验证码继续完成注册");
        System.out.println(mainMessage+"////////////////////////////////////////////////////////////");
        System.out.println(mailSender+"-------------------------------------------");
        mailSender.send(mainMessage);
        System.out.println(mailSender);
        System.out.println("ok");
    }

    //发送邮箱
    @Override
    public void sendEmail1(HttpServletRequest request,String user_mail){
        HttpSession session = request.getSession(true);
        SimpleMailMessage mainMessage = new SimpleMailMessage();
        RandonNumberUtils randonNumberUtils = new RandonNumberUtils();
        String authCode = randonNumberUtils.getRandonString(6);
        System.out.println(authCode);
        session.setMaxInactiveInterval(60 * 2);
        session.setAttribute("authCode", authCode);
        session.setAttribute("user_mail", user_mail);
        //发送者
        mainMessage.setFrom("huystar@126.com");
        //接收者
        mainMessage.setTo(user_mail);
        //发送的标题
        mainMessage.setSubject("修改手机号");
        //发送的内容
        mainMessage.setText("验证码："+authCode+" ， 您正在修改您的手机号，请继续");
        System.out.println(mainMessage+"////////////////////////////////////////////////////////////");
        System.out.println(mailSender+"-------------------------------------------");
        mailSender.send(mainMessage);
        System.out.println(mailSender);
        System.out.println("ok");
    }

    //发送邮箱
    @Override
    public void sendEmail2(HttpServletRequest request,String user_mail){
        HttpSession session = request.getSession(true);
        SimpleMailMessage mainMessage = new SimpleMailMessage();
        RandonNumberUtils randonNumberUtils = new RandonNumberUtils();
        String authCode = randonNumberUtils.getRandonString(6);
        System.out.println(authCode);
        session.setMaxInactiveInterval(60 * 2);
        session.setAttribute("authCode", authCode);
        session.setAttribute("user_mail", user_mail);
        //发送者
        mainMessage.setFrom("huystar@126.com");
        //接收者
        mainMessage.setTo(user_mail);
        //发送的标题
        mainMessage.setSubject("找回密码");
        //发送的内容
        mainMessage.setText("验证码："+authCode+"您正在找回您的密码，输入验证码以继续");
        System.out.println(mainMessage+"////////////////////////////////////////////////////////////");
        System.out.println(mailSender+"-------------------------------------------");
        mailSender.send(mainMessage);
        System.out.println(mailSender);
        System.out.println("ok");
    }

    //验证注册用户
    @Override
    public boolean validationUser(HttpServletRequest request,String code){
        HttpSession session = request.getSession(true);
        String Code = (String) session.getAttribute("code");
        if(Code.equals(code)){
            return true;
        }else{
            return false;
        }
    }
    //判断邮箱是否已存在
    @Override
    public boolean selectEmail(String user_mail){
        System.out.println("user_mall:"+user_mail);
        User user = logUserRepository.selectEmail(user_mail);
        System.out.println(user);
        if(user != null){
            return true;
        }else{
            return false;
        }
    }

    //判断用户名是否已存在
    @Override
    public boolean selectName(String user_name) {
        User user = logUserRepository.selectName(user_name);
        if(user != null){
            return true;
        }else{
            return false;
        }
    }

    //验证用户验证吗和邮箱是否正确
    @Override
    public boolean contrastCode(HttpServletRequest request,String user_mail, String sessionMail, String usercode, String sessioncode) {
        HttpSession session = request.getSession();
        usercode = request.getParameter("user_code"); //获取用户输入的验证码
        sessioncode = (String) session.getAttribute("authCode"); //获取保存在session里面的验证码
        sessionMail = (String) session.getAttribute("user_mail");//获取保存在session里面的邮箱
        if (usercode != null && usercode.equals(sessioncode)&&user_mail != null && user_mail.equals(sessionMail)) { //对比两个code是否正确
           return true;
        } else {
            return false;
        }
    }
}
