package com.github.fiviumaustralia.rnshpilot;

public class EHR {
	
	private Meta meta;
	private String action;
	private EHRStatus ehrStatus;
	private String ehrId;
	
	public Meta getMeta() {
		return meta;
	}
	public void setMeta(Meta meta) {
		this.meta = meta;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public EHRStatus getEhrStatus() {
		return ehrStatus;
	}
	public void setEhrStatus(EHRStatus ehrStatus) {
		this.ehrStatus = ehrStatus;
	}
	public String getEhrId() {
		return ehrId;
	}
	public void setEhrId(String ehrId) {
		this.ehrId = ehrId;
	}
	
}
