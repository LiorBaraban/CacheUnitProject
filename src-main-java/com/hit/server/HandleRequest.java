/**
 * Class 3
 * This class gets the socket request from Server
 * Reads the information of type Request 
 * (it's a json that comes in the socket so we need Gson)
 * it also reads the request header
 * 
 * And then it passes it to a relevant method in CacheUnitController
 **/

package com.hit.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.hit.dm.DataModel;
import com.hit.services.CacheUnitController;

public class HandleRequest<T> implements Runnable {

	private Socket socket;

	private CacheUnitController<T> controller;

	public HandleRequest(Socket s, CacheUnitController<T> controller) {
		this.socket = s;
		this.controller = controller;
	}

	@Override
	public void run() {
		try {
			BufferedReader bufReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream()));
			System.out.println("In handle request thread run");
			String requestJson = this.parseRequestToJson(bufReader, writer);
			// System.out.println(req); 

			Type ref = new TypeToken<Request<DataModel<T>[]>>() {}.getType();
			Request<DataModel<T>[]> parsedRequest = new Gson().fromJson(requestJson, ref);

//			System.out.println(parsedRequest);

			// parsedRequest.getBody() == datamodel[] => dm[0].getDataModelId() == 1,
			// dm[0].getContent()= "lior";
			// parsedRequest.getHeaders() == map => map["action"] = "UPDATE";

//			System.out.println("action= " + parsedRequest.getHeaders().get("action"));
//			System.out.println("dm content= " + parsedRequest.getBody()[0].getContent());
			
			String action = parsedRequest.getHeaders().get("action");
			
			
			//TODO - refactor the methods below need to return a value that Writer will write back to the clients
			switch (action) {
			case "UPDATE":
				this.controller.update(parsedRequest.getBody());
				break;
			case "DELETE":
				this.controller.delete(parsedRequest.getBody());
				break;
			case "GET":
				this.controller.get(parsedRequest.getBody());
				break;
			}
			
			// extract the socket input / output streams
			// read json + header
			// choose a relevant method to pass the info to...
			bufReader.close();
			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	String parseRequestToJson(BufferedReader bufReader, PrintWriter writer) throws IOException {
		
		StringBuilder sb = new StringBuilder();
		try {

			boolean isFinished = false;
			int jsonCurlyBraceCounter = 0;
			while (!isFinished) {
				String s = new String(bufReader.readLine());
				if (s.indexOf("{") >= 0) {
					for (int i = 0; i < s.length(); i++) {
						if (s.charAt(i) == "{".charAt(0)) {
							jsonCurlyBraceCounter++;
						}
					}
				}

				if (s.indexOf("}") >= 0) {
					for (int i = 0; i < s.length(); i++) {
						if (s.charAt(i) == "}".charAt(0)) {
							jsonCurlyBraceCounter--;
						}
						// reading the last } :
						if (jsonCurlyBraceCounter == 0) {
							sb.append(s);
//							System.out.println(sb);
							isFinished = true;
						}
						if (isFinished) {
							break;
						}
					}
				}

				if (jsonCurlyBraceCounter > 0) {
					sb.append(s);
//					System.out.println(sb);
				}
			}
			System.out.println("finished reading json");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return sb.toString();
	}
}
