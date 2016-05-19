package com.github.fiviumaustralia.rnshpilot;

import graphql.GraphQL;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;







import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
//import org.codehaus.jackson.map.SerializationConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;

public class RNSHGraphQLServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	System.out.println("In the GET request");
    	RNSHSchemaSingleton rmshSchema = RNSHSchemaSingleton.getInstance();
        resp.getOutputStream().write("Hello World. Get is not yet implemented".getBytes());
    }

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("In the POST request");
		RNSHSchemaSingleton rmshSchema = RNSHSchemaSingleton.getInstance();
		BufferedReader br = req.getReader();
		String line = null;
		String query = "";
		String jsonString = "";
		try {
			while ((line = br.readLine()) != null) {
				query = query.concat(line);
			}
		}
		catch(Exception e){
	         e.printStackTrace();
	    }
		ObjectMapper mapper = new ObjectMapper();
		Query q = new Query();
		try {
	        q = mapper.readValue(query, Query.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		query = q.getQuery();
        System.out.println("query is:");
        System.out.println(query);
        Object result = null;
        TypeReference<Map<String,Object>> typeRef = new TypeReference<Map<String,Object>>() {};
        
        if (q.getVariables() == null) { 
        	result = new GraphQL(rmshSchema.getSchema()).execute(query).getData();
        } else {
        	Map<String, Object> variables = mapper.readValue(q.getVariables(), typeRef);
        	result = new GraphQL(rmshSchema.getSchema()).execute(query, new Object(), variables).getData();
        }
           
        QueryResult qr = new QueryResult(result);

        jsonString = mapper.writeValueAsString(qr);
        System.out.println("query result is:");
        System.out.println(jsonString);
        
        resp.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Content-Type", "application/json");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(jsonString);
        resp.getWriter().flush();
        resp.getWriter().close();
        
	}

	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
		resp.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
		resp.setHeader("Access-Control-Allow-Origin", "*");

	}
	
	

}
