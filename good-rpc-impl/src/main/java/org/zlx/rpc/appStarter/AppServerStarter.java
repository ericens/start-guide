package org.zlx.rpc.appStarter;

import org.zlx.rpc.rpcFrame.io.server.IOServer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutionException;

/**
 * Created by @author linxin on 13/10/2017.  <br>
 */
public class AppServerStarter {
    public static void main(String[] args) throws NoSuchMethodException, InterruptedException, ExecutionException, IOException, InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {

        IOServer ioServer=new IOServer();
        ioServer.start();

    }
}
