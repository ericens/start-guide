import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by ericens on 2017/5/18.
 */
@Slf4j
public class LogExample {

    public static void main(String[] args) {
        log.debug("this is sl4j debug");
        log.info("this is sl4j info");
        log.warn("this is sl4j warn");
        log.error("this is sl4j error");
    }


}
