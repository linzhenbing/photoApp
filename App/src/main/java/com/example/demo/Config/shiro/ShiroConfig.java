package com.example.demo.Config.shiro;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    /**
     * 创建ShiroFilterFactoryBean
     * @return
     */
    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("defaultWebSecurityManager")DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

        /**
         * 添加Shiro过滤器，过滤器可以对一些权限进行拦截
         * 常用过滤器：
         * 1.anon：无需认证（无需登录）就可以访问
         * 2.authc：必须认证才可以访问
         * 3.user：如果使用了RememberMe的功能，就可以直接访问
         * 4.perms：必须授予资源权限才可以访问
         * 5.role：必须得到角色权限才可以访问
         */
        Map<String, String> filterMap = new LinkedHashMap<String,String>();
        filterMap.put("/user/getUser","perms[super]");
        filterMap.put("/user/forbidUser","perms[super]");
        filterMap.put("/user/enableUser","perms[super]");
        filterMap.put("/user/deleteUser","perms[super]");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 创建DefaultWebSecurityManager
     * @return
     */
    @Bean(name = "defaultWebSecurityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm")UserRealm userRealm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        /*关联一个Realm*/
        defaultWebSecurityManager.setRealm(userRealm);
        return defaultWebSecurityManager;
    }

    /**
     * 创建Realm
     */
    @Bean(name = "userRealm")
    public UserRealm getRealm(){
        return new UserRealm();
    }

    /**
     * 注入配置自定义的密码比较器
     */
    @Bean("credentialsMatcher")
    public CredentialsMatcher credentialsMatcher(){
        return new CredentialsMatcher();
    }

}
