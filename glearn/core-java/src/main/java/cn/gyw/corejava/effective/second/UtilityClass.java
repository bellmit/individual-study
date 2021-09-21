package cn.gyw.corejava.effective.second;

/**
 * 声明不可实例化能力
 */
public class UtilityClass {

    private UtilityClass() {
        throw new AssertionError();
    }
}
