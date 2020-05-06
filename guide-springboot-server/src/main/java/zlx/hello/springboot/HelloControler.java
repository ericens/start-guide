package zlx.hello.springboot;


import com.ctrip.framework.apollo.spring.property.SpringValue;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import zlx.aop.aspectj.IPersonService;
import zlx.hello.springboot.entity.Request;
import zlx.hello.springboot.entity.Resp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

/**
 * Created by @author linxin on 15/06/2018.  <br>
 *

 finish
 curl -XPOST 'http://localhost:8803/hello/' -H'Content-Type: application/json;charset=utf-8' -d'{"status":1002,"taskid":2345}'

 */
@RestController
@Slf4j
public class HelloControler {

    @Autowired
    IPersonService  personService;

    @Autowired
    SpringValueProcessor springValueProcessor;

    @Autowired
    ConfigurableBeanFactory beanFactory;

    @Value("{name}")
    private String name;

    @GetMapping("/getValue")
    public String getValue(){
        return name;
    }

    @GetMapping("/updateValue")
    public String updateValue(String oo){
        Collection<SpringValue> values= springValueProcessor.springValueRegistry.get(beanFactory,"name");
        values.forEach(
                v-> {
                    try {
                        v.update(oo);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
        );
        return name;
    }





    @GetMapping("/zlx/hello")
    public String imeis (@RequestBody Request request) throws Exception {
        try {
            personService.action("zlx.hello world.");

            if(request ==null ){
                return Resp.fail(request,"参数不合法param 为空");
            }
            return Resp.success();
        }catch (Exception e){
            log.info("error:",e);
            return Resp.fail(request,"处理失败");
        }
    }


    @GetMapping("/hi")
    public String hi () throws Exception {
        personService.action("zlx.hello world.");
        return Resp.success();
    }














}
