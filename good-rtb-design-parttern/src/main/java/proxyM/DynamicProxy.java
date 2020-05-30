package proxyM;



import java.lang.reflect.Proxy;

public class DynamicProxy  {
    /**
     *  根据动态代理的原理，生产自己码的原理。我是不是可以生产一个 基于类的代理，而不是基于接口的代理。
     * @param args
     */
    public static void proxyClass(){
        System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
        RealSubject real = new RealSubject();
        RealSubject proxySubject = (RealSubject) Proxy.newProxyInstance(RealSubject.class.getClassLoader(),
                new Class[]{RealSubject.class},
                new ProxyHandler(real));

        proxySubject.SujectShow();
    }
    public static void MyproxyInterface(){
        System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
        PlanGSdg real = new PlanGSdg();
        IPlanGSdg proxySubject = (IPlanGSdg)Proxy.newProxyInstance(PlanGSdg.class.getClassLoader(),
                new Class[]{IPlanGSdg.class},
                new ProxyHandler(real));

        proxySubject.testss();
    }

//    public static void MyproxyInterfacexx(){
//        PlanGSdg real = new PlanGSdg();
//        PlanGSdg s=new Proxy0(new ProxyHandler(real));
//        s.testss();
//    }

    public static void main( String args[] )
    {
        MyproxyInterface();  //生产输出
//        MyproxyInterfacexx();  //调用
//        PlanGSdg0 s;

        PlanGSdg0 s;
        System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
        RealSubject real = new RealSubject();
        Subject proxySubject = (Subject)Proxy.newProxyInstance(Subject.class.getClassLoader(),
                new Class[]{Subject.class},
                new ProxyHandler(real));

        proxySubject.SujectShow();;

    }
}