package cn.gyw.thirdpart;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

public class CodecTest {

	@Test
	public void test1() {
		System.out.println(DigestUtils.md5Hex("123123"));
	}
}
