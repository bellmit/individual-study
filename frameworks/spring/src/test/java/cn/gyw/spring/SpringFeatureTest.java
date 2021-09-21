package cn.gyw.spring;

import org.junit.Test;

import cn.gyw.spring.external.ServiceRegister;
import cn.gyw.spring.service.DataService;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Unit test for spring framework.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class SpringFeatureTest {

//    @Autowired // 无法使用，根据Type 来自动装配，有两个实现类，报错；可以和@Qulifiy 组合
//    @Resource(name = "databaseService")
    private DataService dataService;

//    @Autowired
    private ServiceRegister serviceRegister;

    /**
     * 一个接口，两个实现类
     *
     * 动态根据参数配置指定使用什么实现类
     */
    @Test
    public void shouldSpecifyServiceImpl() {
        System.out.println(dataService.getClass().getName());
    }

//    @Autowired // 方法级别的Autowired
    public void setDataService() {
        this.dataService = serviceRegister.getDataService(true);
    }
}
