package zlx.aop.cglib;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.Enhancer;
@Slf4j
public class HelloClient {
    public static void main(String[] args) {
        Enhancer en=new Enhancer();
        en.setSuperclass(Hello.class);
        en.setCallback(new HelloCallBack());
        Hello x=(Hello)en.create();

        x.req();

        x.response();

    }

}
