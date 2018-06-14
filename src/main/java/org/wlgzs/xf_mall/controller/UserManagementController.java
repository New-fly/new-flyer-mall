package org.wlgzs.xf_mall.controller;


import org.apache.commons.beanutils.BeanUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.wlgzs.xf_mall.base.BaseController;
import org.wlgzs.xf_mall.entity.User;
import org.wlgzs.xf_mall.util.CheckImage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author:胡亚星
 * @createTime 2018-04-22 16:18
 * @description: 用户信息修改控制层
 **/
@RequestMapping("UserManagementController")
@RestController
public class UserManagementController extends BaseController {

    /**
     * @param
     * @return
     * @author 胡亚星
     * @date 2018/4/23 21:29
     * @Description: 展示用户信息
     */
    @RequestMapping("information")
    public ModelAndView displayInformation(Model model, Long userId) {
        User user = userService.findUserById(userId);
        model.addAttribute("user", user);
        return new ModelAndView("information");
    }

    /**
     * @param
     * @return
     * @author 胡亚星
     * @date 2018/5/1 8:16
     * @Description: 修改用户名(个人信息展示页面)
     */
    @RequestMapping("changeInformation")
    public ModelAndView ModifyName(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        userService.ModifyName(request,user);
        model.addAttribute("user", user);
        return new ModelAndView("information");
    }


    /**
     * @param
     * @return
     * @author 胡亚星
     * @date 2018/5/1 8:11
     * @Description: 点击用户头像跳转到头像修改页面
     */
    @RequestMapping("toModifyAvatar")
    public ModelAndView toModifyAvatar(Model model, Long userId) {
        User user = userService.findUserById(userId);
        model.addAttribute("user", user);
        return new ModelAndView("ModifyAvatar");
    }


    /**
     * @param
     * @return
     * @author 胡亚星
     * @date 2018/4/23 21:28
     * @Description: 实现用户头像的修改 文件上传，替换原来的地址
     */
    @RequestMapping("/ModifyAvatar")
    public ModelAndView add(@RequestParam("file") MultipartFile myFileName, HttpSession session,
                            Model model, HttpServletRequest request) throws IOException {
        String fileName = myFileName.getOriginalFilename();
        CheckImage checkImage = new CheckImage();
        User user = null;
        if(checkImage.verifyImage(fileName)){
            user = userService.ModifyAvatar(session,request,myFileName);
        }else{
            user = (User) session.getAttribute("user");
            model.addAttribute("mag","文件格式不正确");
        }
        model.addAttribute("user", user);
        return new ModelAndView("information");
    }

    /**
     * @author 胡亚星
     * @date 2018/5/8 21:55
     * @param
     * @return
     *@Description: 跳转到安全设置
     */
    @RequestMapping("toSafety")
    public ModelAndView toSafety(Model model, Long userId){
        User user = userService.findUserById(userId);
        model.addAttribute("user", user);
        return new ModelAndView("safety");
    }

    /**
     * @author 胡亚星
     * @date 2018/5/9 17:51
     * @param
     * @return
     *@Description: 跳转到修改邮箱
     */
    @RequestMapping("toChangeEmail")
    public ModelAndView toChangeEmail(){
        System.out.println("123123");
        return new ModelAndView("changeEmail");
    }

    /**
     * @param
     * @return
     * @author 胡亚星
     * @date 2018/4/27 20:06
     * @Description: 修改邮箱发送验证码
     */
    @RequestMapping("sendChangeEmail")
    public void sendChangeEmail(HttpServletRequest request) {
        String user_mail = request.getParameter("user_mail");
        logUserService.sendEmail1(request, user_mail);//发送
    }

    /**     
     * @author 胡亚星
     * @date 2018/5/9 17:54  
     * @param   
     * @return
     *@Description: 实现修改邮箱
     */
    @RequestMapping("changeEmail")
    public ModelAndView changeEmail(Model model,HttpServletRequest request){
        //检查邮箱是否存在
        HttpSession session = request.getSession();
        String user_mail = request.getParameter("user_mail");
        String sessionMail = "";
        String usercode = "";
        String sessioncode = "";
        if (logUserService.contrastCode(request,user_mail,sessionMail,usercode,sessioncode)){
            System.out.println(logUserService.selectEmail(user_mail)+"--------------------");
            if(!logUserService.selectEmail(user_mail)){//可以继续
                User user1 = (User) session.getAttribute("user");
                long userId = user1.getUserId();
                model.addAttribute("mgs", "修改成功");
                userService.changeEmail(user_mail,userId);//修改
                User user = userService.findUserById(userId);
                model.addAttribute("user", user);
                return new ModelAndView("information");
            }else{
                model.addAttribute("mgs", "修改失败");
                String url = "redirect:/UserManagementController/toChangeEmail";
                return new ModelAndView(url);
            }
        }else{
            model.addAttribute("mgs", "验证码错误");
            String url = "redirect:/UserManagementController/toChangeEmail";
            return new ModelAndView(url);
        }
    }
    
    /**     
     * @author 胡亚星
     * @date 2018/5/5 10:17  
     * @param   
     * @return   
     *@Description: 跳转到修改密码
     */
    @RequestMapping("toChangePassword")
    public ModelAndView toChangePassword(){
        return new ModelAndView("changePassword");
    }

    /**+
     * @param
     * @return
     * @author 胡亚星
     * @date 2018/5/2 18:03
     * @Description: 修改密码,判断原先密码是否正确
     */
    @RequestMapping("checkPassword")
    public ModelAndView checkPassword(Model model, HttpServletRequest request) {
        String user_password = request.getParameter("user_password");
        String user_rePassword = request.getParameter("user_rePassword");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        long userId = user.getUserId();
        System.out.println(user_password);
        System.out.println(user_rePassword);
        if (userService.checkPassWord(user_password, userId)) {//正确ze修改密码
            System.out.println("正确");
            //model.addAttribute("mgs", "修改成功");
            userService.changePassword(user_rePassword, userId);
            return new ModelAndView("redirect:/toLogin");
        } else {//原密码错误
            model.addAttribute("userId",userId);
            model.addAttribute("mgs", "原密码错误");
            return new ModelAndView("redirect:/toChangePassword");
        }
    }

    /**
     * @param
     * @return
     * @author 胡亚星
     * @date 2018/5/2 18:19
     * @Description: 用户电话的修改
     */
    @RequestMapping("changePhone")
    public ModelAndView changePhone(Model model, HttpServletRequest request) {
        //修改手机号
        User user = userService.changePhone(request);
        model.addAttribute("user", user);
        return new ModelAndView("information");
    }

    /**
     * @param
     * @return
     * @author 胡亚星
     * @date 2018/5/3 8:51
     * @Description: 跳转到找回密码页面
     */
    @RequestMapping("toSendRetrievePassword")
    public ModelAndView toSendRetrievePassword() {
        return new ModelAndView("toSendRetrievePassword");
    }

    /**
     * @param
     * @return
     * @author 胡亚星
     * @date 2018/5/3 8:43
     * @Description: 找回密码发送邮箱
     */
    @ResponseBody
    @RequestMapping(value = "sendRetrievePassword", method = RequestMethod.POST)
    public String sendRetrievePassword(HttpServletRequest request) {
        String user_mail = request.getParameter("user_mail");
        //判断邮箱是否存在
        if(!logUserService.selectEmail(user_mail)){
            System.out.println("进入");
            System.out.println(user_mail);
            logUserService.sendEmail2(request, user_mail);//发送
            return "成功";
        }
        return "该邮箱还未注册";
    }


    /**
     * @param
     * @return
     * @author 胡亚星
     * @date 2018/5/2 17:57
     * @Description: 找回密码验证
     */
    @RequestMapping("passwordContrastCode")
    public ModelAndView passwordContrastCode(Model model, HttpServletRequest request) {

        HttpSession session = request.getSession();
        String user_mail = request.getParameter("user_mail");
        String sessionMail = "";
        String usercode = "";
        String sessioncode = "";
        if (logUserService.contrastCode(request,user_mail,sessionMail,usercode,sessioncode) ){ //对比两个code是否正确
            model.addAttribute("user_mail", user_mail);
            System.out.println("222");
            return new ModelAndView("retrievePassword");
        } else {
            model.addAttribute("mag", "请检查您的验证码或邮箱是否正确");
            System.out.println("111");
            return new ModelAndView("toSendRetrievePassword");
        }
    }

    /**
     * @param
     * @return
     * @author 胡亚星
     * @date 2018/5/3 8:46
     * @Description: 重置密码
     */
    @RequestMapping("retrievePassword")
    public ModelAndView retrievePassword(Model model, HttpServletRequest request, String user_mail) {
        String user_password = request.getParameter("user_password");
        System.out.println(user_mail);
        System.out.println(user_password);
        userService.changePassword(user_password, user_mail);
        model.addAttribute("mgs", "修改成功");
        return new ModelAndView("login");
    }


}
