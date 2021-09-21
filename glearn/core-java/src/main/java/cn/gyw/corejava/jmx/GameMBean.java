package cn.gyw.corejava.jmx;

/**
 * JMX java management Extensions
 * 特点：
 * 1)易于配置，可伸缩，可靠且友好
 * 2)实时管理应用程序的mbean 概念
 */
public interface GameMBean { // 接口，以MBean 结尾

	void playFootball(String clubName);
	
	String getPlayerName();
	
	void setPlayerName(String playerName);
}
