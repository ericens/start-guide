package junit.aop;

import java.lang.instrument.Instrumentation;

/**
 * 如果 JVM 启动时开启了 JPDA， 那么类是允 许被重新加载的
 *

 Caused by: java.lang.UnsupportedOperationException: adding retransformable transformers is not supported in this environment
 Can-Retransform-Classes: true 解决上面的问题


 */
public class TestAgent {
    public static void agentmain(String args, Instrumentation inst) { // 指定我们自己定义的 Transformer，在其中利用 Javassist 做字节码替换
        inst.addTransformer(new TestTransformer(), true);
        try { // 重定义类并载入新的字节码
            inst.retransformClasses(Base.class);
            System.out.println("Agent Load Done.");

        } catch (Exception e) {

            System.out.println("agent load failed!");

        }

    }

}