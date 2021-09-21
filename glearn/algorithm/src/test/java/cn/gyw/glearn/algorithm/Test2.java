package cn.gyw.glearn.algorithm;

import cn.gyw.glearn.algorithm.util.DataGenerator;

import java.util.*;
import java.util.stream.Collectors;

public class Test2 {

    public static void main(String[] args) {
        Test2 app = new Test2();
        int[] arr = DataGenerator.generateRandomArray(1000, 1, 100);
        List<Integer> data = Arrays.stream(arr).boxed().collect(Collectors.toList());
        List<Integer> subList = app.handleData(data);
        Integer min = subList.get(0);
        Integer max = subList.get(subList.size() - 1);
        int rangeSize = (max - min) / 7;

        Map<Integer, Integer> rangeDataMap = new HashMap<>();

        subList.stream().mapToInt((obj) -> obj - min)
                .forEach((d) -> {
                    int idx = d / rangeSize;
                    if (d % rangeSize == 0) {
                        rangeDataMap.putIfAbsent(idx, 0);
                        rangeDataMap.put(idx, rangeDataMap.get(idx) + 1);
                    } else {
                        rangeDataMap.putIfAbsent(idx + 1, 0);
                        rangeDataMap.put(idx + 1, rangeDataMap.get(idx + 1) + 1);
                    }
                });

        System.out.println(rangeDataMap);
    }

    public List<Integer> handleData(List<Integer> data) {
        data.sort((o1, o2) -> {
            if (o1 > o2) {
                return 1;
            }
            if (o1 < o2) {
                return -1;
            }
            return 0;
        });
        int endIndex = data.size() / 10 * 9;
        return data.subList(0, endIndex);
    }

}
