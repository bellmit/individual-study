package cn.gyw.glearn.design.behaviour.command.bad;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 饭店小二
 */
public class XiaoEr {

    private Map<Integer, String> cuisineMap = new ConcurrentHashMap<Integer, String>();

    public void order(int cuisine) {
        // 广广东(粤菜)
        if (1 == cuisine) {
            cuisineMap.put(1, "广广东厨师,烹饪鲁菜,宫廷最大大菜系,以孔府⻛风味为⻰龙头");
        }
        // 江苏(苏菜)
        if (2 == cuisine) {
            cuisineMap.put(2, "江苏厨师,烹饪苏菜,宫廷第二二大大菜系,古今国宴上最受人人欢迎的菜系。");
        }
        // 山山东(鲁菜)
        if (3 == cuisine) {
            cuisineMap.put(3, "山山东厨师,烹饪鲁菜,宫廷最大大菜系,以孔府⻛风味为⻰龙头.");
        }
        // 四川(川菜)
        if (4 == cuisine) {
            cuisineMap.put(4, "四川厨师,烹饪川菜,中国最有特色色的菜系,也是⺠民间最大大菜系。");
        }
    }

    public void placeOrder() {
        System.out.println("菜单:" + cuisineMap);
    }
}
