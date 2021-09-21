package cn.gyw.corejava.javaagent;

import java.util.List;

//import com.sun.tools.attach.VirtualMachine;
//import com.sun.tools.attach.VirtualMachineDescriptor;

/**
 * main 运行后，加载agent 到指定jvm 上
 * 
 * 说明：依赖 JAVA_HOME/lib/dt.jar or tools.jar classpath
 */
public class AgentMainTest {

//	public static void main(String[] args) throws Exception {
//		// 获取当前系统中所有 运行中的 虚拟机
//		System.out.println("running JVM start ");
//		List<VirtualMachineDescriptor> list = VirtualMachine.list();
//		for (VirtualMachineDescriptor vmd : list) {
//			// 如果虚拟机的名称为 xxx 则 该虚拟机为目标虚拟机，获取该虚拟机的 pid
//			// 然后加载 agent.jar 发送给该虚拟机
//			System.out.println("vm name : " + vmd.displayName());
//			if (vmd.displayName().endsWith("cn.gyw.corejava.javaagent.AgentMainTest")) {
//				// attach到一个运行中的java进程上
//				VirtualMachine virtualMachine = VirtualMachine.attach(vmd.id());
//				// 将agent的jar包注入到对应的进程，对应的进程会调用agentmain方法
//				virtualMachine.loadAgent("D:/Temp/javaagent-1.0.0.jar");
//				virtualMachine.detach();
//			}
//		}
//	}
}
