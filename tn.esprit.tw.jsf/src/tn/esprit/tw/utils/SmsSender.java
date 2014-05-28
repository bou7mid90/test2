package tn.esprit.tw.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


public class SmsSender {

	/**
	 * @param args
	 */
	
	private String phoneNumber;
	
	private String message;
	
	private static HttpClient client;
	
	

	public SmsSender() {
		super();
	}
	
	
	@SuppressWarnings("deprecation")
	public void send() throws ClientProtocolException, IOException{
		
		  client = new DefaultHttpClient();
		  HttpGet request = new HttpGet("http://api.clickatell.com/http/sendmsg?user=&password=&api_id=" +
		  		  "&to="+phoneNumber+
		  		  "&text="+message);
		  HttpResponse response = client.execute(request);
		  BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
		  String line = "";
		  while ((line = rd.readLine()) != null) {
		    System.out.println(line);
		  }
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static HttpClient getClient() {
		return client;
	}

	public static void setClient(HttpClient client) {
		SmsSender.client = client;
	}
	
	

}
