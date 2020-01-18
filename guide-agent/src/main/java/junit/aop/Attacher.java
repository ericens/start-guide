package junit.aop;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

import java.io.IOException;

public class Attacher {

    public static void main(String[] args) throws AttachNotSupportedException, IOException, AgentLoadException, AgentInitializationException { // 传入目标 JVM pid
        VirtualMachine vm = VirtualMachine.attach("49355");
        vm.loadAgent("/Users/ericens/git/my/start-guide/guide-agent/target/guide-agent-0.0.1-jar-with-dependencies.jar");

    }
}