
package jmh;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class Lamda {


    public static void main(String[] args) {

        for (int i = 1; i < 10; i++) {
            getAgesWithLambda(getList(i));
            getAges(getList(i));
        }

    }
    public static List getList(int x){
        int count = 10000*(10<<x);
        log.info("the count {} ",count);
        List<Person> personList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            personList.add(new Person("fsx", 18 + i));
        }
        return personList;
    }

    // 使用lambda表达式方式~
    private static List<Integer> getAgesWithLambda(List<Person> personList) {
        Instant start = Instant.now();
        List x = personList.stream().map(Person::getAge).collect(Collectors.toList());
        System.out.println("getAgesWithLambda 耗时：" + Duration.between(start, Instant.now()).toMillis() + "ms");
        return x;
    }

    // 使用普通方式~
    private static List<Integer> getAges(List<Person> personList) {
        Instant start = Instant.now();
        List<Integer> ages = new ArrayList<>();
        for (Person person : personList) {
            ages.add(person.getAge());
        }
        System.out.println("getAges耗时：" + Duration.between(start, Instant.now()).toMillis() + "ms");
        return ages;
    }

    @Data
    @AllArgsConstructor
    public static class Person {
        String name;
        Integer age;
    }
}
