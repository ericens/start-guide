package log4j2.package1;

import lombok.extern.log4j.Log4j2;

/**
 * Created by ericens on 2017/5/18.
 */
@Log4j2
public class LogExample {

    public static void main(String[] args) {
        log.debug("this is log4j debug");
        log.info("this is log4j info");
        log.warn("this is log4j warn");
        log.error("this is log4j error");
    }


}
