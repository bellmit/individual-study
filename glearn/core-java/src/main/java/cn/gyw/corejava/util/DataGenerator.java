package cn.gyw.corejava.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataGenerator {

    /**
     * [
     *      {"year":"2017", "price":1000},
     *      {"year":"2018", "price":1000},
     *      ....
     * ]
     * @return
     */
    public static List<Map<String, Object>> ofYearPriceMapList() {
        List<Map<String, Object>> mapArrayList = new ArrayList<>(10);
        Map<String, Object> yearPriceData = new HashMap<>();
        yearPriceData.put("year", "2019");
        yearPriceData.put("price", 1009);
        mapArrayList.add(yearPriceData);

        yearPriceData = new HashMap<>();
        yearPriceData.put("year", "2018");
        yearPriceData.put("price", 1008);
        mapArrayList.add(yearPriceData);

        yearPriceData = new HashMap<>();
        yearPriceData.put("year", "2017");
        yearPriceData.put("price", 1003);
        mapArrayList.add(yearPriceData);

        yearPriceData = new HashMap<>();
        yearPriceData.put("year", "2017");
        yearPriceData.put("price", 1006);
        mapArrayList.add(yearPriceData);

        return mapArrayList;
    }

    private DataGenerator() {}
}
