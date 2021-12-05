package cn.gyw.backend.resource.houseinfo.model.vo;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * 树状结构数据
 */
@Getter
@Setter
@NoArgsConstructor
public class TreeData {

    /**
     * 节点键
     */
    private String key;
    /**
     * 节点值
     */
    private String value;
    /**
     * 层级 1-...
     */
    private Integer level;
    /**
     * 子节点列表
     */
    private List<TreeData> children = Lists.newArrayList();

    public TreeData(String key, String value, Integer level) {
        this.key = key;
        this.value = value;
        this.level = level;
    }
}
