package cn.gyw.corejava.effective.fifth;

import java.util.ArrayList;
import java.util.List;

/**
 * 泛型
 */
public class ParameterType {

    public static void main(String[] args) {
        List<String> strings = new ArrayList<>();
        unsafeAdd(strings, new Integer(42));
        // Compiler-generated cast
        String s = strings.get(0);

        List<Object> objects = new ArrayList<>();
        objects.add(strings);

        System.out.println(objects.get(0));
    }

    // List<String> 是List 的子类，不是List<Object>的子类；
    private static void unsafeAdd(List list, Object o) { // runtime error
//    private static void unsafeAdd(List<Object> list, Object o) { // compile error，泛型的子类化规则
            list.add(o);
    }
}
