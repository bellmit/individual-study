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
     * 层级 1-...
     */
    private Integer level;
    /**
     * 节点名称
     */
    private String name;
    /**
     * 子节点列表
     */
    private List<TreeData> children = Lists.newArrayList();

    public TreeData(String name) {
        this.name = name;
    }

    public TreeData(Integer level, String name) {
        this.level = level;
        this.name = name;
    }
}
