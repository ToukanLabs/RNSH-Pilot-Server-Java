package com.github.fiviumaustralia.rnshpilot;

import java.util.ArrayList;

public class Party {
	private String id;
	private int version;
	private String firstNames;
	private String lastNames;
	private String gender;
	private String dateOfBirth;
    private Address address;
	private ArrayList<PartyAdditionalInfo> partyAdditionalInfo;
	
	@Override
	public String toString() {
	    String s = "";
	    s = s.concat(id).concat(" ").concat(firstNames).concat(" ").concat(lastNames).concat(" ").concat(address.getAddress());
	    return s;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstNames() {
		return firstNames;
	}

	public void setFirstNames(String firstNames) {
		this.firstNames = firstNames;
	}

	public String getLastNames() {
		return lastNames;
	}

	public void setLastNames(String lastNames) {
		this.lastNames = lastNames;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public ArrayList<PartyAdditionalInfo> getPartyAdditionalInfo() {
		return this.partyAdditionalInfo;
	}

	public void setPartyAdditionalInfo(
			ArrayList<PartyAdditionalInfo> partyAdditionalInfo) {
		this.partyAdditionalInfo = partyAdditionalInfo;
	}
}
