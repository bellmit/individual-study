package cn.gyw.corejava.enumrate;

import java.util.EnumSet;

/**
 * EnumSet 使用
 * 
 * JDK1.5 引入 EnumSet
 */
public class EnumSetDemo {
	public static void main(String[] args) {
		EnumSet<AlarmPoints> points =
				EnumSet.noneOf(AlarmPoints.class);
		points.add(AlarmPoints.BATHROOM);
		System.out.println(points);
		points.addAll(EnumSet.of(AlarmPoints.STAIR1,AlarmPoints.STAIR2,AlarmPoints.KITCHEN));
		System.out.println(points);
		points = EnumSet.allOf(AlarmPoints.class);
		points.removeAll(EnumSet.of(AlarmPoints.STAIR1,AlarmPoints.STAIR2,AlarmPoints.KITCHEN));
		System.out.println(points);
	}
}
