package cn.gyw.spring.config;

import java.io.IOException;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.stereotype.Controller;

import cn.gyw.spring.condition.LinuxCondition;
import cn.gyw.spring.condition.WindowsCondition;
import cn.gyw.spring.model.Cat;
import cn.gyw.spring.model.Dog;
import cn.gyw.spring.model.Green;
import cn.gyw.spring.model.Person;
import cn.gyw.spring.model.Red;
import cn.gyw.spring.service.HelloService;

/**
 * 配置类 == 配置文件
 */
@Configuration //告诉Spring这是一个配置类

//类中组件统一设置。满足当前条件，这个类中配置的所有bean注册才能生效；
@Conditional(value = {WindowsCondition.class})

@ComponentScans({
        @ComponentScan(basePackages = {"cn.gyw.spring"}, includeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class}),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {HelloService.class}),
                @ComponentScan.Filter(type = FilterType.CUSTOM, classes = {MainConfigOfCreateBean.ComponentFilter.class})
        }, useDefaultFilters = false)
})
/*
@ComponentScan  value:指定要扫描的包
excludeFilters = Filter[] ：指定扫描的时候按照什么规则排除那些组件
includeFilters = Filter[] ：指定扫描的时候只需要包含哪些组件
FilterType.ANNOTATION：按照注解
FilterType.ASSIGNABLE_TYPE：按照给定的类型；
FilterType.ASPECTJ：使用ASPECTJ表达式
FilterType.REGEX：使用正则指定
FilterType.CUSTOM：使用自定义规则
*/
//@Import导入组件，id默认是组件的全类名
@Import(value = {Cat.class, Dog.class, MainConfigOfCreateBean.MyBeanSelector.class, })
public class MainConfigOfCreateBean {

    //给容器中注册一个Bean;类型为返回值的类型，id默认是用方法名作为id
    @Bean
    public Person person() {
        return new Person();
    }

    //默认是单实例的
    /**
     * ConfigurableBeanFactory#SCOPE_PROTOTYPE
     * @see ConfigurableBeanFactory#SCOPE_SINGLETON
     * @see org.springframework.web.context.WebApplicationContext#SCOPE_REQUEST  request
     * @see org.springframework.web.context.WebApplicationContext#SCOPE_SESSION	 sesssion
     * @return
     * @Scope:调整作用域
     * prototype：多实例的：ioc容器启动并不会去调用方法创建对象放在容器中。
     * 					每次获取的时候才会调用方法创建对象；
     * singleton：单实例的（默认值）：ioc容器启动会调用方法创建对象放到ioc容器中。
     * 			以后每次获取就是直接从容器（map.get()）中拿，
     * request：同一次请求创建一个实例
     * session：同一个session创建一个实例
     *
     * 懒加载：
     * 		单实例bean：默认在容器启动的时候创建对象；
     * 		懒加载：容器启动不创建对象。第一次使用(获取)Bean创建对象，并初始化；
     *
     */
//    @Scope(value = "prototype")
    @Lazy
    @Bean("commonPerson")
    public Person commonPerson() {
        System.out.println(">> 添加bean ...");
        return new Person(1001L, "普通人", 24);
    }

    /**
     * @Conditional({Condition}) ： 按照一定的条件进行判断，满足条件给容器中注册bean
     *
     * 如果系统是windows，给容器中注册("lisi")
     * 如果是linux系统，给容器中注册("wangwu")
     */
    @Conditional(value = {WindowsCondition.class})
    @Bean
    public Person liSi() {
        return new Person(1002L, "李四", 24);
    }

    @Conditional({LinuxCondition.class})
    @Bean
    public Person wangWu() {
        return new Person(1003L, "王五", 24);
    }

    /**
     * 扫面路径过滤
     */
    public static class ComponentFilter implements TypeFilter {

        /**
         * metadataReader：读取到的当前正在扫描的类的信息
         * metadataReaderFactory:可以获取到其他任何类信息的
         */
        @Override
        public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
            //获取当前类注解的信息
            AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
//            annotationMetadata.getAnnotationTypes().forEach(System.out::println);
            //获取当前正在扫描的类的类信息
            ClassMetadata classMetadata = metadataReader.getClassMetadata();
            // System.out.println("当前类全限定名：" + classMetadata.getClassName());
            //获取当前类资源（类的路径）
            Resource resource = metadataReader.getResource();

            String className = classMetadata.getClassName();
            if (className.contains("version4") || className.contains("version5")) {
                return false; // 不注入
            }
            return false; // 注入
        }
    }

    /**
     * 给容器中注册组件；
     * 1）、包扫描+组件标注注解（@Controller/@Service/@Repository/@Component）[自己写的类]
     * 2）、@Bean[导入的第三方包里面的组件]
     * 3）、@Import[快速给容器中导入一个组件]
     * 		1）、@Import(要导入到容器中的组件)；容器中就会自动注册这个组件，id默认是全类名
     * 		2）、ImportSelector:返回需要导入的组件的全类名数组；
     * 		3）、ImportBeanDefinitionRegistrar:手动注册bean到容器中
     * 4）、使用Spring提供的 FactoryBean（工厂Bean）;
     * 		1）、默认获取到的是工厂bean调用getObject创建的对象
     * 		2）、要获取工厂Bean本身，我们需要给id前面加一个&
     * 			&colorFactoryBean
     */

    /**
     * Bean 选择器
     */
    //自定义逻辑返回需要导入的组件
    public static class MyBeanSelector implements ImportSelector {

        //返回值，就是到导入到容器中的组件全类名
        //AnnotationMetadata:当前标注@Import注解的类的所有注解信息
        @Override
        public String[] selectImports(AnnotationMetadata annotationMetadata) {
            //获取当前类注解的信息
            annotationMetadata.getAnnotationTypes();

            //importingClassMetadata
            //方法不要返回null值
            return new String[] {
                    "cn.gyw.model.pojo.SubClass",
                    "cn.gyw.model.pojo.SuperClass"
            };
        }
    }

    /**
     * 手动注入Bean
     */
    public static class MyDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

        /**
         * AnnotationMetadata：当前类的注解信息
         * BeanDefinitionRegistry:BeanDefinition注册类；
         * 		把所有需要添加到容器中的bean；调用
         * 		BeanDefinitionRegistry.registerBeanDefinition手工注册进来
         */
        @Override
        public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {

            boolean redDefinition = beanDefinitionRegistry.containsBeanDefinition("cn.gyw.model.pojo.Blue");
            boolean blueDefinition = beanDefinitionRegistry.containsBeanDefinition("cn.gyw.model.pojo.Red");

            if (redDefinition && blueDefinition) {
                //指定Bean定义信息；（Bean的类型)
                BeanDefinition beanDefinition = new RootBeanDefinition(Green.class);
                // 注册一个bean，指定bean 名称
                beanDefinitionRegistry.registerBeanDefinition("green", beanDefinition);
            }
        }
    }

    @Bean
    public RedFactoryBean redFactoryBean() {
        return new RedFactoryBean();
    }

    /**
     * 工厂Bean
     */
    public static class RedFactoryBean implements FactoryBean<Red> {

        //返回一个Color对象，这个对象会添加到容器中
        @Override
        public Red getObject() throws Exception {
            System.out.println("RedFactoryBean ... getObject...");
            return new Red();
        }

        @Override
        public Class<?> getObjectType() {
            return Red.class;
        }

        //是单例？
        //true：这个bean是单实例，在容器中保存一份
        //false：多实例，每次获取都会创建一个新的bean
        @Override
        public boolean isSingleton() {
            return true;
        }
    }

}
