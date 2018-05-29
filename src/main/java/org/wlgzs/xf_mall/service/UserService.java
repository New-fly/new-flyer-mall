package org.wlgzs.xf_mall.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.MultipartFile;
import org.wlgzs.xf_mall.entity.User ;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @Auther: 阿杰
 * @Date: 2018/4/15 13:28
 * @Description: 后台增删改查service层
 */
public interface UserService {

    User findUserById(long userId);

    void save(User user);

    String edit(User user);

    String delete(long userId, HttpServletRequest request);

    void save(List<User> users);

    Page<User> findUserPage(String user_name,int pa,int limit);

    //判断修改密码密码是否正确
    boolean checkPassWord(String user_password,long userId);

    //修改用户密码
    void changePassword(String user_rePassword,long userId);

    //修改用户密码
    void changePassword(String user_password,String user_mail);

    //修改邮箱
    void changeEmail(String user_mail,long userId);

    //修改用户名
    void ModifyName(HttpServletRequest request,User user);

    //修改用户头像
    User ModifyAvatar(HttpSession session,HttpServletRequest request,MultipartFile myFileName) throws IOException;

    //后台批量删除用户
    void adminDeleteUsers(String userId);

    //修改手机号
    User changePhone(HttpServletRequest request);
}
