package cn.gyw.spring.propvalue;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 属性测试
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {PropertyValueConfig.class})
public class PropertyValueTest {

    @Autowired
    private ValueDIBean valueDIBean;

    @Test
    public void test() {
        Assert.assertTrue("DI property".equals(valueDIBean.getPropValue()));
    }
}
