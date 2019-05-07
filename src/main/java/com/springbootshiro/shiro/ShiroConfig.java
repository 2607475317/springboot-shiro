package com.springbootshiro.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;


@Configuration//标志这是配置类
public class ShiroConfig {
    /**
     * 1、创建ShiroFilterFactoryBean
     */
    @Bean
  public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("DefaultWebSecurityManager") DefaultWebSecurityManager securityManager){
      ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
      //设置安全管理器
      shiroFilterFactoryBean.setSecurityManager(securityManager);
      /**
       * 添加shiro内置过滤器，可以实现权限拦截
       * 常用过滤器：
       *           anon:无需认证（登录）就可以访问
       *           authc:必须认证才能访问
       *           user:如果使用remember me才能够正常使用
       *           perms:改资源必须得到资源权限才可以访问
       *           role:该角色必须得到角色授权才可以访问
       */
      Map<String,String> filtermap=new LinkedHashMap<String,String>();
      filtermap.put("/add","authc");//设置add请求拦截
      filtermap.put("/update","authc");
      //拦截后默认跳转到login.jsp，调整默认跳转页面
      shiroFilterFactoryBean.setLoginUrl("/tologin");
      //授权过滤器，注意，授权拦截后，会自动跳转到未授权的页面
        filtermap.put("/add","perms[user:add]");//对add请求进行拦截
        filtermap.put("/update","perms[user:update]");
        //设置未授权提示页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/Auth");
      shiroFilterFactoryBean.setFilterChainDefinitionMap(filtermap);
      return shiroFilterFactoryBean;
  }
    /**
     * 2、创建DefaultWebSecurityManager
     */
    @Bean(name="DefaultWebSecurityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userReaml") UserReaml userReaml){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        //关联reaml
        securityManager.setRealm(userReaml);
        return securityManager;
    }
    /**
     * 3、创建一个Reaml
     */
    @Bean(name="userReaml")//添加到容器中并赋予一个名字
    public UserReaml getReaml(){
        return new UserReaml();
    }

    /**
     * 配置ShiroDialect,用于themeleaf和shiro配合使用
     */
    @Bean
    public ShiroDialect getShiroDialect(){
        return  new ShiroDialect();
    }

}
