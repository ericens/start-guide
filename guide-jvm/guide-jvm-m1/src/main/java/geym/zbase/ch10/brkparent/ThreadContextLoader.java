package geym.zbase.ch10.brkparent;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ServiceLoader;
@Slf4j
public class ThreadContextLoader {
    String url = "jdbc:mysql://localhost:3306/";


    static {
        loadInitialDrivers();//zlx执行该方法
        System.out.println("JDBC DriverManager initialized");
    }

    @Test
    public void test() throws ClassNotFoundException, SQLException {
        try {
//            log.info("xxx, {}",String.valueOf(MySQLDriver.class));
//            Class.forName(String.valueOf(MySQLDriver.class));

            Class.forName( MySQLDriver.class.getName() );


            Class.forName("geym.zbase.ch10.brkparent.MySQLDriver");
        } catch (Exception e) {
            log.info("直接加载不了 ");
            e.printStackTrace();
        }

        Connection conn = null;
        // STEP 2: 注册mysql的驱动
        Class x=Class.forName("com.mysql.cj.jdbc.Driver");  // zlx 已经触发了加载，和实例化 加载Class到AppClassLoader（系统类加载器），然后注册驱动
        log.info("x{},{}",x,x.getClassLoader());
        log.info("this {},{}",this,this.getClass().getClassLoader());
        Driver d;


        // STEP 3: 获得一个连接
        System.out.println("Connecting to database...");
        conn = DriverManager.getConnection(url, "root", "");

        ServiceLoader<MySQLDriver> loadedDrivers = ServiceLoader.load(MySQLDriver.class);
        loadedDrivers.forEach(
                i -> log.info("xx", i, i.getClass().getClassLoader())
        );


        System.out.println("ThreadContextLoader  "+ ThreadContextLoader.class.getClassLoader());
        System.out.println("Driver "+com.mysql.jdbc.Driver.class.getClassLoader());
        System.out.println("DriverManager   "+DriverManager.class.getClassLoader());

    }
    //loadInitialDrivers方法
    private static void loadInitialDrivers() {
        AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
                //加载外部的Driver的实现类
                ServiceLoader<MySQLDriver> loadedDrivers = ServiceLoader.load(MySQLDriver.class);
                //省略不必要的代码
                return null;
            }
        });
    }

}
