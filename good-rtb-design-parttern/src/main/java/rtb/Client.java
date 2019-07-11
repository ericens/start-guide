package rtb;

import rtb.server.Request;
import rtb.server.ServerHandler;

/**
 * Created by @author linxin on 2018/12/16.  <br>
 */
public class Client {
    public static void main(String[] args) {
        ServerHandler serverHandler=new ServerHandler();

        serverHandler.handle(new Request());
    }
}
