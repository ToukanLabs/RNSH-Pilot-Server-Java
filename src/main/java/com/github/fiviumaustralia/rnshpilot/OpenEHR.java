package com.github.fiviumaustralia.rnshpilot;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class OpenEHR implements PatientService {
	
	private static String baseUrl;
	private static String subjectNamespace;
	private static String username;
	private static String password;
	
	OpenEHR(String url, String namespace, String user, String pw){
		baseUrl = url;
		subjectNamespace = namespace;
		username = user;
		password = pw;
	}
	
	private static String getAuthorizationHeader() {
		String auth = username.concat(":").concat(password);
		byte[] encodedBytes = Base64.getEncoder().encode(auth.getBytes());
		auth = "Basic ".concat(new String(encodedBytes));
		
		return auth;
	}
	
	private static Response OpenEHRRequest(String targetURL, String body, String reqType) {
	  HttpURLConnection connection = null;  
	  try {
	    //Create connection
	    URL url = new URL(targetURL);
	    connection = (HttpURLConnection)url.openConnection();
	    connection.setRequestMethod(reqType);
	    connection.setRequestProperty("Authorization", getAuthorizationHeader());
	    
	    if (reqType =="POST"){
	    	connection.setRequestProperty("Accept", "application/json");  
		    if (body != null) {
		    	connection.setRequestProperty("Content-Type", "application/json"); 
		    }

	    }
	    
	    connection.setUseCaches(false);
	    connection.setDoOutput(true);

	    if (reqType =="POST"){
	    	//Send request
		    DataOutputStream wr = new DataOutputStream (connection.getOutputStream());
		    wr.writeBytes(body);
		    wr.close();
	    }

	    //Get Response  
	    int respCode = connection.getResponseCode();
	    InputStream is = connection.getInputStream();
	    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
	    StringBuilder respBody = new StringBuilder(); // or StringBuffer if not Java 5+ 
	    String line;
	    while((line = rd.readLine()) != null) {
	    	respBody.append(line);
	    	respBody.append('\r');
	    }
	    rd.close();
	    Response response = new Response(respCode, null, respBody.toString());
	    return response;
	  } catch (Exception e) {
	    e.printStackTrace();
	    return null;
	  } finally {
	    if(connection != null) {
	      connection.disconnect(); 
	    }
	  }
	}

	private Patient PartyToPatient(Party party){
		Patient p = new Patient(party.getId(), party.getFirstNames(), party.getLastNames(), party.getGender(), party.getDateOfBirth(),
				party.getAddress().getAddress());
		
		ArrayList<PartyAdditionalInfo> info = party.getPartyAdditionalInfo();
	    for (int i = 0; i < info.size(); i++) {
	    	PartyAdditionalInfo pai = info.get(0);
	    	System.out.println("key is:" + pai.getKey());
	    	switch (pai.getKey()) {
	            case "rnsh.mrn":  p.setMrn(pai.getValue());
	                     break;
	            case "tumorType":  p.setTumorType(pai.getValue());
	                     break;
	            case "email":  p.setEmail(pai.getValue());
	                     break;
	            case "phone": p.setPhone(pai.getValue());
	                     break;
	            case "surgical":  p.setSurgical(pai.getValue());
	                     break;
	            default: break;
	        }
	    }
        
		return p;
	}
	
	public ArrayList<Patient> GetAllPatients() {
		ArrayList<Patient> patients = new ArrayList<Patient>();
		String url = getBaseUrl().concat("demographics/party/query?lastNames=*&rnsh.mrn=*");
		Response resp = OpenEHRRequest(url, null, "GET");
		
		ObjectMapper mapper = new ObjectMapper();
        PartyList parties = new PartyList();
		try {
	        parties = mapper.readValue(resp.getBody(), PartyList.class);
	        ArrayList<Party> p = parties.getParties();
	        for (int i = 0; i < p.size(); i++) {
	        	Patient patient = PartyToPatient(p.get(i));
	        	patients.add(patient);
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
          
		return patients;
	}

	public Patient GetPatient(int patientId) {
		StringBuilder sb = new StringBuilder();
		sb.append(getBaseUrl());
		sb.append("demographics/party/");
		sb.append(patientId);
		String url = sb.toString();
		Response resp = OpenEHRRequest(url, null, "GET");
		ObjectMapper mapper = new ObjectMapper();
        Party party = null;
        try {
        	party = mapper.readValue(resp.getBody(), Party.class);
        } catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return PartyToPatient(party);
	}

	public String GetEhrId(String mrn) {
		// TODO Auto-generated method stub
		return null;
	}

	public Patient CreatePatient(String firstname, String surname,
			String gender, String dob, String address, String tumorType,
			String surgical, String phone, String email) {
		// TODO Auto-generated method stub
		return null;
	}

	public static String getBaseUrl() {
		return baseUrl;
	}

}
