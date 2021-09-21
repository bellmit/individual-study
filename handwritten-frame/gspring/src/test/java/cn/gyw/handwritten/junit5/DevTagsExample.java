package cn.gyw.handwritten.junit5;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;

/**
 * 多个Tags 使用方式
 *
 */
@RunWith(JUnitPlatform.class)
@SelectPackages("cn.gyw.handwritten.junit5")
@IncludeTags("dev")
public class DevTagsExample {

}
