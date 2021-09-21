package cn.gyw.corejava.effective.seventh;

import java.math.BigInteger;

public class MethodUsage {

    /**
     * {@code 1=1}
     * @param m the modules, which must be positive
     * @return
     * @throws ArithmeticException if m is less than or equals to 0
     */
    public BigInteger mod(BigInteger m) {
        if (m.signum() < 0) {
            throw new ArithmeticException("Modules <= 0 :" + m);
        }
        return null;
    }

    private void checkParam(int[] arr) {
        assert arr.length > 0;
        assert arr == null;
    }

    public static void main(String[] args) {
        new MethodUsage().checkParam(null);
    }
}
