package org.wlgzs.xf_mall.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.wlgzs.xf_mall.entity.User ;

import java.util.List;

/**
 * @Auther: 阿杰
 * @Date: 2018/4/15 13:28
 * @Description: 后台增删改查service层
 */
public interface UserService {

    User findUserById(long userId);

    void save(User user);

    void edit(User user);

    void delete(long userId);

    void save(List<User> users);

    Page<User> findUserPage(String user_name,int pa,int limit);

    //修改用户头像路径
    void modifyAvatar(String user_avatar,long userId);

    //判断修改密码密码是否正确
    boolean checkPassWord(String user_password,long userId);

    //修改用户密码
    void changePassword(String user_password,long userId);

    //修改用户密码
    void changePassword(String user_password,String user_mail);

    //修改邮箱
    void changeEmail(String user_mail,Long userId);
}
