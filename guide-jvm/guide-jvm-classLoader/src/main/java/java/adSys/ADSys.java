package java.adSys;
import java.jvmDebugTool.HotSwapClassLoader;
import java.util.UUID;
import	java.util.concurrent.TimeUnit;

public class ADSys {

    public static void main(String[] args) throws InterruptedException {

        int count= 1;
        Redis redis=new Redis();
        while(true){
            TimeUnit.SECONDS.sleep(1);
            redis.put(UUID.randomUUID().toString(),count++);

           checkJspDir("");

        }
    }

    static public void checkJspDir(String path){
        boolean ischange=true;
        byte[] bytes;
        if(ischange){
        }
    }


}
