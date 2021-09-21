package cn.gyw.corejava.jmx;

import java.lang.management.ManagementFactory;
import java.util.Hashtable;
import java.util.Map;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

/**
 * Typical uses of the JMX technology include:
	1）Consulting and changing application configuration
	2）Accumulating and making available statistics about application behavior
	3）Notifying of state changes and erroneous conditions
 */
public class JmxMain {
	
	public static void main(String[] args) {
		// 注册MBean 的对象，由domain 和 map组成
		ObjectName objectName = null;
		
		String domain = "cn.gyw.corejava.jmx";
		
		Hashtable<String, String> props = new Hashtable<>();
		props.put("type", "base");
		props.put("name", "game");
		
		try {
			objectName = new ObjectName(domain, props);
			
		} catch (MalformedObjectNameException e) {
			e.printStackTrace();
		}
		
		MBeanServer mServer = ManagementFactory.getPlatformMBeanServer();
		Game gameObj = new Game();
		
		try {
			mServer.registerMBean(gameObj, objectName);
		} catch (InstanceAlreadyExistsException e) {
			e.printStackTrace();
		} catch (MBeanRegistrationException e) {
			e.printStackTrace();
		} catch (NotCompliantMBeanException e) {
			e.printStackTrace();
		}
		
		System.out.println("Registration for Game mbean with the platform server is successfull");
		System.out.println("Please open jconsole to access Game mbean");
		
		while (true) {}
	}

}
