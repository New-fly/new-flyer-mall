package org.wlgzs.xf_mall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.wlgzs.xf_mall.entity.User ;
import org.wlgzs.xf_mall.dao.UserRepository;
import org.wlgzs.xf_mall.service.UserService;
import org.wlgzs.xf_mall.util.PageUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

/**
 * @Auther: 阿杰
 * @Date: 2018/4/15 13:42
 * @Description: 用户增删改查的实现方法
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    //后台增加用户
    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    //后台按照id查找用户
    @Override
    public User findUserById(long userId) {
        return userRepository.findById(userId);
    }

    //后台修改用户
    @Override
    public void edit(User user) {
        userRepository.saveAndFlush(user);
    }

    //后台删除用户
    @Override
    public void delete(long userId, HttpServletRequest request) {
        User user = userRepository.findById(userId);
        String img = user.getUser_avatar();
        String path = request.getSession().getServletContext().getRealPath("/");
        File file = new File(path+""+img.substring(1,img.length()));
        System.out.println(path+""+img.substring(1,img.length()));
        if (!img.equals("/headPortrait/morende.jpg") && file.exists() && file.isFile()) {
            file.delete();
        }
        userRepository.deleteById(userId);
    }

    @Override
    public void save(List<User> users) {
        userRepository.saveAll(users);
    }

    //后台遍历用户，后台分页搜索用户
    @Override
    public Page<User> findUserPage(String user_name, int page, int limit) {
        /*Sort sort = null;
        Pageable pageable = null;
        Page<User> page = null;
        if (user_name.equals("")) {
            sort = new Sort(Sort.Direction.DESC, "userId");
            pageable = new PageRequest(page, limit, sort);
            return userRepository.findAll(pageable);
        } else {
            sort = new Sort(Sort.Direction.ASC, "userId");
            pageable = new PageRequest(page, limit, sort);

            Specification<User> specification = new Specification<User>() {
                @Override
                public Predicate toPredicate(Root<User> root,
                                             CriteriaQuery<?> criteriaQuery,
                                             CriteriaBuilder criteriaBuilder) {
                    Predicate predicate = criteriaBuilder.like(root.get("user_name"), "%" + user_name + "%");
                    return criteriaBuilder.and(predicate);
                }
            };
            return userRepository.findAll(specification, pageable);
        }*/
        Sort sort = new Sort(Sort.Direction.DESC, "userId");
        Pageable pageable = new PageRequest(page,limit, sort);
        Specification<User> specification = new PageUtil<User>(user_name).getPage("user_name");
        Page pages = userRepository.findAll(specification,pageable);
        return pages;
    }

    @Override
    public void modifyAvatar(String user_avatar,long userId) {
        userRepository.ModifyAvatar(user_avatar,userId);
    }

    @Override
    public boolean checkPassWord(String user_password, long userId) {
        User user = userRepository.checkPassWord(user_password,userId);
        if(user != null){//正确
            return true;
        }else{//错误
            return false;
        }
    }

    @Override
    public void changePassword(String user_rePassword, long userId) {
        userRepository.changePassword(user_rePassword,userId);
    }

    @Override
    public void changePassword(String user_password, String user_mail) {
        userRepository.changePassword(user_password,user_mail);
    }

    @Override
    public void changeEmail(String user_mail, long userId) {
        System.out.println("123123");
        System.out.println(user_mail);
        System.out.println(userId);
        userRepository.changeEmail(user_mail,userId);
    }
}


