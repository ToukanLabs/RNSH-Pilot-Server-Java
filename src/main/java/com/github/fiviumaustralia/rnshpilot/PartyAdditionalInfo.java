package com.github.fiviumaustralia.rnshpilot;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PartyAdditionalInfo {
	private String id;
	private int version;
	private String key;
	private  String value;
	
	@JsonCreator
	public PartyAdditionalInfo(
		@JsonProperty("id") String id, 
	    @JsonProperty("version") int version,
	    @JsonProperty("key") String key,
	    @JsonProperty("value") String value) {
	        this.id = id;
	        this.version = version;
	        this.key = key;
	        this.value = value;
	}

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
