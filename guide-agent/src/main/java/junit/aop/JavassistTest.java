package junit.aop;

import javassist.*;

import java.io.IOException;

public class JavassistTest {

    public static void main(String[] args) throws NotFoundException, CannotCompileException, IllegalAccessException, InstantiationException, IOException {
//        Base b=new Base();

        ClassPool cp = ClassPool.getDefault();
        CtClass cc = cp.get("junit.aop.Base");
        CtMethod m = cc.getDeclaredMethod("process");
        m.insertBefore("{ System.out.println(\"start\"); }");
        m.insertAfter("{ System.out.println(\"end\"); }");
        Class c = cc.toClass();
        cc.writeFile("/Users/ericens/git/my/start-guide/guide-test/target");
        Base h = (Base) c.newInstance();
        h.process();

    }

}