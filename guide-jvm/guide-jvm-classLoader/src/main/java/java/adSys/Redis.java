package java.adSys;
import lombok.Data;

import	java.util.Date;

import java.util.HashMap;
import java.util.Map;

@Data
public class Redis {
    Map m=new HashMap<>();

    public Object get(String key){
        return m.get(key);
    }
    public void put(String key,Object value){
        m.put(key,value);
    }



}
