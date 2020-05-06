package zlx.factory.autoConfig;

import org.springframework.boot.autoconfigure.AutoConfigurationImportSelector;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@EnableAutoConfiguration
@Import(AutoConfigurationImportSelector.class)
public class MyConfigration {

    public MyConfigration() {
        System.out.println("TestConfig容器初始化...");
    }


    @Bean(name = "getMyCar")
    public Car getCar() {
        Car c = new Car();
        c.setName("dankun");
        return c;
    }

    static public class Car {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
}
