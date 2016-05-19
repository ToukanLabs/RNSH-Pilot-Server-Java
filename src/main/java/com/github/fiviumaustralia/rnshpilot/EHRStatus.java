package com.github.fiviumaustralia.rnshpilot;

public class EHRStatus {
	
	private String subjectId;
	private String subjectNamespace;
	private boolean queryable;
	private boolean modifiable;
	
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	public String getSubjectNamespace() {
		return subjectNamespace;
	}
	public void setSubjectNamespace(String subjectNamespace) {
		this.subjectNamespace = subjectNamespace;
	}
	public boolean isQueryable() {
		return queryable;
	}
	public void setQueryable(boolean queryable) {
		this.queryable = queryable;
	}
	public boolean isModifiable() {
		return modifiable;
	}
	public void setModifiable(boolean modifiable) {
		this.modifiable = modifiable;
	}
	
}
