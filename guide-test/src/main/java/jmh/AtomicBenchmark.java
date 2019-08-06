package jmh;

import org.junit.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;


/**
 *
 * http://tutorials.jenkov.com/java-performance/jmh.html
 *
 * example
 * http://hg.openjdk.java.net/code-tools/jmh/file/tip/jmh-samples/src/main/java/org/openjdk/jmh/samples/
 *
 *
 */



/**
 *
 构建此项目
 mvn archetype:generate -DinteractiveMode=false -DarchetypeGroupId=org.openjdk.jmh \
 -DarchetypeArtifactId=jmh-java-benchmark-archetype -DarchetypeVersion=1.4.1 \
 -DgroupId=org.agoncal.sample.jmh -DartifactId=logging -Dversion=1.0
 */


/**
 * 注解的含义分别是，
 *  1. warm up 2次，  一次都是10s种 默认5次。
 *  2. 真正测试 2次。
 *  3. fork 一次（包含一次 warm up和一次 真正测试），默认是5次
 *  4. 全局共享这个实例，而不是每个线程一个实例。
 */
@Warmup(iterations = 2,time =2,batchSize =1000)
@Measurement(iterations = 2,time =3)
@Fork(1)
@State(value = Scope.Benchmark)
public class AtomicBenchmark {

    AtomicLong atomicLong=new AtomicLong(0);
    LongAdder longAdder = new LongAdder();


    /**
     * 含义分别是：
     *      1. 首先通过单元测试，确定逻辑没有问题
     *      2. 这是一个微基准测试
     *      3. 测试的模式是 平均时间
     *      4. 同时以20线程来运行
     *
     */
    @Test
    @Benchmark()
    @BenchmarkMode(value = Mode.AverageTime)
    @Threads(20)
    public void atom20() {
        atomicLong.incrementAndGet();
    }

    @Test
    @Benchmark
    @BenchmarkMode(value = Mode.AverageTime)
    @Threads(2)
    public void atom2() {
        atomicLong.incrementAndGet();
    }


    @Test
    @Benchmark
    @BenchmarkMode(value = Mode.AverageTime)
    @Threads(20)
    public void adder20() {
        longAdder.increment();
    }

    @Test
    @BenchmarkMode(value = Mode.AverageTime)
    @Benchmark
    @Threads(2)
    public void adder2() {
        longAdder.increment();
    }


    /**
     *     运行方式：
     *     1. 直接类名选择上运行,idea 需要 junit的支持，就是在方法上先要test注解
     *     2. 通过runner，的include方法正则表达式匹配，要运行类，方法。
     *
     */

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder()
                .include("add")
                .forks(1).build();

        new Runner(options).run();
    }

}
