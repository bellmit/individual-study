package cn.gyw.corejava.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import org.junit.Test;

import cn.gyw.corejava.AbstractTest;

/**
 * 普通测试类
 */
public class NomalTest extends AbstractTest {

	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HHmmss");
	
	/**
	 * 文件路径操作 Paths
	 */
	@Test
	public void pathsGetMethod() {
		System.out.println(Paths.get("D:\\", "1.txt").toFile().toString());
	}
	
	@Test
	public void findFileName() {
		String rootDir = "D:\\Temp\\Output";
		String strDate = "20210106";
		String conversationId = "202101061327320206f952eb0";
		String strTime = "052750";
		String result = "";

		if (strDate != null) {
			rootDir = rootDir + '/' + strDate;
		}
		try { // .filter(Files::isRegularFile)
			Stream<Path> s = Files.walk(Paths.get(rootDir)).filter((f) -> {
				System.out.println(">> " + f.toString());
				Path p = f.getParent();
				return f.toString().endsWith(".mp4")
						&& p.getName(p.getNameCount() - 1).toString().startsWith(conversationId);
			});

			if (strTime == null) {
				result = s.findFirst().map((p) -> p.toString()).orElse(null);
			} else {
				result = s.min((p1, p2) -> {
					
					LocalTime baseTime = LocalTime.parse(strTime, TIME_FORMATTER);
					int baseDaySeconds = baseTime.toSecondOfDay();
					int ret = compareDirName(baseDaySeconds, p1.getParent(), p2.getParent());
					if (ret != 0) {
						return ret;
					}
					return compareFileName(baseDaySeconds, p1.getFileName(), p2.getFileName());
				}).map((p) -> {
					return p.toString();
				}).orElse(null);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(">>> fileName :" + result);
	}
	
	/**
	 * 比较目录名中的时分秒
	 * 自动生成的子目录为 yyyymmdd/ChannelName_HHMMSS_MSUSNS/xxxx.mp4
	 * @param baseDaySeconds
	 * @param p1
	 * @param p2
	 * @return
	 */
	private int compareDirName(int baseDaySeconds, Path p1, Path p2) {
		int len1 = p1.toString().length();
		int len2 = p2.toString().length();
		if ( len1 < 16 || len2 < 16 ){
			return Integer.MAX_VALUE;
		}
		int diff1 = getDiff(baseDaySeconds, p1.toString().substring(len1 - 16, len1 - 10));
		int diff2 = getDiff(baseDaySeconds, p2.toString().substring(len2 - 16, len2 - 10));
		return diff1 - diff2;
	}

	/**
	 * 比较双录文件名
	 * 文件名为 // 0_yyyyMMddHHmmssSSS.mp4
	 * @param baseDaySeconds
	 * @param p1
	 * @param p2
	 * @return
	 */
	private int compareFileName(int baseDaySeconds, Path p1, Path p2) {
		int len1 = p1.toString().length();
		int len2 = p2.toString().length();
		if (len1 < 16 || len2 < 16) {
			return Integer.MAX_VALUE;
		}
		int diff1 = getDiff(baseDaySeconds, p1.toString().substring(10, 16));
		int diff2 = getDiff(baseDaySeconds, p2.toString().substring(10, 16));
		return diff1 - diff2;
	}

	private int getDiff(int baseSeconds, String timeStr) {
		LocalTime localTime = LocalTime.parse(timeStr, TIME_FORMATTER);
		return Math.abs(localTime.toSecondOfDay() - baseSeconds);
	}
	
	/**
	 * 打印章节信息 模版：第{seq}章
	 */
	@Test
	public void printChapterInfo() {
		for (int i = 5; i < 12; i++) {
			System.out.printf("第%d章\n", i);
		}
	}

}
