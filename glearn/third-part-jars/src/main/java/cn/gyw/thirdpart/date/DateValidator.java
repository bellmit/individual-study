package cn.gyw.thirdpart.date;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * TODO
 *
 * @date 2021/10/29 14:29
 */
public class DateValidator {

    // private static final Pattern DATE_PATTERN = Pattern.compile("\\d{4}([-/.]?)\\d{1,2}\\1\\d{1,2}");

    private static final String EL = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\.\\s]?((((0?[13578])|(1[02]))[\\-\\/\\.\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\.\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\.\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\.\\s]?((((0?[13578])|(1[02]))[\\-\\/\\.\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\.\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\.\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
    private static final Pattern DATE_PATTERN = Pattern.compile(EL);

    /**
     * 校验传入的日期格式
     *
     * @param args 日期字段可变数组
     */
    public static void validate(String... args) {
        List<String> argList = Stream.of(args).filter(StringUtils::isNotEmpty)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(argList)) {
            return;
        }

        for (String arg : argList) {
            Matcher matcher = DATE_PATTERN.matcher(arg);
            if (!matcher.matches()) {
                throw new RuntimeException("日期格式不正确");
            }
        }
    }
}
