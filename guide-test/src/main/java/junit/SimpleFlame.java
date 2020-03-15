package junit;
import	java.util.ArrayList;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import	java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class SimpleFlame {




    @Test
    public  void test(){
        Runtime runtime=Runtime.getRuntime();
        Map map=new HashMap(4);
        for (int i = 0; i < 4; i++) {
            String key="22"+i;
            map.put(key,key);
        }

        map.put("222","ssss");
    }

    static int count=0;
    public static void main(String[] args) throws InterruptedException {
        SimpleFlame simpleFlame=new SimpleFlame();
        simpleFlame.foo();
    }

    public void foo() {
        bar();
    }

    private void bar() {
        bar2();
    }

    private void bar2() {
        while(true) {
            Math.sqrt(22000002);
        }
//        System.out.println("bar"+(count++));

    }
}
