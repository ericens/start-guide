package zlx.aop.dynamic;

import zlx.aop.aspectj.IPersonService;
import zlx.aop.aspectj.PersonServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.DebuggingClassWriter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
@Slf4j
public class DynamicFooServiceTest {


    public static void main(String[] args) throws Exception {
        log.info("方法1-----------");
        //--该设置用于输出cglib动态代理产生的类
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "/tmp" +
                "");
        // 1、生成$Proxy0的class文件
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        // 2、获取动态代理类
        Class proxyClazz = Proxy.getProxyClass(IPersonService.class.getClassLoader(),IPersonService.class);
        // 3、获得代理类的构造函数，并传入参数类型InvocationHandler.class
        Constructor constructor = proxyClazz.getConstructor(InvocationHandler.class);
        // 4、通过构造函数来创建动态代理对象，将自定义的InvocationHandler实例传入
        IPersonService iHello1 = (IPersonService) constructor.newInstance(new MyInvocationHandler(new PersonServiceImpl()));

        iHello1.action("sss1");





        /**
         * Proxy类中还有个将2~4步骤封装好的简便方法来创建动态代理对象，
         *其方法签名为：newProxyInstance(ClassLoader loader,Class<?>[] instance, InvocationHandler h)
         */
        log.info("方法2-----------");
        IPersonService  iHello2 = (IPersonService) Proxy.newProxyInstance(IPersonService.class.getClassLoader(), // 加载接口的类加载器
                new Class[]{IPersonService.class}, // 一组接口
                new MyInvocationHandler(new PersonServiceImpl())); // 自定义的InvocationHandler
        iHello2.action("sss2");


        log.info("方法3-----------");
        Class proxyClass=Class.forName("com.sun.proxy.$Proxy0");
        // 3、获得代理类的构造函数，并传入参数类型InvocationHandler.class
        Constructor proxyClassConstructor = proxyClass.getConstructor(InvocationHandler.class);
        IPersonService proxyInstance = (IPersonService) proxyClassConstructor.newInstance(new MyInvocationHandler(new PersonServiceImpl()));
        proxyInstance.action("sss3");


        proxyInstance.work("ss");

    }
}