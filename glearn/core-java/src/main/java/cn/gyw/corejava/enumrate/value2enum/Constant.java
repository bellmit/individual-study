package cn.gyw.corejava.enumrate.value2enum;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constant {
	/**
	 * 枚举类包名集合
	 */
	public static List<String> pathList = initPackagePathList();
	/**
	 * 枚举接口类全路径
	 */
	public final static String ENUM_MESSAGE_PATH = "cn.gyw.enumrate.value2enum.EnumMessage";

	/**
	 * 枚举类对应的全路径集合
	 */
	public static final List<String> ENUM_OBJECT_PATH = PackageUtil.getPackageClasses(pathList, true);

	/**
	 * 存放单个枚举对象 map常量定义
	 */
	private static Map<Object, EnumMessage> SINGLE_ENUM_MAP = null;

	/**
	 * 所有枚举对象的 map
	 */
	public static final Map<Class, Map<Object, EnumMessage>> ENUM_MAP = initialEnumMap(true);

	private static List<String> initPackagePathList() {
		List<String> list = new ArrayList<>();
		list.add("cn.gyw.enumrate.value2enum");
		return list;
	}

	static {
		System.out.println(
				"类被加载时，先初始化各个静态变量，再执行static块。" + "所以不能在这里执行pathList的add操作(\"com.example.myFirstProject.enums\")。");
	}

	/**
	 * 加载所有枚举对象数据
	 *
	 * @param isForceCheck
	 *            是否强制校验枚举是否实现了EnumMessage接口,若为false则没有实现接口的枚举类也会被加载
	 */
	private static Map<Class, Map<Object, EnumMessage>> initialEnumMap(boolean isForceCheck) {
		Map<Class, Map<Object, EnumMessage>> ENUM_MAP = new HashMap<>();
		try {
			for (String className : ENUM_OBJECT_PATH) {
				Class<?> cls = null;
				cls = Class.forName(className);
				Class<?>[] iter = cls.getInterfaces();
				boolean flag = false;
				if (isForceCheck) {
					for (Class cz : iter) {
						if (cz.getName().equals(ENUM_MESSAGE_PATH)) {
							flag = true;
							break;
						}
					}
				}
				if (flag == isForceCheck) {
					SINGLE_ENUM_MAP = new HashMap<>();
					initialSingleEnumMap(cls);
					ENUM_MAP.put(cls, SINGLE_ENUM_MAP);
				}

			}
		} catch (Exception e) {

		}
		return ENUM_MAP;
	}

	/**
	 * 加载每个枚举对象数据
	 */
	private static void initialSingleEnumMap(Class<?> cls) throws Exception {
		Method method = cls.getMethod("values");
		EnumMessage inter[] = (EnumMessage[]) method.invoke(cls.newInstance());
		for (EnumMessage enumMessage : inter) {
			SINGLE_ENUM_MAP.put(enumMessage.getValue(), enumMessage);
		}
	}
}
