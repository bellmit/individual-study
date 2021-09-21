package cn.gyw.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 属性赋值服务
 */
@Service
public class AttrAssignService {

    @Value("${PURE_UPPERCASE_FIELD}")
    private String PURE_UPPERCASE_FIELD;

    public String getVal() {
        return PURE_UPPERCASE_FIELD;
    }
}
