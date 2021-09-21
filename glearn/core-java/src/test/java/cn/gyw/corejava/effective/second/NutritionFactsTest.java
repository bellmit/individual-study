package cn.gyw.corejava.effective.second;

import org.junit.Test;

/**
 * 遇到多个构造器参数时要考虑用构建器
 */
public class NutritionFactsTest {

    /**
     * Telescoping Constructor
     * 构建对象
     */
    @Test
    public void createNutritionFactsObject() {
        NutritionFacts nutritionFacts =
                new NutritionFacts(240, 8, 100, 0, 35, 27);
    }
}
