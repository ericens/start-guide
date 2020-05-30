package proxyM.my;

import proxyM.PlanGSdg;
import sun.misc.ProxyGenerator;
import java.io.FileOutputStream;
import java.io.IOException;

public class ProxyUtils {

    public static void main(String[] args) {
        // 保存JDK动态代理生成的代理类，类名保存为 UserServiceProxy
//        ProxyUtils.generateClassFile(RealSubject.class, "UserServiceProxy");

        ProxyUtils.generateClassFile(PlanGSdg.class, "UserServiceProxy");
        // 可以生产一个代理对象。但是没有特别的方法。PlanGSdg的方法不在里面

    }
    /**
     * 将根据类信息动态生成的二进制字节码保存到硬盘中，默认的是clazz目录下
     * params: clazz 需要生成动态代理类的类
     * proxyName: 为动态生成的代理类的名称
     */
    public static void generateClassFile(Class clazz, String proxyName) {
        // 根据类信息和提供的代理类名称，生成字节码
        byte[] classFile = ProxyGenerator.generateProxyClass(proxyName, clazz.getInterfaces());
        String paths = clazz.getResource(".").getPath();
        save(paths,classFile,proxyName);

    }

    public static void save(String paths,byte[] classFile,String proxyName){

        System.out.println(paths);
        FileOutputStream out = null;
        try {
            //保留到硬盘中
            out = new FileOutputStream(paths + proxyName + ".class");
            out.write(classFile);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
