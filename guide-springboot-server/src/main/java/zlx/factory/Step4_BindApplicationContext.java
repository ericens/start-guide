package zlx.factory;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.stream.Stream;
@Slf4j
public class Step4_BindApplicationContext {


    @Test
    public void bindViaCodeXmlTest(){
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        DowJonesNewsListener djNewsListener = (DowJonesNewsListener) context.getBean("djNewsListener");
        djNewsListener.process1();
        ((ClassPathXmlApplicationContext) context).registerShutdownHook();

        Stream.of(context.getBeanDefinitionNames()).forEach(
                beanName->{
                    log.info("beanName..... {},{}",beanName,context.getBean(beanName).getClass());
                }
        );

    }



    @Test
    public void registerShutdownHookTest(){
        ApplicationContext context = new AnnotationConfigApplicationContext("zlx.factory");
        DowJonesNewsListener djNewsListener = (DowJonesNewsListener) context.getBean("djNewsListener");
        djNewsListener.process1();
        ((ClassPathXmlApplicationContext) context).registerShutdownHook();
    }
}
