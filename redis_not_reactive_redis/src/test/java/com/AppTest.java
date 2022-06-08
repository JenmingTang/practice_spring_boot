package com;

import com.pojo.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SpringBootTest(classes = {App.class})
public class AppTest {
//    爆红但是存在 Bean
    @Autowired
    private RedisTemplate<Object,Object> redisTemplateOO;
    @Autowired
//    .yml 啥没写，但 redis 为 spring-boot-starter ，有自动配置的默认值
//    爆红但是存在 Bean
    /**
     * StringRedisTemplate 比 RedisTemplate<String,String> 多了一个 preProcessConnection 方法
     */
    private StringRedisTemplate stringRedisTemplate;

//==================================================================================================
    /*
        ReactiveRedisTemplate reactiveRedisTemplate;
        final Mono mono = reactiveRedisTemplate.opsForValue().set();
        mono.and(new Mono<Object>() {
            @Override
            public void subscribe(CoreSubscriber<? super Object> coreSubscriber) {

            }
        });
     */
    /*
    aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
reactiveRedisTemplate = org.springframework.data.redis.core.ReactiveRedisTemplate@549d14fe
reactiveStringRedisTemplate = org.springframework.data.redis.core.ReactiveStringRedisTemplate@77d54a41
aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
导入 spring-boot-starter-data-redis 依赖就有了，不用导 spring-boot-starter-data-redis-reactive 了吗
导入 spring-boot-starter-data-redis 依赖就有了，不用导 spring-boot-starter-data-redis-reactive 了吗
导入 spring-boot-starter-data-redis 依赖就有了，不用导 spring-boot-starter-data-redis-reactive 了吗
使其变为可观察对象，类似 Android 的 RxJava 框架，故可响应式
使其变为可观察对象，类似 Android 的 RxJava 框架，故可响应式
使其变为可观察对象，类似 Android 的 RxJava 框架，故可响应式
     */
    @Autowired
    private ReactiveRedisTemplate<Object, Object> reactiveRedisTemplate;
    @Autowired
    private ReactiveStringRedisTemplate reactiveStringRedisTemplate;

//==================================================================================================
    @Test
    void test0() {
//        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
//        System.out.println("reactiveRedisTemplate = " + reactiveRedisTemplate);
//        System.out.println("reactiveStringRedisTemplate = " + reactiveStringRedisTemplate);
//        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        System.out.println("=====================String");
        stringRedisTemplate.delete("key");
        final ValueOperations<String, String> forValue = stringRedisTemplate.opsForValue();
        forValue.set("key", "value");
        final String key1 = forValue.get("key");
        System.out.println("key1 = " + key1);


        System.out.println("=====================list 有序");
        stringRedisTemplate.delete("key");
//        rightPushAll 右边插入
        //        插入数 add_count
        final Long add_count0 = stringRedisTemplate.opsForList().rightPushAll("key", "value0", "value1", "value2");
//        end 铁定下标越界，在 cmd client 没报错，这里也一样
        final List<String> strings = stringRedisTemplate.opsForList().range("key", 0, 1000);
        assert strings != null;
        strings.forEach(System.out::println);


        System.out.println("=====================set 无序、不重复");
        stringRedisTemplate.delete("key");
//        插入数 add_count
        final Long add_count1 = stringRedisTemplate.opsForSet().add("key", "value0", "value1", "value2", "value1");
        final Set<String> members = stringRedisTemplate.opsForSet().members("key");
        assert members != null;
        members.forEach(System.out::println);


        System.out.println("=====================set 有序集、指定 double 排序、不重复");
        stringRedisTemplate.delete("key");
        final ZSetOperations<String, String> forZSet = stringRedisTemplate.opsForZSet();
//        boolean 是否添加成功
        final Boolean add = forZSet.add("key", "value2", 0.01);
        forZSet.add("key", "value1", 0.02);
        forZSet.add("key", "value0", 0.03);
        forZSet.add("key", "value0", 0.03);
        forZSet.add("key", "value0", 0.04);
//        final Set<String> rangeByScore = forZSet.rangeByScore();
        final Set<String> range = forZSet.range("key", 0, 1000);
        assert range != null;

        /*
         * =====================set 有序集、指定 double 排序、不重复
         * value2
         * value1
         * value0
         */
        range.forEach(System.out::println);


        System.out.println("=====================hash 类似对象");
        stringRedisTemplate.delete("key");
//        参数 1 实体对象名，2 字段名，3 字段值
        final HashOperations<String, Object, Object> forHash = stringRedisTemplate.opsForHash();
        //字段名 都能以 String 放下去，字段值不止 String 一种类型，报错了 ClassCastException: java.lang.Integer cannot be cast to java.lang.String
        //改为全部用 String
        final HashMap<String, String> map = new HashMap<>();
        map.put("id", "0");
        map.put("name", "tang00");
//        key 应该为 person
        forHash.putAll("key", map);
        final Map<Object, Object> entries = forHash.entries("key");
        entries.forEach((o, o2) -> System.out.println("o = " + o2));
    }
    @Test
    void test1(){
        redisTemplateOO.setKeySerializer(new StringRedisSerializer(StandardCharsets.UTF_8));
        redisTemplateOO.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//        redisTemplateOO.setValueSerializer(new StringRedisSerializer(StandardCharsets.UTF_8));
        final ValueOperations<Object, Object> forValue = redisTemplateOO.opsForValue();
//        class Person implements Serializable，不然报不能使用 JDK 序列化器错
//        key string value jackson
//        forValue.set("key",new Person(0,"tang0"));
//        key string value string
//        java.lang.ClassCastException: com.pojo.Person cannot be cast to java.lang.String
        forValue.set("key1",new Person(1,"tang1"));
        final Person person = (Person) forValue.get("key");
        System.out.println("================");
        System.out.println("person = " + person);
        System.out.println("================");
    }
}
