package com.sellsystem.controller;

import com.sellsystem.entity.User;
import com.sellsystem.service.UserService;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by zhangwei on 2017/4/27.
 */
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/","/index"})
    public String index(){
        return "/index";
    }

    @RequestMapping(value="/login",method= RequestMethod.GET)
    public String login(){
        return "/login";
    }

    @GetMapping("/register")
    public String register() {
        return "/register";
    }

    @PostMapping("/register")
    public String register(User user, Map map) {
        User u = userService.getUserByAccount(user.getAccount()).getData();
        if (null == u) {
        userService.create(user);
        return "redirect:/login";
        } else {
            map.put("msg", "该账号已注册");
            return "/register";
        }
    }

    // 登录提交地址和applicationontext-shiro.xml配置的loginurl一致。 (配置文件方式的说法)
    @PostMapping(value = "/login")
    public String login(HttpServletRequest request, Map<String, Object> map) {
        System.out.println("-------------------------------LoginController.login()");
        //登陆失败从request中获取shiro处理的异常信息
        //shiroLoginFailure:shiro异常类全类名
        String exception = (String) request.getAttribute("shiroLoginFailure");
        System.out.println("---------------------exception：" + exception);
        String msg = "";
        if (null != exception) {
            if (UnknownAccountException.class.getName().equals(exception)) {
                msg = "UnknownAccountException---->账号不存在";
            } else if (IncorrectCredentialsException.class.getName().equals(exception)) {
                msg = "IncorrectCredentialsException--------->密码不正确";
            }else if ("kaptchaValidateFailed".equals(exception)) {
                msg = "kaptchaValidateFailed -- > 验证码错误";
            } else {
                msg = "else >> "+exception;
            }
        }
        map.put("msg", msg);
        //此方法不处理登陆成功，由shiro进行处理
        return "/login";
    }

     /**
     * 首页
     * @return
     */
    @RequestMapping("/login/home")
    public String home(Model model) {
        model.addAttribute("login", "login in  project");
        return "home";
    }

}
