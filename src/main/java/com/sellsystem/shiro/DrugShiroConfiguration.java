package com.sellsystem.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
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
        shiroFilterFactoryBean.setSuccessUrl("/index");
        //未授权界面
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");

        //拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //过滤链定义，从上往下顺序执行，一般将/**放在最下边。这里需要注意
        //<！--authc:所有url都必须认知通过才可以访问；anon:所有url都可以匿名访问-->
//        filterChainDefinitionMap.put("classpath:/static", "anon");
        filterChainDefinitionMap.put("/bootstrap/**", "anon");
        filterChainDefinitionMap.put("/dist/**", "anon");
        filterChainDefinitionMap.put("/images/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/plugins/**", "anon");

        //--------------------------- "/login"，登陆是不能忽略的，否则的话是不会执行realm的---------------------------------------
       // filterChainDefinitionMap.put("/login", "anon");

        //配置退出过滤器，其中的具体退出逻辑shiro已经实现了
        filterChainDefinitionMap.put("/logout", "logout");
//        filterChainDefinitionMap.put("/add", "perms[权限添加]");
        filterChainDefinitionMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        System.out.println("-------------------------Shiro拦截器工厂类注入成功------------------------------");
        return shiroFilterFactoryBean;
    }

    /**
     * securityManager:shiro的核心
     * @return
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(drugShiroRealm());
        //注入缓存管理器
        securityManager.setCacheManager(ehcacheManager());//这个如果执行多次，也是同样的一个对象
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
        //注入凭证匹配器（密码是明文的不需要注入）
        //drugShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return drugShiroRealm;
    }

    /**
     * 凭证匹配器
     * （由于密码校验交给shiro的SimpleAuthenticationInfo进行处理了，所以我们需要修改下doGetAuthenticationInfo的代码）
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法；这里使用MD5算法；
        hashedCredentialsMatcher.setHashIterations(2);//散列的次数，比如两次：md5(md5(""))
        return hashedCredentialsMatcher;
    }

    /**
     * 开启shiro aop注解支持
     * 使用代理方式；所以需要开启代码支持
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor sourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        sourceAdvisor.setSecurityManager(securityManager);
        return sourceAdvisor;
    }

    /**
     * shiro缓存管理器
     * 需要注入对应的其他的实体类中:
     * 1、安全管理器：SecurityManager
     * 可见securityManager是整个shiro的核心；
     * @return
     */
    @Bean
    public EhCacheManager ehcacheManager() {
        System.out.println("-------------------------------EhCacheManager.ehcacheManager()------------");
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
        return ehCacheManager;
    }


}
