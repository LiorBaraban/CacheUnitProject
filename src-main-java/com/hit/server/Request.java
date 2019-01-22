package com.hit.server;

import java.io.Serializable;
import java.util.Map;

public class Request<T> implements Serializable{

	private Map<String, String> headers;
	private T body;
	
	Request(Map<String,String> headers, T body){
		this.headers = headers;
		this.body = body;
	}

	public T getBody() {
		return this.body;
	}
	
	public void setBody(T body) {
		this.body = body;
	}
	
	public Map<String, String> getHeaders(){
		return this.headers;
	}
	
	public void setHeaders(Map<String,String> headers) {
		this.headers = headers;
	}
	
	@Override
	public String toString() {
		return this.headers.toString() + "\n" + this.body.toString();
	}
	
}
