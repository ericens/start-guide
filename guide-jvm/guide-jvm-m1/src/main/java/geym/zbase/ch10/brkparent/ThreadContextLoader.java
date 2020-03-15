package geym.zbase.ch10.brkparent;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.sql.Driver;
import java.util.ServiceLoader;

public class ThreadContextLoader {

    static {
        loadInitialDrivers();//执行该方法
        System.out.println("JDBC DriverManager initialized");
    }


    public void test() throws ClassNotFoundException {
        Class.forName(String.valueOf(Driver.class));
    }

    //loadInitialDrivers方法
    private static void loadInitialDrivers() {
        AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
                //加载外部的Driver的实现类
                ServiceLoader<Driver> loadedDrivers = ServiceLoader.load(Driver.class);
                //省略不必要的代码
                return null;
            }
        });
    }

}
