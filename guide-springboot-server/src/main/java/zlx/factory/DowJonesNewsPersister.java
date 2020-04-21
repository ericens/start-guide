package zlx.factory;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class DowJonesNewsPersister {
    String name="persister";
    String url;
    String pwd;

    DowJonesNewsListener dowJonesNewsListener; //测试循环引用使用，如何
    public DowJonesNewsPersister() {
        log.info("DowJonesNewsPersister construct");
    }

}