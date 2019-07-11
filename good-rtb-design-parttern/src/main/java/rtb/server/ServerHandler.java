package rtb.server;

import rtb.server.impl.BidFreqencyHandler;
import rtb.server.impl.ExposureFreqencyHandler;
import rtb.server.impl.MatrialHandler;
import rtb.server.impl.PriceHandler;

/**
 * Created by @author linxin on 2018/12/16.  <br>
 */
public class ServerHandler {
    static IRequestHandler matrialHandler;
    public ServerHandler(){

        matrialHandler=new MatrialHandler();

        IRequestHandler bidHandler=new BidFreqencyHandler();
        IRequestHandler exposureHandler=new ExposureFreqencyHandler();
        IRequestHandler priceHandler=new PriceHandler();

        matrialHandler.setNextHandle(bidHandler);
        bidHandler.setNextHandle(exposureHandler);
        exposureHandler.setNextHandle(priceHandler);
    }

    /**
     * 入口处理
     * @param request
     */
    public void handle(Request request){
        matrialHandler.handleRequet(request,null);

    }

}
