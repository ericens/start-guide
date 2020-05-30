import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import mock.dao.Order;
import mock.dao.SalesOrderDAO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.*;
import java.util.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.any;

@Slf4j
public class MockitoTest  {
    List<String > sx=new ArrayList<>();


    public List foo(){
        List s=new ArrayList<>();
        s.add("ssss");
        sx.add("aaaa");

        String x=sx.get(0);
        return s;
    }

    public List<String > bar(){
        List f=foo();
        f.add(new Integer(222));
        return f;
    }


    @Test
    public void goo(){
        for (Object o : bar()) {
            log.info("{}",o);
        }
    }

    /**
     *  voidTest 和 voidTest2字节码 一样
     *  申明放在循环里 没有消耗性能。
     */
    public void voidTest(){
        List list=new ArrayList(16);
        Map m;
        for (int i = 0; i < 10; i++) {
          m=new HashMap();
          list.add(m);
        }
    }


    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class MySocket implements AutoCloseable{
        String name;

        @Override
        public void close() throws IOException {
            log.info("close :{}",name);
            if(RandomUtils.nextBoolean()){
                throw new IOException("dddddddd"+name);
            }
        }
        public void write() throws IOException{
            log.info("write:{}",name);
            if(RandomUtils.nextBoolean()){
                throw new IOException("yyyyyyy"+name);
            }
        }
    }


    @Test
    public void testAutoClose(){
        try( InputStream fileInputStream=new FileInputStream("sss");
             BufferedInputStream f=new BufferedInputStream(fileInputStream);
             BufferedReader fx=new BufferedReader(new InputStreamReader(f))
        ) {
            fx.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 10 ; i++) {
            try( MySocket socket=new MySocket("xxx"+i)){
                socket.write();
            }catch (Exception e){
                e.printStackTrace();
            }
        }



    }

    public void voidTest2(){
        List list=new ArrayList(16);
        for (int i = 0; i < 10; i++) {
            Map m=new HashMap();
            list.add(m);
        }

    }

    /**
     * 传入调用方法中的参数，可以使用Mockito中的any()来做参数的匹配，
     * 代表任意的值，还有anyString(), anyInt(), any(Class<T> clazz)等，具体可以查看org.mockito.Matchers。
     * @throws SQLException
     */
    @Test
    public void testSalesOrder() throws SQLException {

        Order order=Order.builder()
                .region("regiion")
                .orderNumber("number")
                .totalPrice(12.2).build();

        ResultSet resultSet=Mockito.mock(ResultSet.class);
        SalesOrderDAO salesOrderDAO=Mockito.mock(SalesOrderDAO.class);
        Mockito.when(salesOrderDAO.loadDataFromDB(any(ResultSet.class) )).thenReturn(order);

        log.info("result:{}", JSON.toJSONString(salesOrderDAO.loadDataFromDB(resultSet)));
    }



    @Test
    public void when_thenReturn(){
        //mock一个Iterator类
        Iterator iterator = Mockito.mock(Iterator.class);
        //预设当iterator调用next()时第一次返回hello，第n次都返回world
        Mockito.when(iterator.next()).thenReturn("zlx.hello").thenReturn("world");
        //使用mock的对象
        String result = iterator.next() + " " + iterator.next() + " " + iterator.next();
        //验证结果
        Assert.assertEquals("zlx.hello world world",result);

        log.info("result:{}", result);


    }
}
