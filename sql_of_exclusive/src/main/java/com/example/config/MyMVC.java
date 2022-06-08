package com.example.config;

import com.example.interceptor.MyInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyMVC implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/personShow").setViewName("personShow");
//        registry.addViewController("/personshow").setViewName("personShow");
        registry.addViewController("/goAddPersonPage").setViewName("addPersonPage");
        registry.addViewController("/templatename").setViewName("templatename");
        WebMvcConfigurer.super.addViewControllers(registry);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//.addPathPatterns("/**") //拦截所有请求，包括静态资源文件
//.excludePathPatterns("/", "/login", "/index.html", "/user/login", "/css/**", "/images/**", "/js/**", "/fonts/**"); //放行登录页，登陆操作，静态资源
//       报错 默认会去请求 默认异常处理，所以会产生一个请求，这里就多走一次
        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
