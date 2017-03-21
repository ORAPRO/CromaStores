package com.cs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {
	
	private static PropertyReader reader; 
	private Properties cromaStoreProperties;
	
//checking if obj is created if not created a new obj is created
	public static PropertyReader getInstance() {
		if (reader == null) {
			reader = new PropertyReader();
		}
		return reader;

	}
// Creating Properties class obj if not created
	
	public void loadProperties(String propertiesFilePath) {
		if (cromaStoreProperties == null) {
			cromaStoreProperties = new Properties();
		}
//load the input file
		try {
			cromaStoreProperties.load(new FileInputStream(propertiesFilePath));

		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public String getProperty(String key) {
		return cromaStoreProperties.getProperty(key);
	}

	public int getInt(String key) {
		return Integer.parseInt(cromaStoreProperties.getProperty(key));
	}

	public boolean getBoolean(String key) {
		return Boolean.parseBoolean(cromaStoreProperties.getProperty(key));
	}

	public double getDouble(String key) {
		return Double.parseDouble(cromaStoreProperties.getProperty(key));
	}

	public float getFloat(String key) {
		return Float.parseFloat(reader.getProperty(key));
	}
	
	public Properties getAllProperties()
	{
		return 	cromaStoreProperties;
	}
}
