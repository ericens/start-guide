package zlx.aop.spring_targetSource;

import org.aopalliance.aop.Advice;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class BrowserAdvice implements Advice {

    public void before(Method method, Object[] args, Object target) throws Throwable {
        encrypt();
    }

    //加密
    private void encrypt(){
        System.out.println("encrypt ...");
    }

}