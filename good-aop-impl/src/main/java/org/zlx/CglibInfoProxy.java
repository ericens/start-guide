package org.zlx;

import org.easymock.cglib.core.DebuggingClassWriter;
import org.easymock.cglib.proxy.Enhancer;
import org.easymock.cglib.proxy.MethodInterceptor;
import org.easymock.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibInfoProxy implements MethodInterceptor {
    private Object target;
    public Object newInstance(Object source){
        target = source;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.target.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    /**
     和我们的jdk动态代理看起来十分相似，只是两个类实现的接口不同，并且生成对象的方法也不同。
     这里非常坑的是invoke方法和invokeSuper的区别，
        如果是用invoke方法一定要使用被代理的对象也就是上文中的target，
        而如果调用invokeSuper方法，则一定要使用被代理后的o对象。

     * @param o
     * @param method
     * @param objects
     * @param methodProxy
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("before method!!!");
//        Object value = methodProxy.invoke(o, objects);
//        Object value = methodProxy.invoke(this.target, objects);
        Object value = methodProxy.invokeSuper(o, objects);
        return value;
    }
    public static void main(String[] args) {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "/Users/ericens/tmp");


        InfoDemo instance = (InfoDemo) new CglibInfoProxy().newInstance(new InfoDemo());
        instance.welcome("zhangsan");

    }
}
