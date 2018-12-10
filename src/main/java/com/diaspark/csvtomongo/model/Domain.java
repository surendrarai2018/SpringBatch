package com.diaspark.csvtomongo.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class Domain implements Serializable {

	private static final long serialVersionUID = 3524468419624051134L;
	
	private int sequence;
	private int id;
	private String orgId;
	private String name;

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
