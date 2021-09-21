package cn.gyw.glearn.algorithm.interview;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringOperateTest {

    /**
     * 查询 字符串中最大的子串
     */
    @Test
    public void findMaxSubstr1() {
        String str1 = "abcdefe";
        String str2 = "abc";
        int common = getCommonStrLength(str1, str2);
        System.out.println(common);
    }

    @Test
    public void findMaxSubstr2() {
        String str1 = "abcdefe";
        String str2 = "aebcaedefe";
        String substr = getSameMaxStr(str1, str2);
        System.out.println(substr);
    }

    /**
     * 找出字符串中出现次数最多的那个字符
     */
    @Test
    public void findMaxTime() {
        String str = "aerqerxafqruoqeraarerqweaa";
        char[] chars = str.toCharArray();
        Map<Character, Integer> countMap = new HashMap<>();
        List<Character> maxChar = new ArrayList<>();

        int cnt = 0;
        int maxCnt = 0;
        for (int i = 0 ; i < chars.length ; i++) {
            cnt = 1;
            char c = chars[i];
            if (countMap.containsKey(c)) {
                cnt = countMap.get(c) + 1;
                countMap.put(c, cnt);
            } else {
                countMap.put(c, cnt);
            }
            if (cnt > maxCnt) {
                maxCnt = cnt;
                maxChar.clear();
                maxChar.add(c);
            } else if (cnt == maxCnt) {
                maxChar.add(c);
            } else {
                continue;
            }
        }

        maxChar.stream().forEach(System.out::println);
    }

    private int getCommonStrLength(String str1, String str2) {
        str1 = str1.toLowerCase();
        str2 = str2.toLowerCase();
        int len1 = str1.length();
        int len2 = str2.length();
        String min = null;
        String max = null;
        String target = null;
        min = len1 <= len2 ? str1 : str2;
        max = len1 >  len2 ? str1 : str2;
        //最外层：min子串的长度，从最大长度开始
        for (int i = min.length(); i >= 1; i--) {
            //遍历长度为i的min子串，从0开始
            for (int j = 0; j <= min.length() - i; j++) {
                target = min.substring(j, j + i);
                //遍历长度为i的max子串，判断是否与target子串相同，从0开始
                for (int k = 0; k <= max.length() - i; k++) {
                    if (max.substring(k,k + i).equals(target)) {
                        return i;
                    }
                }
            }
        }
        return 0;
    }

    private String getSameMaxStr(String str1, String str2) {
        String min = str1.length() <= str2.length() ? str1 : str2;
        String max = str1.equals(min) ? str2 : str1;
        String target = "";
        for (int i=min.length();i>0;i--) {
            for (int j=0;j <= min.length() -i;j++) {
                target = min.substring(j, j+i);

                for (int k=0;k<=max.length()-i;k++) {
                    if (target.equals(max.substring(k, k+i))) {
                        return target;
                    }
                }
            }
        }
        return null;
    }
}
