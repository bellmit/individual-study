package cn.gyw.corejava.enumrate;

import java.util.EnumMap;
import java.util.Map;

/**
 * Enum 容器
 * 1.java.support.EnumMap
 */
public class EnumMapDemo {
	public static void main(String[] args) {
		EnumMap<AlarmPoints,Command> em =
				new EnumMap<>(AlarmPoints.class);
		em.put(AlarmPoints.KITCHEN, new Command() {

			@Override
			public void action() {
				System.out.println("kitchen fire !");
			}
		});
		em.put(AlarmPoints.BATHROOM, new Command() {

			@Override
			public void action() {
				System.out.println("bathroom fire !");
			}
		});

		for(Map.Entry<AlarmPoints,Command> e : em.entrySet()){
			System.out.println(e.getKey() + " : ");
			e.getValue().action();
		}
		try{
			em.get(AlarmPoints.UTILITY).action();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

interface Command {
	void action();
}
