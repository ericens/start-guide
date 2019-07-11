package hello;

/**
 * Created by @author linxin on 05/09/2017.  <br>
 *
 *


 12034 SayHelloApplication          在运行的jvm进程。
 blade prepare jvm -p 12034         代理改进程
 blade create jvm return --classname hello.SayHelloApplication --methodname greet --value modifyxxx --process 12034 -d



 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@RestController
@SpringBootApplication
public class SayHelloApplication {

    private static Logger log = LoggerFactory.getLogger(SayHelloApplication.class);


    public static void main(String[] args) {
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
