package org.wlgzs.xf_mall.service;

import org.springframework.ui.Model;
import org.wlgzs.xf_mall.entity.User;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author:胡亚星
 * @createTime 2018-04-16 21:30
 * @description:用户登陆service层
 **/
public interface LogUserService {

    User login(HttpServletRequest request,String user_name, String user_password);

    void cancellation(HttpServletRequest request);

    void adminCancellation(HttpServletRequest request);
    //验证注册用户
    boolean validationUser(HttpServletRequest request,String code);

    //发送验证码到邮箱
    void sendEmail(HttpServletRequest request,String user_mail);

    //发送验证码到邮箱
    void sendEmail1(HttpServletRequest request,String user_mail);

    //发送验证码到邮箱
    void sendEmail2(HttpServletRequest request,String user_mail);

    //判断邮箱是否已存在
    boolean selectEmail(String user_mail);

    //判断用户名是否存在
    boolean selectName(String user_name);

    //验证用户验证吗和邮箱是否正确
    boolean contrastCode(HttpServletRequest request,String user_mail,String sessionMail,String usercode,String sessioncode);

    //注册
    User registered(User user,HttpServletRequest request);

    //根据邮箱查询用户id
    long getUserId(String user_mail);

    //id实现登陆
    void loginId(HttpServletRequest request,long userId);

    //github添加新账号
    String githubRegistered(HttpServletRequest request, String user_mail, String user_password);
}
