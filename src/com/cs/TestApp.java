package com.cs;

public class TestApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PropertyReader pr = PropertyReader.getInstance();
		pr.loadProperties(args[0]);
		System.out.println(pr.getAllProperties().toString());
		String key = "BASE_LOCATION";
		System.out.println("Key : "+key +" Value: " + pr.getProperty(key) );
		
	}

}
