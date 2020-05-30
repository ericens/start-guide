import zlx.hello.springboot.entity.Resp;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 */
@Slf4j
public class RequestTest {
    int count=0;


    @Test
    public void sucessTest() {
         String s= Resp.success();
        log.info("response:{},runcount:{}",s,count++);



    }

}
