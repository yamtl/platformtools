package com.mdenetnetwork.ep.toolfunctions.core;

import java.net.HttpURLConnection;
import java.util.stream.Collectors;

import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public abstract class MdeNetToolFunction implements HttpFunction{
	@Override
	public void service(HttpRequest request, HttpResponse response) throws Exception {
		response.appendHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");
		
		if ("OPTIONS".equals(request.getMethod())) {
			response.appendHeader("Access-Control-Allow-Methods", "GET");
			response.appendHeader("Access-Control-Allow-Headers", "Content-Type");
			response.appendHeader("Access-Control-Max-Age", "3600");
			response.setStatusCode(HttpURLConnection.HTTP_NO_CONTENT);
			return;
		}
		else {
			JsonObject responseJson = new JsonObject();
			
			try {
				serviceImpl(getJsonObject(request), responseJson);
			}
			catch (Throwable t){
				responseJson.addProperty("output", t.getMessage());
				responseJson.addProperty("error", t.getMessage());
			}
			
			response.getWriter().write(responseJson.toString());
			
		}
	}
	
	public abstract void serviceImpl(JsonObject request, JsonObject response) throws Exception;
	
	protected JsonObject getJsonObject(HttpRequest request) throws Exception {
		String json = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		return getJsonObject(json);
	}
	
	protected JsonObject getJsonObject(String json) {
		return JsonParser.parseString(json).getAsJsonObject();
	}
	
	
}
