package junit.thread;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class PoolExceptionTest {

    ExecutorService executorService= Executors.newFixedThreadPool(2);

    @Test
    public void execute() {
        for(int i=0;i<10;i++){
            try{
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        log.info("this to get thread name");
                        int x=100/0;

                    }
                });

            }catch (Throwable t) {
                log.error("xx",t.getMessage());
            }
        }

        try {
            Thread.sleep(1000*10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
