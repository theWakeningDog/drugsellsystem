package com.sellsystem.shiro;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Apache Shiro 核心通过 Filter 来实现，就好像SpringMvc 通过DispachServlet 来主控制一样。
 * 既然是使用 Filter 一般也就能猜到，是通过URL规则来进行过滤和权限校验，所以我们需要定义一系列关于URL的规则和访问权限。
 * Created by zhangwei on 2017/4/25.
 */
@Configuration
public class DrugShiroConfiguration {

    /**
     * anon:所有url都都可以匿名访问;
     * authc: 需要认证才能进行访问;
     * user:配置记住我或认证通过可以访问
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        System.out.println("-----------------------------------------------DrugShiroConfiguration.shiroFilter()--------------------------------------------------------");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //必须设置SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //如果不设置默认会自动寻找Web工程根目录下的"login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        //登陆成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/login/home");
        //未授权界面
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");

        //拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //过滤链定义，从上往下顺序执行，一般将/**放在最下边。这里需要注意
        //<！--authc:所有url都必须认知通过才可以访问；anon:所有url都可以匿名访问-->
//        filterChainDefinitionMap.put("/static/**", "anon");
//        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/bootstrap/**", "anon");
        filterChainDefinitionMap.put("/dist/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/plugins/**", "anon");
        //配置退出过滤器，其中的具体退出逻辑shiro已经实现了
        filterChainDefinitionMap.put("/logout", "logout");
//        filterChainDefinitionMap.put("/add", "perms[权限添加]");
        filterChainDefinitionMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        System.out.println("-------------------------------------------------------Shiro拦截器工厂类注入成功---------------------------------------------------------");
        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(drugShiroRealm());
        return securityManager;
    }

    /**
     * 身份认证realm; (这个需要自己写，账号密码校验；权限等)
     *
     * @return
     */
    @Bean
    public DrugShiroRealm drugShiroRealm() {
        DrugShiroRealm drugShiroRealm = new DrugShiroRealm();
        return drugShiroRealm;
    }
}
