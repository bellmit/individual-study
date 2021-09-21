package cn.gyw.glearn.javaagent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

/**
 * main 方法执行之前调用
 * 
 * 使用 javaagent 需要几个步骤：
 * 
 * 1、定义一个 MANIFEST.MF 文件，必须包含 Premain-Class 选项，通常也会加入Can-Redefine-Classes 和
 * Can-Retransform-Classes 选项。 2/创建一个Premain-Class 指定的类，类中包含 premain
 * 方法，方法逻辑由用户自己确定。 将 premain 的类和 MANIFEST.MF 文件打成 jar 包。 使用参数 -javaagent: jar包路径
 * 启动要代理的方法。
 *
 */
public class PreMainTraceAgent {

	public static void premain(String agentArgs, Instrumentation inst) {
		System.out.println("agentArgs : " + agentArgs);
		inst.addTransformer(new DefineTransformer(), true);
	}

	static class DefineTransformer implements ClassFileTransformer {
		@Override
		public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
				ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
			System.out.println("premain load Class:" + className);
			return classfileBuffer;
		}
	}
}
