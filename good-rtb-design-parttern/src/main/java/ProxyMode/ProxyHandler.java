package ProxyMode;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyHandler implements InvocationHandler {

    private Object proxied;

    public ProxyHandler( Object proxied )
    {
        this.proxied = proxied;
    }

    /**
     * 进来之后，就是 反射可以搞定   的事情了
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {

        System.out.println("准备工作之前：");

        //转调具体目标对象的方法
        Object object=   method.invoke( proxied, args);

        System.out.println("工作已经做完了！");
        return object;
    }

}