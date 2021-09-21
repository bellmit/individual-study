package cn.gyw.backend.fileserver.strategy;

import java.io.Serializable;
import java.util.Objects;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;

import cn.gyw.backend.fileserver.service.AppContext;
import cn.gyw.platform.common.util.SnowFlake;

/**
 * 主键生成策略
 */
public class SnowFlakeIdentityGenerator extends IdentityGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor s, Object obj) {
        SnowFlake snowFlake = AppContext.get().getBean(SnowFlake.class);
        Object id = snowFlake.nextId();
        if (Objects.nonNull(id)) {
            return (Serializable) id;
        }
        return super.generate(s, obj);
    }
}
