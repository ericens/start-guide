package zlx.hello;

/**
 * Created by @author linxin on 05/09/2017.  <br>
 *
 *


 12034 SayHelloApplication          在运行的jvm进程。
 blade prepare jvm -p 12034         代理改进程
 blade create jvm return --classname zlx.hello.SayHelloApplication --methodname greet --value modifyxxx --process 12034 -d



 */

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cglib.core.DebuggingClassWriter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@RestController
@EnableAutoConfiguration
@ComponentScan({"zlx.aop","zlx.hello"})
@PropertySource("namexx.properties")
@ConfigurationPropertiesScan
//@EnableApolloConfig
public class SayHelloApplication {

    private static Logger log = LoggerFactory.getLogger(SayHelloApplication.class);


    public static void main(String[] args) {

        System.setProperty("log4j2.debug","true");


        System.setProperty("log4j.configurationFile", "log4j2.xml");


        //--该设置用于输出cglib动态代理产生的类
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "/tmp");

        // 1、生成$Proxy0的class文件
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");



        SpringApplication.run(SayHelloApplication.class, args);
    }

    /**
     * curl  'http://localhost:8803/greeting/?name=2342'
     * @param name
     * @return
     */
    @RequestMapping(value = "/greeting")
    public String greet(String name) {
        log.info("Access /greeting");

        List<String> greetings = Arrays.asList("Hi there", "Greetings", "Salutations");
        Random rand = new Random();

        int randomNum = rand.nextInt(greetings.size());
        return greetings.get(randomNum)+" :  "+name+"\n";
    }

    @RequestMapping(value = "/")
    public String home() {
        log.info("Access /");
        return "Hi!"+"\n";
    }


}
