package com.example.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestServlet extends HttpServlet {
	public void nit() throws ServletException{
		System.out.println("init: " + this.getClass().getName());
	}
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		response.setContentType("text/html");// setting the content type
		PrintWriter pw = response.getWriter();// get the stream to write the data
		
		System.out.println("\n>> Read request Data");
	    System.out.println("getProtocol = " +request.getProtocol());
	    System.out.println("getRequestURI = " +request.getRequestURI());
	    System.out.println("getRequestURL = " +request.getRequestURL());
	    System.out.println("getContextPath = " +request.getContextPath());
	    System.out.println("getMethod = " +request.getMethod());
	    
	    String encode = request.getCharacterEncoding();
	    System.out.println("getCharacterEncoding = " +encode);
	    System.out.println("ContentType = " +request.getContentType());
	    if(encode == null) encode = "utf-8";
	    
	    System.out.println("[Print HTTP Headers]");
	    Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames != null && headerNames.hasMoreElements()) {
	        String key = headerNames.nextElement();
	        Enumeration<String> headerValues = request.getHeaders(key);
	        StringBuilder value = new StringBuilder();

	        if (headerValues != null && headerValues.hasMoreElements()) {
	        	String data = headerValues.nextElement();
	        	data = java.net.URLDecoder.decode(data, encode);
	            value.append(data);
	            while (headerValues.hasMoreElements()) {
		            data = headerValues.nextElement();
		        	data = java.net.URLEncoder.encode(data, encode);
	                value.append(",").append(data);
	            }
	        }
	        System.out.println(key + " = " + value.toString());
	    }
	    
	    System.out.println("[Print request parameters]");
	    Enumeration<String> paramNames = request.getParameterNames();
	    
	    String data = "";
		while (paramNames != null && paramNames.hasMoreElements()) {
	        String key = paramNames.nextElement();
	        String[] paramValues = request.getParameterValues(key);
	        StringBuilder value = new StringBuilder();

	        if (paramValues != null) {
	            if(paramValues.length > 0) {
	        		data = java.net.URLDecoder.decode(paramValues[0], encode);
	        		value.append(data);
	        	}
	        	if(paramValues.length > 1) {
		            for (int p=1; p < paramValues.length; p++) {
		            	data = java.net.URLDecoder.decode(paramValues[p], encode);
		                value.append(",").append(data);
		            }
		    	}
	        }
	        System.out.println(key + " = " + value.toString());
	    }
	    
	    System.out.println("[Print request body]");
	    BufferedReader in = null;
	    try {
		    Charset charset = Charset.forName(encode);
		    in = new BufferedReader(new InputStreamReader(request.getInputStream(),charset));
		    String inputLine;
		    StringBuffer reqBody = new StringBuffer();
		
		    while ((inputLine = in.readLine()) != null) {
		        reqBody.append(inputLine);
		    }
		    
		    System.out.println(reqBody.toString());
	    }
	    catch(Exception ex) {
	    	ex.printStackTrace();
	    }
	    finally {
	    	in.close();
	    }
	    
	    // print response JSON
	    String responseJson = "{\"irt\":1.2,\"descr\":\"ÀÀ´ä\"}";
	    System.out.println(">> response JSON = " + responseJson);
	    pw.println(responseJson);
		pw.close();// closing the stream
	}
}