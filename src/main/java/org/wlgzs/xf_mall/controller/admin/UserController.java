package org.wlgzs.xf_mall.controller.admin;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.wlgzs.xf_mall.entity.User;
import org.wlgzs.xf_mall.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Auther: 阿杰
 * @Date: 2018/4/15 13:19
 * @Description: 后台用户增删改查控制层
 */
@Controller
@RequestMapping("AdminUserController")
public class UserController {
    @Resource
    UserService userService;

    @RequestMapping("/")
    public String index() {
        return "redirect:/AdminUserController/adminUserList";
    }
    /**
     * @author 阿杰
     * @param [model]
     * @return java.lang.String
     * @description 后台遍历用户
     */
    @RequestMapping("/adminUserList")
    public String list(Model model, @RequestParam(value = "page",defaultValue = "0") int page,
                       @RequestParam(value = "limit",defaultValue = "10") int limit) {
        String user_name="";
        if(page != 0) page--;
        Page pages = userService.findUserPage(user_name,page,limit);
        model.addAttribute("TotalPages", pages.getTotalPages());//查询的页数
        model.addAttribute("Number", pages.getNumber()+1);//查询的当前第几页
        List<User> users = pages.getContent();
        model.addAttribute("users", users);//查询的当前页的集合
        return "admin/adminUserList";
    }
    /*
    批量添加
    @RequestMapping("/addUsers")
    public void addUsers(){
        List<User> users = new ArrayList<User>();
        User user = null;
        for (int i = 0; i < 100; i++) {
            user = new User();
            user.setUser_phone("test"+i);
            user.setUser_password("test"+(100-i));
            user.setUser_mail("test"+i);
            user.setUser_avatar("test"+i);
            user.setUser_role("test"+i);
            user.setUser_name("test"+i);
            users.add(user);
        }
        userService.save(users);
    }*/
    /**
     * @author 阿杰
     * @param [model, user_name]
     * @return java.lang.String
     * @description 搜索用户
     */
    @RequestMapping("/adminFindUser")
    public  String findUserName(Model model,String user_name,@RequestParam(value = "page",defaultValue = "0") int page,
                                @RequestParam(value = "limit",defaultValue = "10") int limit){
        if(page != 0) page--;
        Page pages = userService.findUserPage(user_name,page,limit);
        model.addAttribute("TotalPages", pages.getTotalPages());//查询的页数
        model.addAttribute("Number", pages.getNumber()+1);//查询的当前第几页
        List<User> users = pages.getContent();
        model.addAttribute("users", users);//查询的当前页的集合
        model.addAttribute("user_name",user_name);
        return "admin/adminUserList";
    }
    /**
     * @author 阿杰
     * @return java.lang.String
     * @description 跳转至增加用户页面
     */
    @RequestMapping("/toAdminAddUser")
    public String toAdd() {
        return "admin/adminAddUser";
    }
    /**
     * @author 阿杰
     * @param [user]
     * @return java.lang.String
     * @description 后台增加用户
     */
    @RequestMapping("/adminAddUser")
    public String add(User user) {
        userService.save(user);
        return "redirect:/AdminUserController/adminUserList";
    }

    /**
     * @author 阿杰
     * @param [model, id]
     * @return java.lang.String
     * @description 跳转至修改用户页面
     */
    @RequestMapping("/toAdminEditUser")
    public String toEdit(Model model, Long userId) {
        User user = userService.findUserById(userId);
        if(user != null){
            model.addAttribute("user", user);
            return "admin/adminEditUser";
        }else{
            model.addAttribute("mag","该用户不存在");
            return "redirect:/AdminUserController/adminUserList";
        }
    }
    /**
     * @author 阿杰
     * @param [user]
     * @return java.lang.String
     * @description 后台修改用户
     */
    @RequestMapping("/adminEditUser")
    public String edit(Model model,User user) {
        String mag = userService.edit(user);
        model.addAttribute("mag",mag);
        return "redirect:/AdminUserController/adminUserList";
    }
    /**
     * @author 阿杰
     * @param [id]
     * @return java.lang.String
     * @description 后台删除用户
     */
    @RequestMapping("/adminDeleteUser")
    public String delete(Model model,Long userId, HttpServletRequest request) {
        String mag = userService.delete(userId, request);
        model.addAttribute("mag",mag);
        return "redirect:/AdminUserController/adminUserList";
    }
    /**     
     * @author 胡亚星
     * @date 2018/5/24 11:06  
     * @param   
     * @return   
     *@Description:后台批量删除用户
     */
    @RequestMapping("/adminDeleteUsers")
    public String adminDeleteUsers(@RequestParam(value = "userId",defaultValue = "439,449") String userId){
        userService.adminDeleteUsers(userId);
        return "redirect:/AdminUserController/adminUserList";
    }
}
