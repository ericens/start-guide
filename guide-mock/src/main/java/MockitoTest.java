import com.alibaba.fastjson.JSON;
import dao.Order;
import dao.SalesOrderDAO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

import static org.mockito.ArgumentMatchers.any;

@Slf4j
public class MockitoTest  {
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
        Mockito.when(iterator.next()).thenReturn("hello").thenReturn("world");
        //使用mock的对象
        String result = iterator.next() + " " + iterator.next() + " " + iterator.next();
        //验证结果
        Assert.assertEquals("hello world world",result);

        log.info("result:{}", result);


    }
}
