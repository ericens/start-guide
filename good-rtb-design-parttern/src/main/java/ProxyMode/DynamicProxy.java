package ProxyMode;


import java.lang.reflect.Proxy;

public class DynamicProxy  {

    public static void main( String args[] )
    {
        RealSubject real = new RealSubject();
        Subject proxySubject = (Subject)Proxy.newProxyInstance(Subject.class.getClassLoader(),
                new Class[]{Subject.class},
                new ProxyHandler(real));

        proxySubject.SujectShow();;

    }
}