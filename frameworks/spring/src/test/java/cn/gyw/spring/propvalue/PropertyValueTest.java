package cn.gyw.spring.propvalue;

import cn.gyw.spring.lifecycle.LifecycleConfig;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 属性测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {LifecycleConfig.class})
public class PropertyValueTest {
}
