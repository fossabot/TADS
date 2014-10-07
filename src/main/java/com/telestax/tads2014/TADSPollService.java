
package com.telestax.tads2014;

import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * A simple CDI service which is able to say hello to someone
 * 
 * @author Jean Deruelle
 * 
 */
public class TADSPollService {

    static String TP_URL = "https://tp.mu/holler/lookup_msisdn.php";
    static String CHARSET = "UTF-8";
    static String TOKEN = "9YtXr4AhVKaqBWrTmNYj2Px";
    static String COUNTRY = "original-country";

    String getBirthDate(String fromNumber) {
	if(fromNumber == null) {
		return "69";
	} 
        return fromNumber.substring(fromNumber.length()-2, fromNumber.length());
    }

    String getLocation(String fromNumber) throws Exception {
	String location = "France";
	if(fromNumber == null) {
		return location;
	} 
	if(fromNumber.startsWith("+")) {
		fromNumber = fromNumber.substring(1, fromNumber.length());
	}
	
	String query = String.format("token=%s&msisdn=%s", URLEncoder.encode(TOKEN, CHARSET), URLEncoder.encode(fromNumber, CHARSET));

	System.out.println("url " + TP_URL + "?" + query);
	URLConnection connection = new URL(TP_URL + "?" + query).openConnection();
	connection.setRequestProperty("Accept-Charset", CHARSET);

	HttpURLConnection httpConnection = (HttpURLConnection) connection; 
	int status = httpConnection.getResponseCode();

	if(status == 200) {
		BufferedReader in = new BufferedReader(new InputStreamReader(
                                    connection.getInputStream()));
		String responseBody = "";
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
		    System.out.println("response line " + inputLine);
		    responseBody = responseBody.concat(inputLine);
		}
		in.close();
		System.out.println("responseBody" + responseBody);
		int indexOfCountry = responseBody.indexOf(COUNTRY);
		if(indexOfCountry != -1) {
			String originalCountryStart = responseBody.substring(indexOfCountry + "original-country\":\"".length(), responseBody.length());
			String originalCountry = originalCountryStart.substring(0, originalCountryStart.indexOf("\""));
			System.out.println("originalCountry " + originalCountry);
			location = originalCountry;
		}
	}
	return location;
    }

    String getDrink(String drink) {
	if(drink == null) {
		return "no drink";
	}
	if(drink.equalsIgnoreCase("1")) {
		return "Beer";
	}
	if(drink.equalsIgnoreCase("2")) {
		return "Wine";
	}
	if(drink.equalsIgnoreCase("3")) {
		return "Soft Drink";
	}
	return "no drink";
    } 
}
