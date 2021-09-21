package cn.gyw.platform.configuration.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.gyw.platform.configuration.entity.Configuration;
import cn.gyw.platform.configuration.entity.ConfigurationPK;

import java.util.Collection;

/**
 * 数据操作类
 */
@Repository
public interface ConfigurationDao extends JpaRepository<Configuration, ConfigurationPK> {

    /**
     * 根据 项 section 查询配置
     * @param section
     * @return
     */
    Collection<Configuration> findBySection(String section);

    /**
     * 根据 项section 删除配置
     * @param section
     * @return
     */
    int removeBySection(String section);
}
