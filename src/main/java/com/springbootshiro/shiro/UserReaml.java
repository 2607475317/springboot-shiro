package com.springbootshiro.shiro;


import com.springbootshiro.pojo.User;
import com.springbootshiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

public class UserReaml extends AuthorizingRealm {
    /**
     * 执行授权逻辑
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行授权");
        //给资源进行授权
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        //授权方式一：添加资源的授权字符串,注意要和ShiroConfig的授权字符串相同
         //info.addStringPermission("user:add");

        //授权方式二获取当前登录用户
        Subject subject = SecurityUtils.getSubject();
        User user  =(User) subject.getPrincipal();
        //到数据库查询当前用户内的授权字符串
        User byId = userService.findById(user.getId());
        info.addStringPermission(byId.getPerms());
        return info;
    }
    @Autowired
    private UserService userService;

    /**
     * 执行认证逻辑
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行认证逻辑");


        //编写shiro判断逻辑，判断用户名和密码
        //1、判断用户名
        UsernamePasswordToken token= (UsernamePasswordToken) authenticationToken;
        User byName = userService.findByName(token.getUsername());
        if (byName==null){
              //用户名不存在
            return null;//shiro会抛出一个UnknownAccountException异常
        }
        //2、判断密码

       return new SimpleAuthenticationInfo(byName,byName.getPassword(),"");
    }
}
