package com.example.demo.Config;

import com.example.demo.Config.intercepors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;
    /*这个方法是用来设置静态资源的,比如html/js.css等*/
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

    }
    /*这个方法是用来注册拦截器，自定义的拦截器必须在这个方法添加注册才能生效*/

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns("/user/login", "/user/register");
    }
}
