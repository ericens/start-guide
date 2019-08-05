package jmh;

import org.junit.Test;
import org.openjdk.jmh.annotations.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.concurrent.TimeUnit;

/**
 * Created by @author linxin on 14/01/2018.  <br>
 *
 *     https://www.jianshu.com/p/192b782c31bc
 o.a.s.j.ZeroCopyTest.commonFileCopy      thrpt       20  311.986 ± 51.073  ops/s
 o.a.s.j.ZeroCopyTest.transferFromDemo    thrpt      200  479.462 ± 31.598  ops/s
 */


@Fork(1)
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 2)
@Measurement(iterations = 2)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class ZeroCopyTest {

    private static String from="/Users/ericens/tmp/jmh-info.log";
    private static String to="/Users/ericens/tmp/all_0.data2";

    @Benchmark
    @Test
    public void transferFromDemo() throws IOException {
        transferFromDemo(from,to);
    }

    @Benchmark
    @Test
    public void commonFileCopy() throws IOException {
        commonFileCopy(from,to);
    }

    public  void transferFromDemo(String from, String to)
            throws IOException {
        FileChannel fromChannel = new FileInputStream(from).getChannel();
        FileChannel toChannel = new FileOutputStream(to).getChannel();

        long position = 0;
        long count = fromChannel.size();

        toChannel.transferFrom(fromChannel, position, count);

        fromChannel.close();
        toChannel.close();
    }



    public  void commonFileCopy(String from, String to)
            throws IOException {
        FileInputStream fileInputStream = new FileInputStream(from);
        FileOutputStream fileOutputStream = new FileOutputStream(to);

        int position = 0;

        byte[] b=new byte[1024*8];

        while( (position=fileInputStream.read(b))>0){
            fileOutputStream.write(b,0,position);
        }
        fileInputStream.close();
        fileOutputStream.close();
    }

}
