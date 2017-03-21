package com.cs.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
	
	private static PropertyReader Reader; 
	private Properties CProperties;
	
//checking if obj is created if not created a new obj is created
	public static PropertyReader getInstance() {
		if (Reader == null) {
			Reader = new PropertyReader();
		}
		return Reader;

	}
// Creating Properties class obj if not created
	
	public void loadProperties(String propertiesFilePath) {
		if (CProperties == null) {
			CProperties = new Properties();
		}
//load the input file
		try {
			CProperties.load(new FileInputStream(propertiesFilePath));

		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public String getProperty(String key) {
		return Reader.getProperty(key);
	}

	public int getInt(String key) {
		return Integer.parseInt(Reader.getProperty(key));
	}

	public boolean getBoolean(String key) {
		return Boolean.parseBoolean(Reader.getProperty(key));
	}

	public double getDouble(String key) {
		return Double.parseDouble(Reader.getProperty(key));
	}

	public float getFloat(String key) {
		return Float.parseFloat(Reader.getProperty(key));
	}
	
	public Properties getAllProperties()
	{
		return 	CProperties;
	}
}
