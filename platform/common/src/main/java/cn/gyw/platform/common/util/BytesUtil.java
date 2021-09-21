package cn.gyw.platform.common.util;

import java.io.ByteArrayOutputStream;

/**
 * 字节工具类
 */
public final class BytesUtil {

    public static byte[] integerToBytes(int integer, int len) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (int i = 0; i < len; i++) {
            baos.write(integer);
            integer >>= 8;
        }
        return baos.toByteArray();
    }

    public static int bytesToInteger(byte[] bytes) {
        int integer = 0;
        int len = bytes.length;
        for (int i = 0; i < len; i++) { // 一个int型占4个字节 ，一个字节8位
            int temp = 0;
            temp = temp | bytes[i];
            temp = temp << 8 * i;
            integer = integer | temp;
        }
        return integer;
    }

    public static byte[] intToBytes(int i) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (i >>> 24 & 0xff);
        bytes[1] = (byte) (i >>> 16 & 0xff);
        bytes[2] = (byte) (i >>> 8 & 0xff);
        bytes[3] = (byte) (i & 0xff);

        return bytes;
    }

    public static int bytesToInt(byte[] bytes) {
        int byte4 = bytes[3];
        int byte3 = bytes[2];
        int byte2 = bytes[1];
        int byte1 = bytes[0];

        return byte1 | byte2 | byte3 | byte4;
    }

    private BytesUtil() {}
}
