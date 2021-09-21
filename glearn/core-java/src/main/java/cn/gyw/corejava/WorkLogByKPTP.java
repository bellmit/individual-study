package cn.gyw.corejava;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基于KPTP 的工作模板生成
 */
public final class WorkLogByKPTP {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(WorkLogByKPTP.class);
	private final static DateTimeFormatter DAY_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	/**
	 * 在指定目录下生成工作日志文件
	 * 
	 * @return
	 * @throws URISyntaxException 
	 */
	public static boolean buildCurrentYearWorkLog(String targetDir) throws URISyntaxException {
		Path target = Paths.get(targetDir);
		if (!Files.exists(target)) {
			try {
				Files.createDirectories(target);
			} catch (IOException e) {
				LOGGER.error("target dir create faild");
				return false;
			}
		}
		
		URI templateUri = Thread.currentThread().getContextClassLoader().getResource("KPTP.txt").toURI();
		Path template = Paths.get(templateUri);
		
		LocalDate tmp = LocalDate.now();
		LocalDate endDay = LocalDate.of(tmp.getYear(), Month.DECEMBER, Month.DECEMBER.length(tmp.isLeapYear()));
		String filename = "";
		while (tmp.isBefore(endDay)) {
			tmp = tmp.plusDays(1);
			if (isWeekDay(tmp.getDayOfWeek())) {
				continue;
			}
			filename = DAY_FORMATTER.format(tmp) + ".txt";
			try {
				Files.copy(template, Paths.get(target + "/" + filename));
			} catch (IOException e) {
				LOGGER.error("error:{}", e);
			}
		}
		return true;
	}
	
	private static boolean isWeekDay(DayOfWeek dayOfWeek) {
		if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
			return true;
		}
		return false;
	}
	
	private WorkLogByKPTP() {}
}
