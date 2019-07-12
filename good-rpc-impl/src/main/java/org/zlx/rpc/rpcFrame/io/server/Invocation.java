package org.zlx.rpc.rpcFrame.io.server;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.zlx.rpc.rpcFrame.entity.Request;
import org.zlx.rpc.rpcFrame.entity.Response;
import org.zlx.rpc.rpcFrame.io.Dispatcher;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class Invocation implements Runnable {
    //TODO 初始化时候把 服务暴露出去
    static ConcurrentHashMap<String,Class> serviceMap=new ConcurrentHashMap();

    Request request;

    Class serviceClass;
    Dispatcher dispatcher;
    Method method;



    static {
        serviceMap.put(org.zlx.rpc.appStarter.service.HelloServiceI.class.getName(), org.zlx.rpc.appStarter.service.HelloServiceIImpl.class);
    }


    public Invocation(Dispatcher dispatcher, Request request) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        if(StringUtils.isBlank(request.getService())
                || request.getMethod()==null
                || request.getParam()==null
                || request.getParamType()==null ) {
            log.info("error method:{} ", request);
        }

        this.dispatcher = dispatcher;
        this.request=request;
        this.serviceClass = serviceMap.get(request.getService());
        if (serviceClass == null) {
            throw new ClassNotFoundException(request.getService() + " not found");
        }
        method = serviceClass.getMethod(request.getMethod(), request.getParamType());
    }


    @Override
    public void run() {
        //执行业务方法
        Object result=null;
        try {
            //TODO 模拟5s 延迟
            Thread.sleep(1000*2);
            result = method.invoke(serviceClass.newInstance(), request.getParam());
            log.debug("the invoke result is :{}",result);
        } catch (Exception e) {
            e.printStackTrace();
        }


        //返回方法结果
        Response response=new Response();
        response.setReqId(request.getReqId());
        response.setResp(result);
        try {
            dispatcher.sendResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

