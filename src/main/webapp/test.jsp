<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ page
import="java.util.*,java.nio.charset.Charset,java.io.*" 
%><%
    System.out.println("\n>> Read request Data");
    
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
	while (paramNames != null && paramNames.hasMoreElements()) {
        String key = paramNames.nextElement();
        String[] paramValues = request.getParameterValues(key);
        StringBuilder value = new StringBuilder();

        if (paramValues != null) {
        	if(paramValues.length > 0) {
        		value.append(paramValues[0]);
        	}
        	if(paramValues.length > 1) {
	            for (int p=1; p < paramValues.length; p++) {
	                value.append(",").append(paramValues[p]);
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
    String responseJson = "{\"irt\":1.2,\"descr\":\"응답\"}";
    System.out.println(">> response JSON = " + responseJson);
 %><%= responseJson %>