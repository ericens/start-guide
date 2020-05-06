package zlx.factory.autoConfig;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.stream.Stream;

@Slf4j
public class TestScan {

    @Test
    public void MapperAutoConfigTest(){
        AnnotationConfigApplicationContext applicationContext=new AnnotationConfigApplicationContext();
        applicationContext.register(MyConfigration.class);
        applicationContext.refresh();

        Stream.of(applicationContext.getBeanDefinitionNames()).forEach(
                beanName->
                        log.info("beanName.... {},{}",beanName,applicationContext.getBean(beanName).getClass())
        );


    }

}
