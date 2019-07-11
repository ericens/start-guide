package rtb.server.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import rtb.server.IRequestHandler;
import rtb.server.Request;
import rtb.server.Result;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by @author linxin on 2018/12/16.  <br>
 */
@Slf4j
public class MatrialHandler implements IRequestHandler {
    private IRequestHandler nextHandle;
    public void setNextHandle(IRequestHandler nextHandle) {
        this.nextHandle=nextHandle;
    }

    public Result handleRequet(Request request,Result result) {
        if((result= doHandle(request))!=null &&  result.getAid()!=null && result.getAid().size()>0 ){
            log.info("the result is :{}", JSON.toJSONString(result));
            result=nextHandle.handleRequet(request,result);
        }
        return result;
    }

    Result doHandle(Request request){
        int size=RandomUtils.nextInt(0,10);
        List list=new ArrayList(10);
        for(int i=0;i<size;i++){
            list.add(i);
        }
        return Result.builder()
                .aid(list)
                .build();
    }

}
