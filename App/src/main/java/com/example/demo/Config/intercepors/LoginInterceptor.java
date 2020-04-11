package com.example.demo.Config.intercepors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("进入拦截器");
        Cookie[] cookies = request.getCookies();
        System.out.println(cookies);
        String token = null;
        for (Cookie cookie : cookies) {
            switch(cookie.getName()){
                case "children_user":
                    token = cookie.getValue();
                    break;
                default:
                    break;
            }
        }
        if(token!=null){
            return true;
        }else
            return false;
    }
}
