package geym.zbase.my;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.LockSupport;


@Slf4j
public class ReadByHeapByteBufferTest {
    List<Thread> list=new ArrayList();
    CountDownLatch countDownLatch=new CountDownLatch(1);

    public void contoll() throws IOException, InterruptedException {
        log.info("����۲� �ڶ����ڴ���䣬ʹ��cache��");
        readStart("start");
        list.forEach(
                t->{
                    LockSupport.unpark(t);
                }
        );

        countDownLatch.await();
    }

    public void readStart(String expect) throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        String str = null;
        while (true) {
            str = reader.readLine();
            if(str!=null && str.equals(expect)){
                break;
            }
        }

    }
    public   List<Thread> oneCall() throws InterruptedException, IOException {
        File data = new File("/tmp/data.txt");
        FileChannel fileChannel = new RandomAccessFile(data, "rw").getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(5 * 1024 * 1024);
        log.info("����۲� ��һ���ڴ���䣺");
        readStart("start");

        for (int i = 0; i < 50; i++) {
            Thread.sleep(200);
            Thread thread=new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        log.info("�̵߳�һ�ζ�ȡ��ʹ�ö����ڴ�,  �����˶����ڴ����");
                        fileChannel.read(buffer);
                        buffer.clear();

                        LockSupport.park();
                        log.info("�̵߳ڶ��ζ�ȡ��ʹ�ö����ڴ�,û�������ڴ�,  ������threadLocal�� cache");
                        fileChannel.read(buffer);
                        buffer.clear();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            list.add(thread);
            thread.start();
        }
        return list;
    }




    public static void main(String[] args) throws IOException, InterruptedException {
        ReadByHeapByteBufferTest readByHeapByteBufferTest=new ReadByHeapByteBufferTest();
        readByHeapByteBufferTest.oneCall();
        readByHeapByteBufferTest.contoll();
    }
}

