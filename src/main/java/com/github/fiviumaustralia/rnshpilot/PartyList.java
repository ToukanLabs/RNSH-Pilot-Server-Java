package com.github.fiviumaustralia.rnshpilot;

import java.util.ArrayList;

public class PartyList {
	
	private Meta meta;
	
	private String action;
	
	private ArrayList<Party> parties;

	public ArrayList<Party> getParties() {
		return parties;
	}

	public void setParties(ArrayList<Party> parties) {
		this.parties = parties;
	}

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
}
