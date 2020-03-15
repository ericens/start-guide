package junit.jvmExample;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class JvmExample {



    @Test
    public void test(){
        GrandFater f=new Son();
        f.print();


        for (int i = 0; i < 1000000000; i++) {
            StringBuffer stringBuffer=new StringBuffer();
            List li=new ArrayList(2);
            for (int j = 0; j < 10000; j++) {
                stringBuffer.append(i);
            }
        }

        new Thread(
                () -> System.out.println("Lambda Thread run()")
        ).start();

    }

    public static void main(String[] args) throws InterruptedException {
        JvmExample s=new JvmExample();
        s.test();

    }


    @Test
    public void test2() throws InterruptedException {



    }


}
