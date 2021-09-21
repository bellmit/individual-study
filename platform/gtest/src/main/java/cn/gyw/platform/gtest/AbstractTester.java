package cn.gyw.platform.gtest;

import cn.gyw.platform.gtest.annotations.GJunit;
import cn.gyw.platform.gtest.intercepters.GTimeIntercepter;
import cn.gyw.platform.gtest.runners.GInterceperRunner;

import org.junit.runner.RunWith;

@GJunit(values = {
        GTimeIntercepter.class
})
@RunWith(value = GInterceperRunner.class)
public abstract class AbstractTester {

}
