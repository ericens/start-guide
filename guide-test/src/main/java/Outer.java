import lombok.extern.slf4j.Slf4j;
import sun.reflect.CallerSensitive;
import sun.reflect.Reflection;

abstract class InnerClass {
    public abstract void print();
}
@Slf4j
public class Outer {

    public  void test1( String s1) {// 参数必须是final
        //成员内部类
        InnerClass c = new InnerClass() {
            public void print() {
                System.out.println(s1);
            }
        };
        c.print();
        log.info("sss{}",Reflection.getCallerClass());

    }

    public  void test2(String s2) {// 参数必须是final
        //匿名内部类
        new Outer() { //名字可以跟外部类一样
            public void qq() {
                System.out.println(s2);
            }
        }.qq();
        log.info("sss{}",Reflection.getCallerClass());
    }
    @CallerSensitive
    public static void main(String[] args) {
        log.info("sss{}",Reflection.getCallerClass());
        Outer o=new Outer();
        o.test1("inner1");
        o.test2("inner2");

        execute();
        o.test2("主方法已经 真的 over");

    }
    static public void execute() {
        log.info("sss{}",Reflection.getCallerClass());
        int s = 10;
        System.out.println("主方法已经over"+10);
//        s=20; //Variable 's' is accessed from within inner class, needs to be final or effectively final
        class InnerClass {
            public void execute() {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            log.info("sss{}",Reflection.getCallerClass());
                            Thread.currentThread().sleep(2000);
                            System.out.println(s);
                        } catch (final InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        }
        new InnerClass().execute();
        System.out.println("主方法已经over");
    }
}
