package com;

import com.mapper.PersonMapper;
import com.pojo.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
//这里可能会报错，解决：指明启动类类对象
@SpringBootTest(classes = {App.class})
public class AppTest {
    @Autowired
    private PersonMapper personMapper;
//    这里可能报错，解决：必须 public

    /**
     * 第二个错 说啥插件问题，解决：pom.xml 加该插件的 <skipTests>true</skipTests>
     * <build>
     *         <plugins>
     *             <plugin>
     *                 <groupId>org.apache.maven.plugins</groupId>
     *                 <artifactId>maven-surefire-plugin</artifactId>
     *                 <version>2.22.2</version>
     *                 <configuration>
     *                     <skipTests>true</skipTests>
     *                 </configuration>
     *             </plugin>
     *         </plugins>
     *     </build>
     */
    @Test
    public void test0(){
        final List<Person> people = personMapper.selectAllPerson();
        /**
         * ==========================
         * Person(id=1, name=person0, cats=[Cat(id=3, person_id=1, name=cat2, person=Person(id=1, name=person0, cats=null)), Cat(id=2, person_id=1, name=cat1, person=Person(id=1, name=person0, cats=null)), Cat(id=1, person_id=1, name=cat0, person=Person(id=1, name=person0, cats=null))])
         * Person(id=2, name=person1, cats=[Cat(id=6, person_id=2, name=cat5, person=Person(id=2, name=person1, cats=null))])
         * Person(id=3, name=person2, cats=[Cat(id=5, person_id=3, name=cat4, person=Person(id=3, name=person2, cats=null)), Cat(id=4, person_id=3, name=cat3, person=Person(id=3, name=person2, cats=null))])
         * Person(id=4, name=person3, cats=[Cat(id=null, person_id=null, name=null, person=Person(id=4, name=person3, cats=null))])
         * ==========================
         * 用有 N+1 查询问题的方式，就不会出现 Person4 的问题了，person4 没有猫
         */
        System.out.println("==========================");
        people.forEach(System.out::println);
        System.out.println("==========================");
    }
}
