package zlx.factory.importBeanDefinitionRegistrarTest;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import zlx.factory.FactoryProcessor;

/**
 * 1. 多引入：引入 MapperAutoConfiguredMyBatisRegistrar.class,FactoryProcessor.class
 * 2. 单引入: MapperAutoConfiguredMyBatisRegistrar
 *
 * 3. MapperAutoConfiguredMyBatisRegistrar里面有 包扫描，
 *    1. 指定扫描范围
 *    2. 指定扫描目标，注解Mapper.class
 */
@Configuration
//@Import(value = {MapperAutoConfiguredMyBatisRegistrar.class,FactoryProcessor.class})
@Import(value = {MapperAutoConfiguredMyBatisRegistrar.class})
public class MapperAutoConfig {
}
