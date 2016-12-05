package com.github.fiviumaustralia.rnshpilot;

import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import graphql.GraphQL;
import graphql.schema.GraphQLNonNull;
import static graphql.schema.GraphQLArgument.newArgument; 
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import static graphql.Scalars.GraphQLString;
import static graphql.Scalars.GraphQLInt;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;

public class RNSHSchemaSingleton {
	private static RNSHSchemaSingleton instance = null;
	private GraphQLSchema schema;
	private PatientService patientService;
	
	public GraphQLSchema getSchema() {
		return schema;
	}

	protected RNSHSchemaSingleton() {
		// Exists only to defeat instantiation. 
		
		// Allergies Object Type
		GraphQLObjectType allergyType = newObject()
				.name("Allergies")
				.description("Represent the allergies of a patient") 
				.field(newFieldDefinition() 
                    .name("name") 
                    .description("The name of the Allergy the patient has") 
                    .type(GraphQLString) 
                    .build()) 
            	.field(newFieldDefinition() 
                    .name("date") 
                    .description("Then date the patient identified the allergy") 
                    .type(GraphQLString) 
                    .build()) 
                .build();
		
		// Patient Object Type
		GraphQLObjectType patientType = newObject()
				.name("Patient")
				.description("Represent the type of a Patient in an openEHR party") 
				.field(newFieldDefinition() 
                    .name("id") 
                    .description("openEHR Party Id of the Patient.") 
                    .type(GraphQLInt) 
                    .build()) 
            	.field(newFieldDefinition() 
                    .name("mrn") 
                    .description("Medical Record Number used for patient identification at RNSH") 
                    .type(GraphQLString) 
                    .build()) 
                .field(newFieldDefinition() 
                    .name("ehrId") 
                    .description("openEHR electronic health record identifier") 
                    .type(GraphQLString) 
                    .dataFetcher(new DataFetcher() { 
						public Object get(DataFetchingEnvironment environment) {
							Patient p = (Patient)environment.getSource();
							String mrn = p.getMrn();

							return GetPatientService().GetEhrId(mrn);
						} 
                    }) 
                    .build())
                .field(newFieldDefinition() 
                    .name("dob") 
                    .description("Patient Date of birth") 
                    .type(GraphQLString) 
                    .build())
                .field(newFieldDefinition() 
                    .name("firstname") 
                    .description("Patient first name") 
                    .type(GraphQLString) 
                    .build())
                .field(newFieldDefinition() 
                    .name("surname") 
                    .description("Patient surname") 
                    .type(GraphQLString) 
                    .build())
                .field(newFieldDefinition() 
                    .name("address") 
                    .description("Patients main contact address") 
                    .type(GraphQLString) 
                    .build())
                .field(newFieldDefinition() 
                    .name("phone") 
                    .description("Patients phone number") 
                    .type(GraphQLString) 
                    .build())
                .field(newFieldDefinition() 
                    .name("email") 
                    .description("Patients email address") 
                    .type(GraphQLString) 
                    .build())
                .field(newFieldDefinition() 
                    .name("gender") 
                    .description("Patient Gender, either MALE or FEMALE") 
                    .type(GraphQLString) 
                    .build())
                .field(newFieldDefinition() 
                    .name("tumorType") 
                    .description("Tumour Type Either Prostate, Breast or CNS") 
                    .type(GraphQLString) 
                    .build())
                .field(newFieldDefinition() 
                    .name("surgical") 
                    .description("If a patient has had surgery on their tumour then true otherwise false") 
                    .type(GraphQLString) 
                    .build())
                .field(newFieldDefinition() 
                    .name("allergies") 
                    .description("A list of allergies that the patient may have.") 
                    .type(new GraphQLList(allergyType)) 
                    .build())   
                .build();
		
		
		
		GraphQLObjectType queryType = newObject()
                .name("RootQuery")
                .field(newFieldDefinition() 
                    .name("patients") 
                    .type(new GraphQLList(patientType)) 
                    .dataFetcher(new DataFetcher() { 
                        public Object get(DataFetchingEnvironment environment) { 
                        	ArrayList<Patient> patients = GetPatientService().GetAllPatients();
                            return patients; 
                        } 
                    }) 
                    .build())
                .field(newFieldDefinition() 
                    .name("patient") 
                    .type(patientType) 
                    .argument(newArgument() 
                            .name("id") 
                            .type(new GraphQLNonNull(GraphQLInt)) 
                            .build()) 
                    .dataFetcher(new DataFetcher() { 
						public Object get(DataFetchingEnvironment environment) {
							int id = environment.getArgument("id");
							
							return GetPatientService().GetPatient(id);
						} 
                    }) 
                    .build())
                .build();

		schema = GraphQLSchema.newSchema()
                .query(queryType)
                .build();
	} 
	
	public static RNSHSchemaSingleton getInstance() {
		if(instance == null) {
			instance = new RNSHSchemaSingleton();
		}
		return instance;
	}
	
	public PatientService GetPatientService(){
		if (patientService == null) {
			System.out.println("initialising patient service");
			patientService = new OpenEHR("https://ehrscape.code4health.org/rest/v1/", "rnsh.mrn", "rnshpilot_c4h", "lIsombRI");
		}
		return patientService;
	}
	
}
