package com.github.fiviumaustralia.rnshpilot;

import graphql.GraphQL;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



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
        System.out.println("query is:");
        System.out.println(query);
		Object result = new GraphQL(rmshSchema.getSchema()).execute(query).getData();
		
		ObjectMapper mapper = new ObjectMapper();
        jsonString = mapper.writeValueAsString(result);
        
        System.out.println("result is:");
        System.out.println(result);
        System.out.println("jsonString is:");
        System.out.println(jsonString);
        
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(jsonString);
        resp.getWriter().flush();
        resp.getWriter().close();
        
	}

}
