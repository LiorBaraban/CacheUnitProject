
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
			System.out.println(requestJson); 

			Type ref = new TypeToken<Request<DataModel<T>[]>>() {}.getType();
			Request<DataModel<T>[]> parsedRequest = new Gson().fromJson(requestJson, ref);

			String action = parsedRequest.getHeaders().get("action");
			
			
			switch (action) {
			case "UPDATE":
				boolean updateResult = this.controller.update(parsedRequest.getBody());
				writer.write("update result = " + updateResult);
				break;
			case "DELETE":
				boolean deleteResult = this.controller.delete(parsedRequest.getBody());
				writer.write("delete result = " + deleteResult);
				break;
			case "GET":
				DataModel<T>[] dataModelsArray = this.controller.get(parsedRequest.getBody());
				writer.write("get result =" + dataModelsArray );
				break;
			case "STATISTICS":
				String statistics = this.controller.getStatistics();
				writer.write(statistics);
			}
			
			writer.flush();
			
			bufReader.close();
			writer.close();
		} catch (Exception e) {
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
							isFinished = true;
						}
						if (isFinished) {
							break;
						}
					}
				}

				if (jsonCurlyBraceCounter > 0) {
					sb.append(s);
				}
			}
			System.out.println("finished reading json");
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return sb.toString();
	}
}
