package junit.thread;

import lombok.extern.slf4j.Slf4j;

/**
 *  3 线程，
 *      1个线程打印一个字母A,
 *      1个线程打印一个字母B,
 *      1个线程打印一个字母C,
 *  轮流打印ABC,
 */
@Slf4j
public class ThreadPrintABC implements Runnable  {





    private String name;
    private Object notifyObj;
    private Object waiyObj;

    private ThreadPrintABC(String name, Object notifyObj, Object waiyObj) {
        this.name = name;
        this.notifyObj = notifyObj;
        this.waiyObj = waiyObj;
    }

    @Override
    public void run() {
        int count = 0;
        while (count < 20) {
            //先拥有前一个锁对象
            try {
                //在拥有自身锁对象
                synchronized (waiyObj) {
                    System.out.print(name);

                    //释放自身锁
                    waiyObj.notify();
                }

                synchronized (notifyObj) {
                    //释放对象
                    notifyObj.wait();
                }
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            count++;

        }
    }

    public static void main(String[] args) throws Exception {
        log.info("start ...");

        Object a = new Object();
        Object b = new Object();
        Object c = new Object();
        ThreadPrintABC pa = new ThreadPrintABC("A", c, a);
        ThreadPrintABC pb = new ThreadPrintABC("B", a, b);
        ThreadPrintABC pc = new ThreadPrintABC("C", b, c);


        new Thread(pa).start();
        new Thread(pb).start();
        new Thread(pc).start();

        Thread.sleep(1000);


    }
}
