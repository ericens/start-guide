package junit.jvmExample;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 java -Djdk.internal.lambda.dumpProxyClasses junit.jvmExample.LambdaTest
 */
public class LambdaTest {
    public static void printString(String s, Print<String> print) {
        print.print(s);
    }
    public static void main(String[] args) {
        for (int i = 0; i<100; i++){
            Consumer<String> c = s -> System.out.println(s);
            System.out.println(c.hashCode());
        }

        printString("test", (x) -> System.out.println(x));

        foreachTest();
    }


    public static void foreachTest(){
        List<Integer> list=new ArrayList();
        for (Integer item: list) {
            System.out.println(item);
        }

        System.out.println("xxxxxxxxxxxxx");

        list.stream().forEach(
                item->  System.out.println(item)
        );
    }
}

interface Print<T> {
    public void print(T x);
}