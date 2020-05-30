package zlx.aop.aspectj;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component("aspectjx")
public class AnnotationAspectJTest {

    @Pointcut("execution(* *.action(*))")
    public void action() {
    }

    @Pointcut("execution(* *.work(*))")
    public void work() {
    }

    @Pointcut("action() || work())")
    public void compositePointcut() {
    }

    //前置通知
    @Before("compositePointcut()")
    public void beforeAdvice() {
        log.info("before advice ........................the work class{} ", this.getClass());

    }

    //后置通知
    @After("compositePointcut()")
    public void doAfter() {
        log.info("after advice ........................the work class{} ", this.getClass());    }
}