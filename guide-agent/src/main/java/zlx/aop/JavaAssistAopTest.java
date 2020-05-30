package zlx.aop;

import javassist.*;
import toTestExample.Base;

import java.io.IOException;

public class JavaAssistAopTest {

    public static byte[] refineBytes() throws NotFoundException, CannotCompileException, IOException {
        CtClass cc= doRefine();
        Class c = cc.toClass();
        return cc.toBytecode();
    }

    public static Class refineClass() throws NotFoundException, CannotCompileException, IOException {
        CtClass cc= doRefine();
        Class c = cc.toClass();
        return c;
    }

    public static CtClass doRefine() throws NotFoundException, CannotCompileException, IOException {
        ClassPool cp = ClassPool.getDefault();
        CtClass cc = cp.get("toTestExample.Base");
        CtMethod m = cc.getDeclaredMethod("process");
        m.insertBefore("{ System.out.println(\"start\"); }");
        m.insertAfter("{ System.out.println(\"end\"); }");
        cc.writeFile("/Users/ericens/git/my/start-guide/guide-test/target");
        return cc;
    }


    public static void main(String[] args) throws NotFoundException, CannotCompileException, IllegalAccessException, InstantiationException, IOException {
//        Base b=new Base();
        Class c= refineClass();
        Base h = (Base) c.newInstance();
        h.process();

    }

}