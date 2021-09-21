package cn.gyw.spring.mybatis.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Table;

@Table(name = "t_phone")
public class Phone implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private Double price;
	// big string clob
	private String desc;
	// 二进制字节数组 blob
	private byte[] image;
	private LocalDate producedDate;
	private LocalDateTime createdTime;
	private LocalDateTime modifiedTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public LocalDate getProducedDate() {
		return producedDate;
	}

	public void setProducedDate(LocalDate producedDate) {
		this.producedDate = producedDate;
	}

	public LocalDateTime getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}

	public LocalDateTime getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(LocalDateTime modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	@Override
	public String toString() {
		return "Phone [id=" + id + ", name=" + name + ", price=" + price + ", desc=" + desc + ", image=" + image
				+ ", producedDate=" + producedDate + ", createdTime=" + createdTime + ", modifiedTime=" + modifiedTime
				+ "]";
	}

}
