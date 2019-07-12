package util;

import org.junit.Test;
import org.zlx.rpc.rpcFrame.entity.Role;
import org.zlx.rpc.rpcFrame.io.Dispatcher;
import org.zlx.rpc.rpcFrame.io.client.IOClient;

public class AssertTest {

    @Test
    public void RoleTest(){
        Dispatcher dispatcher=new Dispatcher(new IOClient());
        dispatcher.setRole(Role.server);

        assert dispatcher.getRole().equals(Role.client):" 角色错误";
    }
}
