package com.github.fiviumaustralia.rnshpilot;

import java.util.ArrayList;

public interface PatientService {
	
	/**
	 * Get all patients from a PAS and return them in a list
	 * 
	 * @return  a list of patients
	 */
	public ArrayList<Patient> GetAllPatients();
	
	
	/**
	 *  get patient details for a specified id
	 * @param patientId
	 * @return a patient object
	 */
	public Patient GetPatient(int patientId);
	
	
	/**
	 * Get the Electronic Health Record Id using a medical record number
	 * @param mrn - the medical record number used to get the ehr id
	 * @return string representation of the EHR ID
	 */
	public String GetEhrId(String mrn);
	
	
	/**
	 * Create a new patient
	 * @param firstname
	 * @param surname
	 * @param gender
	 * @param dob
	 * @param address
	 * @param tumorType
	 * @param surgical
	 * @param phone
	 * @param email
	 * @return a patient object for the newly created patient
	 */
	public Patient CreatePatient(String firstname, String surname, String gender, String dob, String address,
			String tumorType, String surgical, String phone, String email);
	
}
