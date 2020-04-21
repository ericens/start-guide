package zlx.factory;

import com.alibaba.fastjson.JSON;
import javassist.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.stream.Stream;

@Slf4j
@NoArgsConstructor
@Data
@Service
public class Config    {
    String str="bbb";
    public void  p(){
        log.info("ConfigConfigConfigConfigConfigConfigConfigConfigConfigConfigConfigConfig");
    }
}