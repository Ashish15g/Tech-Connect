package com.tech_connect.baseclass;
import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.BeforeClass;

import com.tech_connect.pagesclass.Login;
import com.tech_connect.utilitiesclass.GetpropData;

public class BaseClass extends BaseDriver{
	@BeforeClass
	public void preCondition() throws IOException {
		String tech_brouser = GetpropData.propData("browser");
		String tech_url = GetpropData.propData("url");
		String u_name = GetpropData.propData("username");
		String pass = GetpropData.propData("password");
		if (tech_brouser.equals("chrome")) {
			driver = new ChromeDriver();
		} 
		else if (tech_brouser.equals("edge")) {
			driver = new EdgeDriver();
		} 
		else if (tech_brouser.equalsIgnoreCase("firefox")) { 
		    driver = new FirefoxDriver();
		} 
		else if (tech_brouser.equalsIgnoreCase("safari")) {
		    driver = new SafariDriver();
		} 
		else {
		    throw new IllegalArgumentException("Unsupported browser: " + tech_brouser);
		}

		driver.manage().window().maximize();
		driver.get(tech_url);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		Login lg = new Login(driver);
		lg.loginValidation(u_name, pass);
	
//		Thread.sleep(2000);

		

	}

}
