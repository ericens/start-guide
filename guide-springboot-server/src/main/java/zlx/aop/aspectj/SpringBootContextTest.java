package zlx.aop.aspectj;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.core.DebuggingClassWriter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.bind.annotation.RestController;

//https://blog.csdn.net/rtuujnncc/article/details/68926465

@Slf4j
@RestController
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass=true, exposeProxy=true)
public class SpringBootContextTest {


    public static void main(String[] args) {


        //--该设置用于输出cglib动态代理产生的类
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "/tmp" );

        // 1、生成$Proxy0的class文件
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        ApplicationContext app = SpringApplication.run(SpringBootContextTest.class, args);

        Object x= app.getBean("aspectjx");
        log.info("x:{}",x.getClass());   // 这个还是会运行完， 然后挂起

        IPersonService personService= (IPersonService)app.getBean("personService");
        personService.action("zlx.hello world.");
        log.info("personService:{}",personService.getClass());

    }

}
