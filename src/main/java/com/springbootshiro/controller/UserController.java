package com.springbootshiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class UserController {
    @RequestMapping("/text")
    public String text(Model model){
        model.addAttribute("name","哈哈哈");
        return "text";

    }
    @RequestMapping("/add")
    public String add(){

        return "user/add";

    }
    @RequestMapping("/update")
    public String update(){

        return "user/update";

    }
    @RequestMapping("/tologin")
    public String tologin(){

        return "login";

    }

    /**
     * 登录逻辑处理
     * @param name
     * @param password
     * @return
     */
    @RequestMapping("/login")
    public String login(String name,String password,Model model) {
        System.out.println("name"+name);
        System.out.println("password"+password);
        /**
         * 使用shiro编写认证操作
         */
        //获取subject
        org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
        //封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(name, password);
        //执行登录方法
        try {
            subject.login(token);
            //执行成功，跳转到text.html
            return "redirect:/text";
        } catch (UnknownAccountException e) {
            model.addAttribute("msg", "用户名不存在");
            return "login";//要将消息传递到前端页面，不能重定向
        } catch (IncorrectCredentialsException e) {
            model.addAttribute("msg", "密码错误");
            return "login";
        }

    }
    //未授权页面
    @RequestMapping("/Auth")
    public String auth(){
        return "noAuth";
    }


}
