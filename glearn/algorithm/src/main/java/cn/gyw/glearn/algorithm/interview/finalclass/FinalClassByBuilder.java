package cn.gyw.glearn.algorithm.interview.finalclass;

import java.util.HashMap;

/**
 * Created by guanyw on 2018/7/11.
 */
public class FinalClassByBuilder {

	//required fields
	private int id;
	private String name;

	//optional fields
	private HashMap properties;
	private String company;

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public HashMap getProperties() {
		//return cloned object to avoid changing it by the client application
		return (HashMap) properties.clone();
	}

	public String getCompany() {
		return company;
	}

	/**
	 * 不可变类只有一个私有构造器
	 *
	 * @param builder Builder为对象
	 */
	private FinalClassByBuilder(ImmutableClassBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.properties = builder.properties;
		this.company = builder.company;
	}

	//Builder class
	public static class ImmutableClassBuilder {
		//required fields
		private int id;
		private String name;

		//optional fields
		private HashMap properties;
		private String company;

		public ImmutableClassBuilder(int i, String nm) {
			this.id = i;
			this.name = nm;
		}

		/**
		 * 成员变量是对象时，使用深拷贝或者克隆来放置变量被更改
		 *
		 * @param hm
		 * @return
		 */
		public ImmutableClassBuilder setProperties(HashMap hm) {
			this.properties = (HashMap) hm.clone();
			return this;
		}

		public ImmutableClassBuilder setCompany(String comp) {
			this.company = comp;
			return this;
		}

		public FinalClassByBuilder build() {
			return new FinalClassByBuilder(this);
		}
	}

}
