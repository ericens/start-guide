package org.zlx.rpc.appStarter;

import lombok.extern.slf4j.Slf4j;
import org.zlx.rpc.rpcFrame.io.client.ClientProxy;
import org.zlx.rpc.rpcFrame.io.client.IOClient;
import org.zlx.rpc.appStarter.service.HelloServiceI;

/**
 * Created by @author linxin on 13/10/2017.  <br>
 *
 */

@Slf4j
public class AppClientStarter<T> {

    public static void main(String[] args) {
            log.info("server start....");
        IOClient ioClient=new IOClient();
        try {
            ioClient.connect();
        } catch (Exception e) {
            log.error("error when connect ",e);
        }

        ClientProxy clientProxy=new ClientProxy(ioClient.getDispatcher());
        HelloServiceI helloServiceI=clientProxy.getRemoteProxyObj(HelloServiceI.class);

        //TODO 加入多线程测试，看看能发送信息 和接受信息正确。
        while (true) {
            try {
                String param="hello server";
                log.info("call server:{}",param);

                Object echoStr=helloServiceI.sayHi(param);
                log.info("echo str:{}",echoStr);
                Thread.sleep(1000);
            } catch (Exception e) {
                log.error("error invoke remote socket ",e);
            }

        }


    }

}
