package agents;

import zlx.aop.JavaAssistAopTest;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

public class TestTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        System.out.println("Transforming " + className);
        try {
            return JavaAssistAopTest.refineBytes();
        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;

    }

}