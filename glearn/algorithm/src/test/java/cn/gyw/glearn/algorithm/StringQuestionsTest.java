package cn.gyw.glearn.algorithm;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cn.gyw.glearn.algorithm.StringQuestions;
import cn.gyw.glearn.algorithm.util.DataGenerator;

/**
 * 字符串相关问题测试
 */
public class StringQuestionsTest {

	private StringQuestions stringQuestions;
	
	/**
	 * 字符最大频率问题
	 */
	@Test
	public void mostChar() {
		String target = DataGenerator.generateString(32);
		char c = stringQuestions.findMostChar(target);
		System.out.println("<<" + String.valueOf(c));
	}
	
	/**
	 * 最大不重复子串
	 */
	@Test
	public void findLongestUniqueSubString() {
		String target = "abcabcbb";
		Assert.assertEquals("abc", stringQuestions.findLongestUniqueSubString(target));
		target = "bbbb";
		Assert.assertEquals("b", stringQuestions.findLongestUniqueSubString(target));
		target = "btwwt";
		Assert.assertEquals("btw", stringQuestions.findLongestUniqueSubString(target));
	}
	
	@Before
	public void init() {
		stringQuestions = new StringQuestions();
	}
}
