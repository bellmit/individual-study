package cn.gyw.corejava.effective.third;

// broken - violates symmetry:违反对称性
public class CaseInsensitiveString {

    public final String s;

    public CaseInsensitiveString(String s) {
        if (s == null) {
            throw new NullPointerException();
        }
        this.s = s;
    }

    /**
     * broken - violates symmetry:违反对称性
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CaseInsensitiveString) {
            return s.equalsIgnoreCase(
                    ((CaseInsensitiveString)obj).s);
        }
        if (obj instanceof String) {
            return s.equalsIgnoreCase((String)obj);
        }
        return false;
    }

    /**
     * 对称性
     */
//    @Overwrite
//    public boolean equals(Object obj) {
//        return obj instanceof CaseInsensitiveString
//                && ((CaseInsensitiveString)obj).s.equalsIgnoreCase(s);
//    }
}
