package log4j2;

import log4j2.package1.LogExample;
import log4j2.package2.LogExampleDebug;
import log4j2.package2.LogExampleWarn;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.ThreadContext;

import java.util.UUID;

/**
 *
 *   这个类不能使用 log注解，因为使用了之后，已经先初始化了 logFactory.
 *   ,再指定指定 systemProperties就无效了。。  这不能在启动参数里面加载的。这时候已经启动好了。
 */

public class Log4jTest {
    public static void main(String[] args) {
        System.setProperty("log4j.configurationFile", "/Users/ericens/git/my/start-guide/guide-log-log4j/src/main/resources/log4j2-debug.xml");

        System.out.println("this is --------------------");
        ThreadContext.put("txid","0001");
        ThreadContext.push(UUID.randomUUID().toString().substring(24));
        LogExample.main(null);
        LogExampleDebug.main(null);
        LogExampleWarn.main(null);
        ThreadContext.pop();

        System.out.println("this is --------------------");

        ThreadContext.push(UUID.randomUUID().toString().substring(24));
        LogExample.main(null);
        LogExampleDebug.main(null);
        LogExampleWarn.main(null);

        System.out.println("this is --------------------");
        LogExample.main(null);
        LogExampleDebug.main(null);
        LogExampleWarn.main(null);

    }
}
