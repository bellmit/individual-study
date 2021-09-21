package cn.gyw.spring.plugins;

public class PluginConfig {

	private String id;
	private String name;
	private boolean active;
	private String className;
	private String jarRemoteUrl;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getJarRemoteUrl() {
		return jarRemoteUrl;
	}

	public void setJarRemoteUrl(String jarRemoteUrl) {
		this.jarRemoteUrl = jarRemoteUrl;
	}

	@Override
	public String toString() {
		return "PluginConfig [id=" + id + ", name=" + name + ", active=" + active + ", className=" + className
				+ ", jarRemoteUrl=" + jarRemoteUrl + "]";
	}
}
