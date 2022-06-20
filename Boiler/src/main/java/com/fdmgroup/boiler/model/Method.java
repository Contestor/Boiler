package com.fdmgroup.boiler.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * This is a method entity which stores the name, description, code, whether it has been shared or not, and attributes owned by the method
 * @author Zane Havemann
 */
@Entity
public class Method {

	@Id
	@GeneratedValue
	private Long id;
	private String name;
	@Lob
	private Clob description;
	@Lob
	private Clob code;
	private boolean shared;
	@OneToMany(cascade = CascadeType.ALL)
	private List<Attribute> attributes = new ArrayList<Attribute>();
	@ManyToOne
	@JsonBackReference
	private User user;
	
	public Method() {
		super();
	}

	public Method(String name, String description, String code, boolean shared, List<Attribute> attributes, User user) {
		super();
		this.name = name;
		/** This converts the description from String to Clob and sets it */
		try {
			this.description = new javax.sql.rowset.serial.SerialClob(description.toCharArray());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		/** This converts the code from String to Clob and sets it */
		try {
			this.code = new javax.sql.rowset.serial.SerialClob(code.toCharArray());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.shared = shared;
		this.attributes = attributes;
		this.user = user;
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

	/**
	 * This getter returns the converted description from Clob to String
	 * @return description - This is a string which contains the description for the method
	 */
	public String getDescription() {
		Clob clobObject = (Clob)description;                                   
        final StringBuilder sb = new StringBuilder();
        String getDescription="";
        try {
        	final Reader reader = clobObject.getCharacterStream();
        	final BufferedReader br = new BufferedReader(reader);
        	int b;
        	while(-1 != (b = br.read())) {
        		sb.append((char)b);
        	}
        	br.close();
        	getDescription = sb.toString();
        }
        catch (SQLException | IOException e) {
        	e.printStackTrace();
        }
		return getDescription;
	}

	/**
	 * This setter converts the description from String to Clob and sets it
	 * @param description - This is a string which contains the description for the method
	 */
	public void setDescription(String description) {
		try {
			this.description = new javax.sql.rowset.serial.SerialClob(description.toCharArray());
		} catch (SQLException e) {
			e.printStackTrace();
		};
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * This getter returns the converted code from Clob to String
	 * @return code - This is a string which contains the code for the method
	 */
	public String getCode() {
		Clob clobObject = (Clob)code;                                   
        final StringBuilder sb = new StringBuilder();
        String getCode="";
		try {
			Reader reader;
			reader = clobObject.getCharacterStream();
        	final BufferedReader br = new BufferedReader(reader);
        	int b;
        	while(-1 != (b = br.read())) {
        		sb.append((char)b);
        	}
        	br.close();
        	getCode = sb.toString();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		return getCode;
	}

	/**
	 * This setter converts the code from String to Clob and sets it
	 * @param code - This is a string which contains the code for the method
	 */
	public void setCode(String code) {
		try {
			this.code = new javax.sql.rowset.serial.SerialClob(code.toCharArray());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean getShared() {
		return shared;
	}

	public void setShared(boolean shared) {
		this.shared = shared;
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Method [id=" + id + ", name=" + name + ", description=" + description + ", code=" + code + ", shared="
				+ shared + ", attributes=" + attributes + ", user=" + user + "]";
	}
		
}
