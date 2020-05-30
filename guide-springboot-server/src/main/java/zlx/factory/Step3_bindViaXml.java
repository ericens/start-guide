package zlx.factory;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

@Slf4j
public class Step3_bindViaXml {


    /*************************************************************************************************************************************************************************************************/

    @Test
    public void bindViaCodeXmlTest(){
        DefaultListableBeanFactory beanRegistry = new DefaultListableBeanFactory();
        BeanFactory container = bindViaCodeXml(beanRegistry);
        A newsProvider = (A) container.getBean("a");
        newsProvider.toString();
    }

    /**
     * 通过xml 注入
     * @param registry
     * @return
     */
    public static BeanFactory bindViaCodeXml(BeanDefinitionRegistry registry) {
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(registry);
        reader.loadBeanDefinitions("bean-redenpendency.xml");

        return (BeanFactory)registry;

        // 或者直接 //return new XmlBeanFactory(new ClassPathResource("../news-config.xml")); }

    }




}
