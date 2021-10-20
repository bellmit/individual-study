package cn.gyw.components.web.utils;

import cn.gyw.components.web.enums.CommonRespEnum;
import cn.gyw.components.web.model.BaseRequest;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * @description 参数非空校验器
 * @createdTime 2021/10/20 15:33
 */
public final class ParamsValidator {

    private static final Logger log = LoggerFactory.getLogger(ParamsValidator.class);

    public static <R extends BaseRequest<?>> void validateParams(R request, String... args) {
        List<String> paramList = Arrays.asList(args);
        CommonRespEnum.PARAM_NULL.assertEmpty(paramList, "请求参数不能为空");

        paramList.forEach(param -> {
            String value = null;
            boolean flag = false;
            // 不可同时为空
            if (param.contains("#")) {
                String[] params = param.split("#");
                for (String p : params) {
                    try {
                        value = BeanUtils.getProperty(request.getData(), param);
                    } catch (Exception e) {
                        log.error("No such field: " + param);
                        log.error("异常：" + e);
                    }
                    if (StringUtils.isNotBlank(value)) {
                        flag = true;
                        break;
                    }
                }
                CommonRespEnum.PARAM_NULL.assertFalse(flag, "参数为空:" + param);
            } else {
                try {
                    value = BeanUtils.getProperty(request.getData(), param);
                } catch (Exception e) {
                    log.error("No such field: " + param);
                    log.error("异常：" + e);
                }
                CommonRespEnum.PARAM_NULL.assertNull(value, "参数为空:" + param);
            }
        });
    }

    private ParamsValidator() {

    }
}
