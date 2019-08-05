import com.alibaba.fastjson.JSON;
import mock.dao.Order;
import mock.dao.SalesOrderDAO;
import mock.dao.impl.SalesOrderDAOImpl;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;

import java.sql.ResultSet;

import static org.easymock.EasyMock.expectLastCall;

@Slf4j
public class EasyMockTest extends TestCase {

    public void testSalesOrder() {
        IMocksControl control = EasyMock.createControl();

        ResultSet mockResultSet = control.createMock(ResultSet.class);
        try {

            mockResultSet.next();
            expectLastCall().andReturn(true).times(2);
            expectLastCall().andReturn(false).times(1);

            mockResultSet.getString(1);
            expectLastCall().andReturn("DEMO_ORDER_001").times(1);
            expectLastCall().andReturn("DEMO_ORDER_002").times(1);
            expectLastCall().andReturn("DEMO_ORDER_003").times(1);

            mockResultSet.getString(2);
            expectLastCall().andReturn("Asia Pacific").times(1);
            expectLastCall().andReturn("Europe").times(1);
            expectLastCall().andReturn("America").times(1);

            mockResultSet.getDouble(3);
            expectLastCall().andReturn(350.0).times(1);
            expectLastCall().andReturn(1350.0).times(1);
            expectLastCall().andReturn(5350.0).times(1);

            control.replay();


            while (mockResultSet.next()) {
                SalesOrderDAO orderImpl = new SalesOrderDAOImpl();
                Order order=orderImpl.loadDataFromDB(mockResultSet);
                log.info(JSON.toJSONString(order));
            }
            control.verify();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
