package hello.springboot.entity;

import com.alibaba.fastjson.JSON;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by @author linxin on 10/07/2018.  <br>
 */
@Builder
@Data
@Slf4j
public class Resp {

    int errCode;
    String msg;
    long callCount;

    static long calls=0;

    public  static  String success(){
        String s= JSON.toJSONString(Resp.builder()
                .msg("ok")
                .errCode(0)
                .callCount(calls++)
                .build());
        return s;
    }


    public  static  String fail(Request pram,String msg){
        String s=JSON.toJSONString(Resp.builder()
                .msg(msg)
                .errCode(-1).build());
        log.error("error found  param:{},msg:{}",JSON.toJSONString(pram),msg);
        return s;
    }

}
