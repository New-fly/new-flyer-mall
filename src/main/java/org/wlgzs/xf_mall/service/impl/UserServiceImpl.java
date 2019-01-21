package org.wlgzs.xf_mall.service.impl;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.wlgzs.xf_mall.dao.LogUserRepository;
import org.wlgzs.xf_mall.dao.ShippingAddressRepository;
import org.wlgzs.xf_mall.dao.UserRepository;
import org.wlgzs.xf_mall.entity.User;
import org.wlgzs.xf_mall.filter.DemoFilter;
import org.wlgzs.xf_mall.service.UserService;
import org.wlgzs.xf_mall.util.IdsUtil;
import org.wlgzs.xf_mall.util.PageUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther: 阿杰
 * @Date: 2018/4/15 13:42
 * @Description: 用户增删改查的实现方法
 */
@Service
public class UserServiceImpl implements UserService {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(DemoFilter.class);

    @Resource
    private UserRepository userRepository;

    @Resource
    private LogUserRepository logUserRepository;

    @Resource
    private ShippingAddressRepository shippingAddressRepository;

    //后台增加用户
    @Override
    public void save(User user) {
        user.setUser_avatar("/api/path/headPortrait/picture.jpg");
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
        User userTwo = userRepository.findById(user.getUserId());
        if(userTwo != null){
            if(userTwo.getUser_role().equals("超级管理员")){
                user.setUser_role("超级管理员");
            }
            user.setUser_avatar(userTwo.getUser_avatar());
            user.setUserIntegral(userTwo.getUserIntegral());
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
            if(img!=null && !img.equals("/api/path/headPortrait/picture.jpg")){
                new IdsUtil().deleteFile(img.substring(9));
            }
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
            pageable = PageRequest.of(page, limit, sort);
            return userRepository.findAll(pageable);
        } else {
            sort = new Sort(Sort.Direction.ASC, "userId");
            pageable = PageRequest.of(page, limit, sort);

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
        Pageable pageable = PageRequest.of(page,limit, sort);
        Specification<User> specification = new PageUtil<User>(user_name).getPage("user_name","user_role","user_mail");
        return userRepository.findAll(specification,pageable);
    }

    @Override
    public boolean checkPassWord(String user_password, long userId) {
        User user = userRepository.checkPassWord(user_password,userId);
        return user != null;
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
        userRepository.changeEmail(user_mail,userId);
    }

    @Override
    public void ModifyName(HttpServletRequest request,User user) {
        Map<String, String[]> properties = request.getParameterMap();
        try {
            BeanUtils.populate(user, properties);
        } catch (IllegalAccessException | InvocationTargetException e) {
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
        String realName;
        String user_avatar;
        if (!Objects.equals(myFileName.getOriginalFilename(), "")) {
            String fileName = myFileName.getOriginalFilename();
            assert fileName != null;
            String fileNameExtension = fileName.substring(fileName.indexOf("."));
            // 生成实际存储的真实文件名
            realName = UUID.randomUUID().toString() + fileNameExtension;
            user_avatar = "/upload/headPortrait/" + realName;
            File uploadFile = new File("." + user_avatar);
            IdsUtil.writeFile(myFileName, uploadFile);
        } else {
            user_avatar = "/upload/headPortrait/picture.jpg";
        }
        Map<String, String[]> properties = request.getParameterMap();
        User user = userRepository.findById(id);
        try {
            BeanUtils.populate(user, properties);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        if(user.getUser_avatar()!=null && !user.getUser_avatar().equals("/api/path/headPortrait/picture.jpg")){
            new IdsUtil().deleteFile(user.getUser_avatar().substring(9));
        }
        user.setUser_avatar("/api/path" + user_avatar);
        userRepository.ModifyAvatar("/api/path" + user_avatar,id);
        return user;
    }

    @Override
    public void adminDeleteUsers(String userId) {
        long[] Ids = IdsUtil.IdsUtils(userId);
        userRepository.deleteByIds(Ids);
    }

    @Override
    public User changePhone(HttpServletRequest request) {
        String id = request.getParameter("userId");
        long userId = Long.parseLong(id);
        String user_phone = request.getParameter("user_phone");
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(user_phone);
        if(m.matches() && user_phone.equals("")){
            userRepository.changePhone(user_phone,userId);
        }
        return userRepository.findById(userId);
    }
}


