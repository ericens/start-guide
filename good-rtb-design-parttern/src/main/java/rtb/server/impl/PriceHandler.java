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
public class PriceHandler implements IRequestHandler {
    IRequestHandler nextHandle;
    public void setNextHandle(IRequestHandler nextHandle) {
        this.nextHandle=nextHandle;
    }

    public Result handleRequet(Request request,Result result) {
        result=exec(request,result);
        log.info("the result is :{}", JSON.toJSONString(result));
        return result;
    }

    Result  exec(Request request,Result result){
        int index=RandomUtils.nextInt(0,result.getAid().size());
        List list=new ArrayList();
        list.add(result.getAid().get(index));
        result.setAid( list);
        result.setPrice(800L);
        result.setCode("sucdess");

        return result;
    }

}
