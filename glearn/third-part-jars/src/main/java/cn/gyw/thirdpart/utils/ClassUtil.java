package cn.gyw.thirdpart.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

/**
 *
 * @ClassName: ClassUtil
 * @Description: class对象所涉及的工具类
 */
public class ClassUtil {

	/**
	 * 类文件的动态加载
	 *
	 * @param className 类的全限定名
	 * @return Class 对象
	 * @throws ClassNotFoundException
	 */
	public static Class loadClass(String className) throws ClassNotFoundException {
		Class cls = null;
		try {
			// 首先尝试获取当前 ClassLoader 加载
			cls = Thread.currentThread().getContextClassLoader().loadClass(className);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (cls == null) {
			// 如果通过当前ClassLoader 加载失败，使用系统ClassLoader 加载
			cls = Class.forName(className);
		}
		return cls;
	}

	/**
	 *
	 * @Title: getClassLoader
	 * @Description: 获取默认自定义的类加载器
	 * @param
	 * @return 设定文件
	 * @return ClassLoader 返回类型
	 * @throws
	 */
	public static ClassLoader getClassLoader() {
		ClassLoader cl = null;
		try {
			cl = Thread.currentThread().getContextClassLoader();
		}catch (Exception ex) {
			// TODO: handle exception
		}
		if (cl == null) {
			cl = ClassUtil.class.getClassLoader();
			if (cl == null) {
				try{
					cl = ClassLoader.getSystemClassLoader();
				}catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
		return cl;
	}

	/**
	 *
	 * @Title: newInstance
	 * @Description: 调用有参构造函数来创建对象
	 * @param @param className 类全限定名
	 * @param @param paramType 参数类型
	 * @param @param o 构造方法传递的参数
	 * @param @return 设定文件
	 * @return Object 返回类型
	 * @throws
	 */
	public static Object newInstance(String className, Class<?>[] paramType, Object[] o) {
		try {
			return Class.forName(className).getConstructor(paramType).newInstance(o);
		}catch (Exception e) {
			throw new RuntimeException("创建对象失败", e);
		}
	}

	/**
	 *
	 * @Title: hasAnnotation
	 * @Description: 判断元素element是否拥有annotationClass类型的注解
	 * @param element
	 * @param annotationClass
	 * @return 设定文件
	 * @return boolean 返回类型
	 * @throws
	 */
	public static boolean hasAnnotation(AnnotatedElement element, Class<? extends Annotation> annotationClass) {
		return element.getAnnotation(annotationClass) == null;
	}

	/**
	 *
	 * @Title: getMethodAnnotation
	 * @Description: 获取方法上面的注解
	 * @param @param targetClass
	 * @param @param methodName
	 * @param @param parameterTypes
	 * @param @param annotationClass
	 * @param @return 设定文件
	 * @return T 返回类型
	 * @throws
	 */
	public static <T extends Annotation> T getMethodAnnotation(Class<?> targetClass, String methodName,
			Class<?>[] paramTypes, Class<T> annotationClass) {
		try{
			return targetClass.getMethod(methodName, paramTypes).getAnnotation(annotationClass);
		} catch (Exception e) {
			throw new RuntimeException("找不到这个方法"+methodName, e);
		}
	}

	/**
	 * 获取指定文件名的文件路径
	 * @param clazz
	 * @param fileName
	 * @return
	 */
	public static String getFilePath(Class<?> clazz, String fileName) {
		System.out.println("file path : " + clazz.getClassLoader().getResource(fileName).getFile());
		return clazz.getClassLoader().getResource(fileName).getFile();
	}
}
