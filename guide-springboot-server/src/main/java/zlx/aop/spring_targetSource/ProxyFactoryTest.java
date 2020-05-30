package zlx.aop.spring_targetSource;

import org.springframework.aop.framework.ProxyFactory;

public class ProxyFactoryTest {

    public static void main(String[] args) {
        // 1.创建代理工厂
        ProxyFactory factory = new ProxyFactory();
        // 2.设置目标对象
        factory.setTarget(new ChromeBrowser());
        // 3.设置代理实现接口
        factory.setInterfaces(new Class[]{Browser.class});
        // 4.添加前置增强
//        factory.addAdvice(new BrowserBeforeAdvice());
        factory.addAdvice(new BrowserAdvice());
        // 6.获取代理对象
        Browser browser = (Browser) factory.getProxy();

        browser.visitInternet();
    }
}
