package com;

import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        //IllegalArgumentException: Sources must not be empty 解决：加 App.class
        final SpringApplication springApplication = new SpringApplication(App.class);
        springApplication.setBannerMode(Banner.Mode.OFF);
        springApplication.run(args);
    }
//    是个 Bean 都会走
    //这能拿到 命令行启动 App 的 参数
    //以后所有 Bean 都这样写，反正容器不需要
//    @Bean
//    CommandLineRunner commandLineRunner(RedisTemplate<Object,Object> stringRedisTemplate){
////        StringRedisTemplate 和 RedisTemplate 完全不同，具体看 Redis 自动配置类源码
//        stringRedisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
//        stringRedisTemplate.setKeySerializer();
//        stringRedisTemplate.setValueSerializer();
//        stringRedisTemplate.setHashKeySerializer();
//        stringRedisTemplate.setHashValueSerializer();
//        return args -> System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
//    }
}
