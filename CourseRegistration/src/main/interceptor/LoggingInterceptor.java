package com.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class LoggingInterceptor implements HandlerInterceptor {

    
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String uri = request.getRequestURI();
        HttpSession session = request.getSession(false);

        
        if (uri.contains("/login")) {
            System.out.println("[LOGIN ATTEMPT] URL: " + uri);
        }

        
        if (uri.contains("/register")) {
            String email = (session != null) ? (String) session.getAttribute("email") : "Unknown";
            System.out.println("[COURSE REGISTRATION] User: " + email + " -> " + uri);
        }

        return true; 
    }

    
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) throws Exception {
        
    }
}