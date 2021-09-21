package cn.gyw.spring.plugins;

import java.util.List;

public class Plugins {

	private String name;
	private List<PluginConfig> config;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PluginConfig> getConfig() {
		return config;
	}

	public void setConfig(List<PluginConfig> config) {
		this.config = config;
	}

	@Override
	public String toString() {
		return "Plugins [name=" + name + ", config=" + config + "]";
	}

}
