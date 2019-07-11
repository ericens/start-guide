package rtb.server;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Created by @author linxin on 2018/12/16.  <br>
 */
@Builder
@Data
public class Result {
    String code;
    List aid;
    long price;
}
