package com.github.fiviumaustralia.rnshpilot;

public class PartyAdditionalInfo {
	private String id;
	private int version;
	private String key;
	private  String value;
	
	@Override
	public String toString() {
	    String s = "party info: ";
	    s = s.concat(id).concat(" key: ").concat(key).concat(" value: ").concat(value);
	    return s;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
