import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class SetData2Object {

    @Test
    public void test2(){
        List s= Arrays.asList(2,3,3,234);
        for (Object x:s){
            log.info("xx:{}",x);
        }
    }
    public void setData(Object o){
        Field[] fields=o.getClass().getDeclaredFields();
        for (Field field : fields) {
            boolean isAccessible = field.isAccessible();
            if(field.isAccessible()==false){
                field.setAccessible(true);
                if(field.getType().isPrimitive()){

                }
                else if( field.equals("sss") ){

                }
                else if(field.getType().isArray()){

                }


            }
            field.setAccessible(isAccessible);
        }
    }
}
