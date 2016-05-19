package com.github.fiviumaustralia.rnshpilot;

public class QueryResult {
	private Object data;

	QueryResult(Object data) {
		this.data = data;
	}
	
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
