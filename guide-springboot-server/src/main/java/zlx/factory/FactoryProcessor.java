package zlx.factory;

import com.alibaba.fastjson.JSON;
import javassist.*;
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
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import java.io.IOException;
import java.util.stream.Stream;

@Slf4j
@NoArgsConstructor
public class FactoryProcessor implements InitializingBean, BeanPostProcessor, BeanNameAware, BeanFactoryPostProcessor  {
    String name="xxx";
    BeanFactory beanFactory;
    String nameWare;


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        log.error("postProcessBeforeInitialization........"+ JSON.toJSONString(bean));
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        log.error("postProcessAfterInitialization....."+ JSON.toJSONString(bean));
        return bean;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.error("InitializingBean....afterPropertiesSet .");
    }

    @Override
    public void setBeanName(String name) {
        log.info("BeanNameAware................................."+name);
        this.nameWare=name;
    }

    /**
     * bean factory已经经历了standard initialization；
     * 方法的入参ConfigurableListableBeanFactory beanFactory即是standard initialization后的bean zlx.factory；
     * 把standard initialization后的bean factory交给我们处理，我们可以对其进行修改。
     * ————————————————
     * 版权声明：本文为CSDN博主「微风不躁」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
     * 原文链接：https://blog.csdn.net/hhx_echo/article/details/76684615
     * @param beanFactory
     * @throws BeansException
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        log.info("BeanFactoryPostProcessor.................................");

        Stream.of(beanFactory.getBeanDefinitionNames()).forEach(
                i-> {
                    log.info(i);

                }
        );
        String key="djNewsListener";

        DefaultListableBeanFactory defaultListableBeanFactory
                = (DefaultListableBeanFactory) beanFactory;

        try {
//            BeanDefinition beanDefinition= beanFactory.getBeanDefinition(key);
//           // Class c=subClass(beanDefinition.getBeanClassName());
//           // beanDefinition.setBeanClassName(c.getCanonicalName());
//
//            defaultListableBeanFactory.removeBeanDefinition(key);
//            defaultListableBeanFactory.registerBeanDefinition(key,beanDefinition);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 定义子类
     *  1. 子类 前后自己的方法
     *  2. 子类 中间调用父类的方法。 出现代理不能A, B，调用B的情况。
     *
     *  1. 子类覆盖 父类所有的方法
     *  2. spring中bean的 引用，引用到子类实例。 所有调用走子类。
     *  3. 由于覆盖父类所有方法，必然从写很多属性。
     * @param className
     * @return
     * @throws NotFoundException
     * @throws CannotCompileException
     * @throws IOException
     */
    public static Class subClass(String className) throws NotFoundException, CannotCompileException, IOException {
        ClassPool cp = ClassPool.getDefault();
        CtClass prarentClass = cp.get(className);


        CtClass subClass=cp.makeClass(className+"$1");
        subClass.setSuperclass(prarentClass);

        Stream.of(prarentClass.getDeclaredFields()).forEach(
                f->{
                    CtField myField = null;
                    try {
                        myField = new CtField(f.getType(), f.getName(), subClass);
                        myField.setModifiers( f.getModifiers());
                        if(f.getGenericSignature()!=null){
                            myField.setGenericSignature( f.getGenericSignature());
                        }
                        Object constantValue=f.getConstantValue();

                        // 初始值是 "xiaoming"
//                        sb.addField(param, CtField.Initializer.constant("xiaoming"));
                        subClass.addField(myField);
                    } catch (CannotCompileException e) {
                        e.printStackTrace();
                    } catch (NotFoundException e) {
                        e.printStackTrace();
                    }

                }
        );


        // 添加无参的构造体
        CtConstructor parentConstructor = new CtConstructor(new CtClass[] {}, subClass);
        parentConstructor.setBody("{ System.out.println(\" constructsubClass... \"); }");
        subClass.addConstructor(parentConstructor);



        CtMethod m = prarentClass.getDeclaredMethod("process1");
        CtMethod newMethod = CtNewMethod.copy(m, subClass, null);

        newMethod.insertBefore("{ System.out.println(\"start\"); }");
        newMethod.insertAfter("{ System.out.println(\"end\"); }");
        subClass.addMethod(newMethod);

        subClass.writeFile("/Users/ericens/tmp/");


//
//        CtMethod m2 = cc.getDeclaredMethod("process2");
//        m2.insertBefore("{ System.out.println(\"start\"); }");
//        m2.insertAfter("{ System.out.println(\"end\"); }");
//        cc.writeFile("/Users/ericens/git/my/start-guide/guide-test/target");


        Class c = subClass.toClass();
        return c;
    }

    /**
     * 虽然可以在 targetBean实例化前，进行初始化。走到BeanFactoryPostProcessor时，class 已经加载。
     * 最后，linked error. 重复的定义。除非自定义类加载器。
     * -XX:+TraceClassLoading 查看
     * @param className
     * @return
     * @throws NotFoundException
     * @throws CannotCompileException
     * @throws IOException
     */
    public Class modifyClass(String className) throws NotFoundException, CannotCompileException, IOException {
        ClassPool cp = ClassPool.getDefault();
        CtClass cc = cp.get(className);
        CtMethod m = cc.getDeclaredMethod("process1");
        m.insertBefore("{ System.out.println(\"start\"); }");
        m.insertAfter("{ System.out.println(\"end\"); }");
        cc.writeFile("/Users/ericens/tmp/");

//        if(true){
//            this.getClass().getClassLoader().
//        }
        return cc.toClass();


    }

}
