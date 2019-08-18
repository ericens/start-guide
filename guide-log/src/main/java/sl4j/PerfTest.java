package sl4j;

import org.junit.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 空跑：    PerfTest.logTest  thrpt    2  4 8245 0201.573          ops/s
 * 写文件    PerfTest.logTest  thrpt    2      14 5690.580          ops/s
 */
@Warmup(iterations = 1,time =1)
@Measurement(iterations = 2,time =10)
@State(value = Scope.Benchmark)
//@Slf4j
public class PerfTest {
    Logger log= LoggerFactory.getLogger(this.getClass());

    @Threads(20)
    @Benchmark
    @Fork(1)
    @Test
    public void logTest(){
        log.debug("this is the test ");
    }

    @Threads(2)
    @Benchmark
    @Fork(1)
    public void log2Test(){
        log.debug("this is the test ");
    }

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder()
                .include("PerfTest")
                .forks(1).build();

        new Runner(options).run();
    }

}
