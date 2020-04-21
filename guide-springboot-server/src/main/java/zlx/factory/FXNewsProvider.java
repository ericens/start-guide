package zlx.factory;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Service;
import zlx.factory.importBeanDefinitionRegistrarTest.Mapper;

@Data
@Service
@Mapper
public class FXNewsProvider  {
    private static final Logger log = LoggerFactory.getLogger(FXNewsProvider.class);

    String name="xxx";
    BeanFactory beanFactory;

    DowJonesNewsListener newsListener;
    DowJonesNewsPersister newPersistener;

    String nameWare;
    public FXNewsProvider(){
        log.info("FXNewsProvider construct");
    }
    public void print() {
        log.info("hh:n:{},dowJonesNewsListener{}", name, JSON.toJSONString(newsListener));
    }
}
