package server;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Test;
import org.zlx.rpc.rpcFrame.entity.Request;
import org.zlx.rpc.rpcFrame.io.Dispatcher;
import org.zlx.rpc.rpcFrame.io.server.Invocation;

import java.lang.reflect.InvocationTargetException;

public class InvocationTest {

    @Test
    public void testSalesOrder() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        IMocksControl control = EasyMock.createControl();


        Dispatcher dispatcher = control.createMock(Dispatcher.class);
        Request request = new Request();
        request.setService("org.zlx.rpc.app.service.HelloServiceI");
        request.setMethod("sayHi");
        request.setParam("zenglx");
        request.setParamType(String.class);

        Invocation invocation = new Invocation(dispatcher, request);
        invocation.run();

    }
}
