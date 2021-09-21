package cn.gyw.handwritten.junit5;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("production")
public class ProdEnvironmentTest {

	/**
	 * RepetitionInfo用于注入约一个重复测试的当前重复成信息@RepeatedTest
	 * @param info
	 */
	@BeforeEach
	public void initEach(RepetitionInfo info){
		int currentRepetition = info.getCurrentRepetition();
        int totalRepetitions = info.getTotalRepetitions();
        //Use information as needed
        System.out.println(currentRepetition);
        System.out.println(totalRepetitions);
	}
	
	@Test
	public void test() {
		System.out.println("Production test...");
	}
	
	/**
	 * 指定重复次数之外，您还可以为每个重复次数指定一个自定义显示名称 静态文本+动态占位符的组合。
	 * 当前，支持3个占位符：
	 * {displayName}：显示@RepeatedTest方法的名称
	 * {currentRepetition}：当前的重复计数。
	 * {totalRepetitions}：重复总数
	 * 
	 * 预定义格式
	 * RepeatedTest.LONG_DISPLAY_NAME和RepeatedTest.SHORT_DISPLAY_NAME
	 */
	@DisplayName("Test repeat run ...")
	@RepeatedTest(value= 5, name= "{displayName} - repetition {currentRepetition} of {totalRepetitions}")
	public void repeatRun() {
		System.out.println("Repeat run ...");
	}
}
