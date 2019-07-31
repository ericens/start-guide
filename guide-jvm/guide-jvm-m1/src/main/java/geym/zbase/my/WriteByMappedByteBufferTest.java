package geym.zbase.my;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.concurrent.CountDownLatch;

/**
 * https://www.cnkirito.moe/nio-buffer-recycle/
 */

public class WriteByMappedByteBufferTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        File data = new File("/tmp/data.txt");
        data.createNewFile();
        FileChannel fileChannel = new RandomAccessFile(data, "rw").getChannel();
        for (int i = 0; i < 1000; i++) {
            Thread.sleep(1000);
            fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 1024L * 1024 * 1024);
        }
        System.out.println("map finish");
        new CountDownLatch(1).await();
    }
}
