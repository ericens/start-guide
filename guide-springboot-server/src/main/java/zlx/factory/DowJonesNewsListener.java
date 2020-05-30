package zlx.factory;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.*;
import org.springframework.core.io.ResourceLoader;

@Data
public class DowJonesNewsListener implements InitializingBean , BeanNameAware,
        BeanClassLoaderAware, BeanFactoryAware ,DisposableBean,
        ResourceLoaderAware,
        ApplicationContextAware,
        ApplicationEventPublisherAware{
    private static final Logger log = LoggerFactory.getLogger(DowJonesNewsListener.class);



    String name="listener";
    DowJonesNewsPersister persister;

    public DowJonesNewsListener() {
        log.info("DowJonesNewsListener construct");
    }

    public void process1(){
           log.info("this DowJonesNewsListener processing1111");
           this.process2();
    }

    public void process2(){
        log.info("this DowJonesNewsListener processing22222");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("InitializingBean....afterPropertiesSet .");
    }

    @Override
    public void setBeanName(String name) {
        log.info("BeanNameAware................................." + JSON.toJSONString(this));
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        log.info("BeanClassLoaderAware...............................setBeanClassLoader."+name);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        log.info("BeanFactoryAware...............................setBeanFactory"+name);

    }

    /**
     *         ((ClassPathXmlApplicationContext) context).registerShutdownHook(); 使其生效
     * @throws Exception
     */
    @Override
    public void destroy() throws Exception {
        log.info("DisposableBean...............................destroy"+ JSON.toJSONString(this));

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.info("ApplicationContextAware...............................setApplicationContext");

    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        log.info("ApplicationEventPublisherAware...............................setApplicationEventPublisher");

    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        log.info("ResourceLoaderAware...............................setResourceLoader");

    }
}