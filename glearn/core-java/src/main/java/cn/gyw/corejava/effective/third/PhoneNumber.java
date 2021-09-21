package cn.gyw.corejava.effective.third;

/**
 * 标准的equals 方法
 */
public final class PhoneNumber {

    private final short areaCode;
    private final short prefix;
    private final short lineNumber;

    public PhoneNumber(int areaCode, int prefix, int lineNumber) {
        rangCheck(areaCode, 999, "area code");
        rangCheck(prefix, 999, "prefix");
        rangCheck(lineNumber, 999, "line number");
        this.areaCode = (short) areaCode;
        this.prefix = (short) prefix;
        this.lineNumber = (short) lineNumber;
    }

    private static void rangCheck(int arg, int max, String name) {
        if (arg < 0 || arg > max) {
            throw new IllegalArgumentException(name + ":" + arg);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof PhoneNumber)) {
            return false;
        }
        PhoneNumber phoneNumber = (PhoneNumber) obj;
        return phoneNumber.areaCode == this.areaCode && phoneNumber.prefix == this.prefix
                && phoneNumber.lineNumber == this.lineNumber;
    }

    @Override
    public int hashCode() {
//        int result = 17;
        int result = hashCode;
        if (result == 0) {
            // 等价于 (result << 5) - result , JVM 自动优化乘法为移位+减法
            result = 31 * result + areaCode;
            result = 31 * result + prefix;
            result = 31 * result + lineNumber;
        }
        return result;
    }

    // Lazy initialized ,cached hashCode
    private volatile int hashCode;
}
