package org.zlx.rpc.rpcFrame.io.client;

import org.zlx.rpc.rpcFrame.entity.Request;
import org.zlx.rpc.rpcFrame.entity.Response;
import org.zlx.rpc.rpcFrame.io.Dispatcher;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ClientProxy {
    Dispatcher ioClient;

    public ClientProxy(Dispatcher ioClient){
        this.ioClient=ioClient;
    }

    public <T> T getRemoteProxyObj(final Class<?> serviceInterface){
        return (T) Proxy.newProxyInstance(
                serviceInterface.getClassLoader(),
                new Class<?>[]{serviceInterface},
                new ClinetInvocationHandler(serviceInterface,ioClient));
    }

    static class ClinetInvocationHandler implements InvocationHandler{
        final Class<?> serviceInterface;
        Dispatcher dispatcher;
        public ClinetInvocationHandler(final Class<?> serviceInterface,Dispatcher dispatcher){
            this.serviceInterface=serviceInterface;
            this.dispatcher=dispatcher;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws IOException {
            Request request=new Request();

            request.setService(serviceInterface.getName());
            request.setMethod(method.getName());

            StringBuffer stringBuffer=new StringBuffer();
            for (Object arg : args) {
                stringBuffer.append(arg.toString()).append(":");
            }
            request.setParamType(String.class);
            request.setParam(stringBuffer.toString());

            Response response=dispatcher.sendRequest(request);
            return response==null?null:response.getResp();
        }
    }

}
