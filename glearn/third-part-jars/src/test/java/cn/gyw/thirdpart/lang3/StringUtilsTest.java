package cn.gyw.thirdpart.lang3;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * org.apache.
 */
public class StringUtilsTest {

    @Test
    public void shouldConfirmIsNull() {
        String str = null;
        Assert.assertTrue(StringUtils.isBlank(str));
        Assert.assertTrue(StringUtils.isEmpty(str));
    }
}
