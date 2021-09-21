package cn.gyw.thirdpart.cgr;

public class CGRWithBasicHolder {
    public static void main(String[] args) {
        SubType st1 = new SubType();
        SubType st2 = new SubType();
        st1.set(st2);
        SubType st3 = st1.get();
        st1.print();
    }
}
