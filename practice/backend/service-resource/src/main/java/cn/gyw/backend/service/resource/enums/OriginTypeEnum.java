package cn.gyw.backend.service.resource.enums;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据来源
 */
public enum OriginTypeEnum {

    FTX(0),
    OTHER(-1);

    private static final Logger log = LoggerFactory.getLogger(OriginTypeEnum.class);
    private Integer code;

    OriginTypeEnum(Integer code) {
        this.code = code;
    }

    /**
     * @param enumName 枚举名称
     * @return 枚举值
     */
    public static Integer getCode(String enumName) {
        if (StringUtils.isEmpty(enumName)) {
            log.info("Enum name is null, return OTHER");
            return OTHER.code;
        }
        for (OriginTypeEnum typeEnum : values()) {
            if (typeEnum.name().equalsIgnoreCase(enumName)) {
                return typeEnum.code;
            }
        }
        return OTHER.code;
    }
}
