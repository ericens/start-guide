package log4j2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 *
 * PerfTest.logTest  thrpt    2  139432.523          ops/s
 *
 *
 *  
 */

@Warmup(iterations = 1,time =1)
@Measurement(iterations = 2,time =10)
@State(value = Scope.Benchmark)
public class SyncPerfTest {
    Logger  log=null;



    @Setup(Level.Iteration)
    public void setup() {
        System.out.println("this is the sync..");
        log= LogManager.getLogger(SyncPerfTest.class);
    }

    @Threads(20)
    @Benchmark
    @Fork(1)
    public void logTest(){
        log.info("this is the test ");
    }
    
    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder()
                .include("SyncPerfTest")
//                .include("AsyncPerfTest")
                .forks(1).build();

        new Runner(options).run();
    }

}
