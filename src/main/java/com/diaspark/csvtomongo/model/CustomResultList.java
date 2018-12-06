package com.diaspark.csvtomongo.model;

import java.io.Serializable;
import java.util.List;

public class CustomResultList implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1693046355859466074L;


	private List<Domain> results;
	
	private String nextLine;
	
	private long count;

	public List<Domain> getResults() {
		return results;
	}

	public void setResults(List<Domain> results) {
		this.results = results;
	}

	public String getNextLine() {
		return nextLine;
	}

	public void setNextLine(String nextLine) {
		this.nextLine = nextLine;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	
}
