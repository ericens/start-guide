package hello.springboot;


import hello.springboot.entity.Request;
import hello.springboot.entity.Resp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by @author linxin on 15/06/2018.  <br>
 *

 finish
 curl -XPOST 'http://localhost:8803/hello/' -H'Content-Type: application/json;charset=utf-8' -d'{"status":1002,"taskid":2345}'

 */
@RestController
@Slf4j
public class HelloControler {

    @PostMapping("/hello")
    public String imeis (@RequestBody Request request) throws Exception {
        try {
            if(request ==null ){
                return Resp.fail(request,"参数不合法param 为空");
            }
            return Resp.success();
        }catch (Exception e){
            log.info("error:",e);
            return Resp.fail(request,"处理失败");
        }
    }













}
