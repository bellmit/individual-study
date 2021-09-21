package cn.gyw.corejava.jvm.servertmpcode;

/**
 * 修改class文件，暂时只提供修改常量池常量的功能
 */
public class ClassModifier {

	private static final int CONSTANT_POOL_COUNT_INDEX = 8;

	private static final int CONSTANT_Utf8_info = 1;

	/**
	 * 常量池中11种常量所占的长度，CONSTANT_Utf8_info 型常量除外，因为它不是定长的
	 */
	private static final int[] CONSTANT_ITEM_LENGTH = { -1, -1, -1, 5, 5, 9, 9, 3, 3, 5, 5, 5, 5 };

	private static final int u1 = 1;
	private static final int u2 = 2;

	private byte[] classByte;

	public ClassModifier(byte[] classByte) {
		this.classByte = classByte;
	}

	/**
	 * 修改常量池中 CONSTANT_Utf8_info 常量的内容
	 */
	public byte[] modifyUTF8Constant(String oldStr, String newStr) {
		int cpc = getConstantPoolCount();
		int offset = CONSTANT_POOL_COUNT_INDEX + u2;
		for (int i = 0; i < cpc; i++) {
			int tag = ByteUtils.bytes2Int(classByte, offset, u1);
			if (tag == CONSTANT_Utf8_info) {
				int len = ByteUtils.bytes2Int(classByte, offset + u1, u2);
				offset += (u1 + u2);
				String str = ByteUtils.bytes2String(classByte, offset, len);
				if (str.equals(oldStr)) {
					byte[] strBytes = ByteUtils.string2Bytes(newStr);
					byte[] strLen = ByteUtils.int2Bytes(newStr.length(), u2);
					classByte = ByteUtils.bytesReplace(classByte, offset - u2, u2, strLen);
					classByte = ByteUtils.bytesReplace(classByte, offset, len, strBytes);
					return classByte;
				} else {
					offset += len;
				}
			} else {
				offset += CONSTANT_ITEM_LENGTH[tag];
			}
		}
		return classByte;
	}

	/**
	 * 获取常量池中常量的数量
	 * 
	 * @return 常量数量
	 */
	public int getConstantPoolCount() {
		return ByteUtils.bytes2Int(classByte, CONSTANT_POOL_COUNT_INDEX, u2);
	}

	private static class ByteUtils {

		public static int bytes2Int(byte[] bytes, int start, int len) {
			int sum = 0;
			int end = start + len;
			for (int i = start; i < end; i++) {
				int n = ((int) bytes[i]) & 0xff;
				n <<= (--len) * 8;
				sum = n + sum;
			}
			return sum;
		}

		public static byte[] int2Bytes(int value, int len) {
			byte[] bytes = new byte[len];
			for (int i = 0; i < len; i++) {
				bytes[len - i - 1] = (byte) ((value >> 8 * i) & 0xff);
			}
			return bytes;
		}

		public static String bytes2String(byte[] bytes, int start, int len) {
			return new String(bytes, start, len);
		}

		public static byte[] string2Bytes(String str) {
			return str.getBytes();
		}

		public static byte[] bytesReplace(byte[] originalBytes, int offset, int len, byte[] replaceBytes) {
			byte[] newBytes = new byte[originalBytes.length + (replaceBytes.length - len)];
			System.arraycopy(originalBytes, 0, newBytes, 0, offset);
			System.arraycopy(replaceBytes, 0, newBytes, offset, replaceBytes.length);
			System.arraycopy(originalBytes, offset + len, newBytes, offset + replaceBytes.length,
					originalBytes.length - offset - len);
			return newBytes;
		}
	}
}
