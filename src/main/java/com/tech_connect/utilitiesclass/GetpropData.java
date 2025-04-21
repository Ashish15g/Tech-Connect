package com.tech_connect.utilitiesclass;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class GetpropData {
	
	public static String propData(String key) throws IOException{
		  Properties prop = new Properties();
		  FileInputStream fis = new FileInputStream("src//main//resources//config.properties");
		  prop.load(fis);
		 String data= prop.getProperty(key);
		 return data;
	}
}
