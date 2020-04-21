package zlx.factory;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;

@Slf4j
public class Step1_bindViaCode {
    @Test
    public void bindViaCodeTest(){
        DefaultListableBeanFactory beanRegistry = new DefaultListableBeanFactory();
        BeanFactory container = bindViaCode(beanRegistry);
        FXNewsProvider newsProvider = (FXNewsProvider) container.getBean("provider");
        newsProvider.print();
    }

    /**
     * 通过定义RootBeanDefinition，实现注册到BeanFactory中
     * @param registry
     * @return
     */
    public  BeanFactory bindViaCode(BeanDefinitionRegistry registry) {
        AbstractBeanDefinition provider = new RootBeanDefinition(FXNewsProvider.class);
        AbstractBeanDefinition listener = new RootBeanDefinition(DowJonesNewsListener.class);

        registry.registerBeanDefinition("provider", provider);
        registry.registerBeanDefinition("dowJonesNewsListener", listener);

        MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("newsListener", listener));
        provider.setPropertyValues(propertyValues);
        return (BeanFactory) registry;
    }

}
