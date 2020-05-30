package zlx;

import com.google.common.util.concurrent.RateLimiter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;

@Data
public class SingleRator{
    int seconds;
    int thresholds;
    RateLimiter rateLimiter;


    /** 反射的方式
     *
     */
    public void setMax(Object o, double max) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Class c= Class.forName("com.google.common.util.concurrent.SmoothRateLimiter");
        Field f=c.getDeclaredField("maxPermits");
        f.setAccessible(true);
        f.set(o,max);
    }

    public boolean tryAcquire(int count){
        return rateLimiter.tryAcquire(count);
    }



    public  SingleRator(int seconds,int thresholds ) throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException {
        this.seconds=seconds;
        this.thresholds=thresholds;

        double rate=((double)thresholds)/seconds;
        this.rateLimiter=RateLimiter.create(rate);
        setMax(rateLimiter,thresholds);

    }


}
