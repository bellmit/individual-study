package cn.gyw.corejava.concurrent.design.master;


public class Task {

	private int id;
	private String taskName;
	private String description;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "task [id=" + id + ", taskName=" + taskName + ", description.md=" + description + "]";
	}
}
