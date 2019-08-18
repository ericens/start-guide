import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.slf4j.NDC;
import sl4j.package1.LogExampleDebug;
import sl4j.package2.LogExample;
import sl4j.package2.LogExampleWarn;

/**
 * 使用logback.xml 文件，文件名不变。变了找不到。
 */
@Slf4j
public class Sl4jTest {
    public static void main(String[] args) {


        MDC.put("txid", "txid111");
        log.info("-----------txid11111111--------------");
        LogExample.main(null);
        LogExampleDebug.main(null);
        LogExampleWarn.main(null);

        MDC.put("txid", "txid333");
        log.info("---------txid333----------------");
        LogExample.main(null);
        LogExampleDebug.main(null);
        LogExampleWarn.main(null);



        NDC.push("NDC001");
        log.info("---------NDC001----------------");
        LogExample.main(null);
        LogExampleDebug.main(null);
        LogExampleWarn.main(null);

    }
}
