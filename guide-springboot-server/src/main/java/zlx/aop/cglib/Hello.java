package zlx.aop.cglib;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Hello {
    public void req(){
        log.info("zlx.hello from target");
    }

    final public void response(){
        log.info("zlx.hello final from target");
    }
}
