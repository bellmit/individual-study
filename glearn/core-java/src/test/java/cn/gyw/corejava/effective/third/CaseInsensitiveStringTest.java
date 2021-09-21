package cn.gyw.corejava.effective.third;

import org.junit.Test;

public class CaseInsensitiveStringTest {

    /**
     * 违反自反性
     */
    @Test
    public void shouldEquals() {
        CaseInsensitiveString caseInsensitiveString = new CaseInsensitiveString("Polish");
        String s = "polish";

        System.out.println(caseInsensitiveString.equals(s));
        System.out.println(s.equals(caseInsensitiveString));
    }
}
