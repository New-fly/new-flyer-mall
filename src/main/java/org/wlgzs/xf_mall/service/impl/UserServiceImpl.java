package org.wlgzs.xf_mall.service.impl;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.wlgzs.xf_mall.dao.LogUserRepository;
import org.wlgzs.xf_mall.dao.ShippingAddressRepository;
import org.wlgzs.xf_mall.entity.User ;
import org.wlgzs.xf_mall.dao.UserRepository;
import org.wlgzs.xf_mall.service.UserService;
import org.wlgzs.xf_mall.util.PageUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Auther: 阿杰
 * @Date: 2018/4/15 13:42
 * @Description: 用户增删改查的实现方法
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LogUserRepository logUserRepository;

    @Autowired
    private ShippingAddressRepository shippingAddressRepository;

    //后台增加用户
    @Override
    public void save(User user) {
        user.setUser_avatar("/headPortrait/morende.jpg ");
        userRepository.save(user);
    }

    //后台按照id查找用户
    @Override
    public User findUserById(long userId) {
        return userRepository.findById(userId);
    }

    //后台修改用户
    @Override
    public String edit(User user) {
        User user0 = userRepository.findById(user.getUserId());
        if(user0 != null){
            userRepository.saveAndFlush(user);
            return "成功";
        }
        return "失败";
    }

    //后台删除用户
    @Override
    public String delete(long userId, HttpServletRequest request) {
        User user = userRepository.findById(userId);
        if(user != null){
            String img = user.getUser_avatar();
            String path = request.getSession().getServletContext().getRealPath("/");
            if(img!=null){
                File file = new File(path+""+img.substring(1,img.length()));
                System.out.println(path+""+img.substring(1,img.length()));
                if (!img.equals("/headPortrait/morende.jpg") && file.exists() && file.isFile()) {
                    file.delete();
                }
            }
            System.out.println(123);
            userRepository.deleteById(userId);
            return "成功";
        }
        return "失败";
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
        Specification<User> specification = new PageUtil<User>(user_name).getPage("user_name","user_role","user_mail");
        Page pages = userRepository.findAll(specification,pageable);
        return pages;
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

    @Override
    public void ModifyName(HttpServletRequest request,User user) {

        Map<String, String[]> properties = request.getParameterMap();
        System.out.println(user);
        try {
            BeanUtils.populate(user, properties);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        String user_name1 = user.getUser_name();
        String user_name = request.getParameter("user_name");
        //判断用户名是否存在
        if(user_name1 != null && !user_name1.equals("")){
            if(logUserRepository.selectName(user_name) == null){
                user.setUser_name(user_name);
                userRepository.saveAndFlush(user);
                //修改收货地址表中的用户名
                shippingAddressRepository.modifyName(user_name,user_name1);
                //从新存入session
                HttpSession session = request.getSession(true);
                session.setMaxInactiveInterval(60 * 20);
                session.setAttribute("name", user.getUser_name());//之后用过滤器实现
                session.setAttribute("userId", user.getUserId());
                session.setAttribute("user",user);
            }
        }
    }

    @Override
    public User ModifyAvatar(HttpSession session, HttpServletRequest request, MultipartFile myFileName) throws IOException {
        String userId = request.getParameter("userId");
        long id = Long.parseLong(userId);
        String realName = "";
        String user_avatar = "";
        if (!myFileName.getOriginalFilename().equals("")) {
            String fileName = myFileName.getOriginalFilename();
            String fileNameExtension = fileName.substring(fileName.indexOf("."), fileName.length());

            // 生成实际存储的真实文件名
            realName = UUID.randomUUID().toString() + fileNameExtension;

            // "/upload"是你自己定义的上传目录
            String realPath = session.getServletContext().getRealPath("/headPortrait");
            File uploadFile = new File(realPath, realName);
            myFileName.transferTo(uploadFile);
            user_avatar = request.getContextPath() + "/headPortrait/" + realName;
        } else {
            user_avatar = request.getContextPath() + "/headPortrait/" + "morende.jpg";
        }
        System.out.println(user_avatar);
        Map<String, String[]> properties = request.getParameterMap();
        User user = userRepository.findById(id);
        try {
            BeanUtils.populate(user, properties);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        user.setUser_avatar(user_avatar);
        userRepository.ModifyAvatar(user_avatar,id);
        return user;
    }
}


