package agents;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

import java.io.IOException;

/**
 * 1. 本jvm 链接到目标 jvm.
 * 2. 本jvm 运行执行的jar。 jar的MANI-FEST.MF中指定了agentmain方法的类。可以运行agent
 * 3. agent中对 目标jvm的类 进行refine. 重新定义
 */
public class Attacher {

    public static void main(String[] args) throws AttachNotSupportedException, IOException, AgentLoadException, AgentInitializationException { // 传入目标 JVM pid
        VirtualMachine vm = VirtualMachine.attach("49355");
        vm.loadAgent("/Users/ericens/git/my/start-guide/guide-agent/target/guide-agent-0.0.1-jar-with-dependencies.jar");

    }
}