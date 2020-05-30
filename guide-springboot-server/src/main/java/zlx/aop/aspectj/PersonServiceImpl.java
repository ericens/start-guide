package zlx.aop.aspectj;


import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;

//编写实现类
@Service("personService")
@Slf4j
public class PersonServiceImpl implements IPersonService {

    @Override
    public String action(String msg) {
        log.info("the action class{} ", this.getClass());
        System.out.println("FooService, method doing.");

//        this.work(msg);
// *** 代码 1 ***， 调用原对象


        ((PersonServiceImpl) AopContext.currentProxy()).work(msg);
// *** 代码 2 ***  调用代理对象
        return "[" + msg + "]";
    }

    @Override
//    final public String work(String msg) {
     public String work(String msg) {
        log.info("the work class{} ", this.getClass());
        System.out.println("work: * " + msg + " *");
        return "* " + msg + " *";
    }
}