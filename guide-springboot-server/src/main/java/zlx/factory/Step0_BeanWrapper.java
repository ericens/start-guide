package zlx.factory;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

@Slf4j
public class Step0_BeanWrapper {
    /**
     *  通过BeanWrapper，设置bean ,已经bean的属性
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @Test
    public  void wrap() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Object provider = Class.forName(FXNewsProvider.class.getName()).newInstance();
        Object listener = Class.forName(DowJonesNewsListener.class.getName()).newInstance();
        Object persister = Class.forName(DowJonesNewsPersister.class.getName()).newInstance();

        BeanWrapper newsProvider = new BeanWrapperImpl(provider);
        newsProvider.setPropertyValue("newsListener", listener);
        newsProvider.setPropertyValue("newPersistener", persister);

        assert  (newsProvider.getWrappedInstance() instanceof FXNewsProvider);
        assert (provider == newsProvider.getWrappedInstance());
        assert (listener == newsProvider.getPropertyValue("newsListener"));
        assert (persister == newsProvider.getPropertyValue("newPersistener"));

        log.info("last:{}", JSON.toJSONString(newsProvider.getWrappedInstance()));
    }

}
