package cn.gyw.thirdpart.utils;

import java.util.Arrays;

public class StubDataUtil {

    //1.Java标准类库Arrays有作用十分有限的fill()方法：
    //只能用同一个值填充各个位置，而针对对象而言，就是复制同一个引用进行填充
    /**
     * 仅支持数组类型填充测试数据
     * @param seq 数组
     * @param size 数组长度
     */
    public static <T> void fillingArrays(T[] seq,T val){
        Arrays.fill(seq, val);
    }

    public static <T> void fillingArraysByIndex(T[] seq,int fromIndex,int toIndex,T val){
        Arrays.fill(seq, fromIndex, toIndex, val);
    }
}
