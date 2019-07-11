package rtb.server;

/**
 * Created by @author linxin on 2018/12/16.  <br>
 */
public interface IRequestHandler {
     /**
      * 下一个处理节点
      * @param nextHandle
      */
     void setNextHandle(IRequestHandler nextHandle);

     /**
      * 自己的处理逻辑
      * @param request
      * @param result
      * @return
      */
     Result  handleRequet(Request request,Result result);

}
