package sl4j.package2;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by ericens on 2017/5/18.
 */
@Slf4j
public class LogExampleWarn {

    public static void main(String[] args) {
        log.debug("this is sl4j debug");
        log.info("this is sl4j info");
        log.warn("this is sl4j warn");
        log.error("this is sl4j error");
    }


}
