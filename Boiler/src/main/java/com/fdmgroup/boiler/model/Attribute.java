package com.fdmgroup.boiler.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * This is an attribute entity which stores the name of the attribute
 * @author Zane Havemann
 */
@Entity
public class Attribute {
	
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String dataType;
	
	public Attribute() {
		super();
	}

	public Attribute(String name, String dataType) {
		super();
		this.name = name;
		this.dataType = dataType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	@Override
	public String toString() {
		return "Attribute [id=" + id + ", name=" + name + "]";
	}

}
