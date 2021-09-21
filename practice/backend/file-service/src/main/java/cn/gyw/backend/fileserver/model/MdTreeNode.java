package cn.gyw.backend.fileserver.model;

import java.util.ArrayList;
import java.util.List;

public class MdTreeNode {

	private String label;
	
	private String fileName;

	private List<MdTreeNode> children = new ArrayList<>();

	public MdTreeNode() {
		super();
	}

	public MdTreeNode(String fileName) {
		super();
		this.label = fileName.replace(".md", "");
		this.fileName = fileName;
	}
	
	public void addNode(MdTreeNode node) {
		children.add(node);
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public List<MdTreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<MdTreeNode> children) {
		this.children = children;
	}
}
