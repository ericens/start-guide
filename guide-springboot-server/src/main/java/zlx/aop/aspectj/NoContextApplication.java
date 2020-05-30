package zlx.aop.aspectj;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

@Slf4j
public class NoContextApplication {


    public static void main(String[] args) {
        AspectJProxyFactory factory=new AspectJProxyFactory();
        factory.setProxyTargetClass(true);
        factory.setTarget(new PersonServiceImpl());
        factory.setExposeProxy(true);
        factory.addAspect(AnnotationAspectJTest.class);
        PersonServiceImpl x=(PersonServiceImpl)  factory.getProxy();
        x.action("sss");
        log.info("spring2 personService:{}",x.getClass());
    }


}
