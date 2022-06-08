package com.shop;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Properties;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//用 @Mapper 了，不然 idea 不提示
//能用，但 idea 提示出错
//单数据源，现在是自动配置
//@MapperScan("com.shop.mapper")
@SpringBootApplication
//配置了 ComponentScan 就不能自动扫描了
public class AuctionSystemApp implements WebMvcConfigurer {

    //不知道 @ModelAttribute 怎么用
//    @ModelAttribute
//    public User getUser(){
//        final User user = new User();
//        user.setUsername("user.setUsername");
//        return user;
//    }

    public static void main(String[] args) {
//        SpringApplication.run(AuctionSystemApp.class, args);
        final SpringApplication springApplication = new SpringApplication(AuctionSystemApp.class);
        springApplication.setBannerMode(Banner.Mode.OFF);
        springApplication.run(args);
    }
//    ====================================
//    验证码

    /**
     * import com.google.code.kaptcha.impl.DefaultKaptcha;
     * import com.google.code.kaptcha.util.Config;
     * <p>
     * import java.util.Properties;
     * <p>
     * import org.springframework.context.annotation.Bean;
     *
     * @return
     */
    @Bean
    public DefaultKaptcha getDefaultKaptcha() {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        // 图片边框
        properties.setProperty("kaptcha.border", "yes");
        // 边框颜色
        properties.setProperty("kaptcha.border.color", "105,179,90");
        // 字体颜色
        properties.setProperty("kaptcha.textproducer.font.color", "red");
        // 图片宽
        properties.setProperty("kaptcha.image.width", "110");
        // 图片高
        properties.setProperty("kaptcha.image.height", "40");
        // 字体大小
        properties.setProperty("kaptcha.textproducer.font.size", "30");
        // session key
        properties.setProperty("kaptcha.session.key", "code");
        // 验证码长度
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        // 字体
        properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);

        return defaultKaptcha;
    }
//    ====================================

    // 底层就是spring mvc的拦截器机制
// D:\\pic 路径映射成网络地址路径
//  D:\\pic  -> http://localhost:8080    /uploads/cc.jpg   /uploads/**
// file: 固定写法  类似于 http: 访问协议
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/toRegister").setViewName("registerUserPage");
        registry.addViewController("/toAuctionPage").setViewName("addAuction2");
        WebMvcConfigurer.super.addViewControllers(registry);
    }

    @Override

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        file:///C:/Users/aaa.html
//        http://localhost:8086/upload/white_under.jpg --> file:///D:/temporary/white_under.jpg
        registry.addResourceHandler("/upload/**").addResourceLocations("file:///D:/temporary/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        WebMvcConfigurer.super.addFormatters(registry);
    }
    //    ====================================
}
