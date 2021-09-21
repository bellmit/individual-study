package cn.gyw.springboot.redis.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * 未实现序列化的类
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public interface IPerson {

	String getName();
	
	int getAge();
}
