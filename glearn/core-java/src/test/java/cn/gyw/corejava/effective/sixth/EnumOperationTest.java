package cn.gyw.corejava.effective.sixth;

import org.junit.Test;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EnumOperationTest {

    /**
     * EnumMap 对花园中的香草进行分类，分几年生的集合
     */
    @Test
    public void enumMapReplace() {
        // 香草的花园
        Herb[] garden = {
                new Herb("香草1", Herb.Type.ANNUAL),
                new Herb("香草2", Herb.Type.PERENNIAL),
                new Herb("香草3", Herb.Type.ANNUAL),
                new Herb("香草4", Herb.Type.PERENNIAL),
                new Herb("香草5", Herb.Type.BIENNIAL),
                new Herb("香草6", Herb.Type.PERENNIAL),
                new Herb("香草7", Herb.Type.PERENNIAL),
                new Herb("香草8", Herb.Type.BIENNIAL),
                new Herb("香草9", Herb.Type.BIENNIAL),
                new Herb("香草10", Herb.Type.ANNUAL)
        };

        Map<Herb.Type, Set<Herb>> herbsByType = new EnumMap<>(Herb.Type.class);
        for(Herb.Type herbType : Herb.Type.values()) {
            herbsByType.put(herbType, new HashSet<>());
        }

        for (Herb herb : garden) {
            herbsByType.get(herb.getType()).add(herb);
        }

        System.out.println(herbsByType);
    }

    @Test
    public void print() {
        System.out.println("==============");
        System.out.println(Operation.PLUS);

        System.out.println(Operation.PLUS.ordinal());
    }

    /**
     * EnumSet 代替位域
     */
    @Test
    public void enumSetReplaceBitwise() {
        Text text = new Text();
        text.applyStyles(Text.STYLE_BOLD | Text.STYLE_ITALIC);
    }

    @Test
    public void enumWithInterface() {
        test(RunState.class);
    }

    private <T extends Enum<T> & RunProcesse> void test(Class<T> op) {
        for (RunProcesse runProcesse : op.getEnumConstants()) {
            runProcesse.showStatus();
        }
    }
}
