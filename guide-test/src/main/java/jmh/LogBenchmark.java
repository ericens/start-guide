
package jmh;

import org.junit.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Warmup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@Fork(1)
@Warmup(iterations =2,time = 3,batchSize =1000)
@Measurement(iterations =2,time = 3)
public class LogBenchmark {

    private static final Logger logger = LoggerFactory.getLogger(LogBenchmark.class);


    @Test
    @Benchmark
    public void testVariableArguments() {

        String x = "", y = "", z = "";

        for (int i = 0; i < 100; i++) {
            x += i; y += i; z += i;

            logger.debug("Variable arguments {} {} {}", x, y, z);
        }
    }

    @Test
    @Benchmark
    public void testIfDebugEnabled() {

        String x = "", y = "", z = "";

        for (int i = 0; i < 100; i++) {
            x += i; y += i; z += i;

            if (logger.isDebugEnabled())
                logger.debug("If debug enabled {} {} {}", x, y, z);
        }
    }

    @Test
    @Benchmark
    public void testConcatenatingStrings() {

        String x = "", y = "", z = "";

        for (int i = 0; i< 100; i++) {
            x += i; y += i; z += i;

            logger.debug("Concatenating strings " + x + y + z);
        }
    }
}
