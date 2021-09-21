package cn.gyw.corejava;

import cn.gyw.corejava.junit.JunitCustomRunner;
import cn.gyw.corejava.junit.annotations.JunitCustom;
import cn.gyw.corejava.junit.custom.JunitTimeInterceptor;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(value = JunitCustomRunner.class)
@JunitCustom(values = {
        JunitTimeInterceptor.class
})
public abstract class AbstractTest {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

}
