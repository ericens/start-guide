package zlx.factory.importBeanDefinitionRegistrarTest;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.stream.Stream;
@Slf4j
public class TestScan {

    @Test
    public void test(){
        ApplicationContext applicationContext=new AnnotationConfigApplicationContext("zlx.factory.importBeanDefinitionRegistrarTest");
        Stream.of(applicationContext.getBeanDefinitionNames()).forEach(
                beanName->{
                    log.info("beanName..... {},{}",beanName,applicationContext.getBean(beanName).getClass());
                }
        );
    }



    @Test
    public void xmlScan(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans-scan.xml");
        Stream.of(applicationContext.getBeanDefinitionNames()).forEach(
                beanName->{
                    log.info("beanName..... {},{}",beanName,applicationContext.getBean(beanName).getClass());
                }
        );
    }



    /**
     * @Configuration 注释的类 类似于于一个 xml 配置文件的存在(针对于 ClassXmlPathApplicationContext 来说)
     */
    @Test
    public void MapperAutoConfigTest(){
        AnnotationConfigApplicationContext applicationContext=new AnnotationConfigApplicationContext();
        applicationContext.register(MapperAutoConfig.class);
        applicationContext.refresh();

        Stream.of(applicationContext.getBeanDefinitionNames()).forEach(
                beanName->{
                    log.info("beanName..... {},{}",beanName,applicationContext.getBean(beanName).getClass());
                }
        );


    }

}
