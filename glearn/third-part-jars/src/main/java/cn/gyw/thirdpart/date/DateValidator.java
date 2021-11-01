package cn.gyw.thirdpart.date;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO
 *
 * @date 2021/10/29 14:29
 */
public class DateValidator {

    private static final Logger log = LoggerFactory.getLogger(DateValidator.class);

    // private static final Pattern DATE_PATTERN = Pattern.compile("\\d{4}([-/.]?)\\d{1,2}\\1\\d{1,2}");
    private static final Pattern DATE_PATTERN = Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))((((0?[13578])|(1[02]))((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))((0?[1-9])|([1-2][0-9])|(30)))|(0?2((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))((((0?[13578])|(1[02]))((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))((0?[1-9])|([1-2][0-9])|(30)))|(0?2((0?[1-9])|(1[0-9])|(2[0-8]))))))");

    /**
     * 校验传入的字段日期格式
     * @param request 请求对象，一级对象
     * @param args 日期字段名数组，空字符串和null，不校验
     */
    public static void validate(Object request, String... args) {
        List<String> propList = Arrays.asList(args);
        if (request == null || CollectionUtils.isEmpty(propList)) {
            return;
        }
        for (String prop : propList) {
            String value = null;
            try {
                value = BeanUtils.getProperty(request, prop);
            } catch (Exception e) {
                log.warn("No such field: " + prop, e);
            }
            if (StringUtils.isNotEmpty(value)) {
                if (value.length() != 8) {
                    throw new RuntimeException("系统异常[Date length != 8]");
                }
                Matcher matcher = DATE_PATTERN.matcher(value);
                if (!matcher.matches()) {
                    throw new RuntimeException("系统异常[Date pattern error]");
                }
            }
        }
    }
}
