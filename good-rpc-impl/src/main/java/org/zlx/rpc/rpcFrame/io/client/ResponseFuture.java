package org.zlx.rpc.rpcFrame.io.client;

import org.zlx.rpc.rpcFrame.entity.Request;
import org.zlx.rpc.rpcFrame.entity.Response;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ResponseFuture implements Future<Response> {
    boolean isDone=false;

    Request request;
    Response response;

    ReentrantLock lock =new ReentrantLock();
    Condition readyCondition= lock.newCondition();

    public ResponseFuture(Request request){
        this.request=request;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    private Long defaultTime=5*1000L;
    @Override
    public Response get() throws InterruptedException{

        try {
            return   get(defaultTime,TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public Response get(long timeout, TimeUnit unit) throws TimeoutException, InterruptedException {
        lock.lock();
        Long start=System.currentTimeMillis();
        try {
            while(true){
                //TODO done 增加了调用超时，毕竟网络不可靠，不能无限等待返回。
                readyCondition.await(timeout,unit);
                if (isDone() || System.currentTimeMillis() - start > timeout) {
                    break;
                }
            }
            if (! isDone()) {
                throw new TimeoutException("调用远程服务超时");
            }
        }
        finally {
            lock.unlock();
        }
        return response;
    }

    public void setResponse(Response response){
        lock.lock();
        try {
            this.response = response;
            isDone = true;
            readyCondition.signalAll();
        }
        finally {
            lock.unlock();
        }
    }
}
