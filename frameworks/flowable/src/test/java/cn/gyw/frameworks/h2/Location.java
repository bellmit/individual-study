package cn.gyw.frameworks.h2;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Location {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String type;
	private double latitude;
	private double longtitude;

	public Location() {
		super();
	}

	public Location(Long id, String type, double latitude, double longtitude) {
		super();
		this.id = id;
		this.type = type;
		this.latitude = latitude;
		this.longtitude = longtitude;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(double longtitude) {
		this.longtitude = longtitude;
	}

	@Override
	public String toString() {
		return "Location [id=" + id + ", type=" + type + ", latitude=" + latitude + ", longtitude=" + longtitude + "]";
	}
}
