package com.boletos.apirest.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesUtils {
	
	private Logger log = LoggerFactory.getLogger(PropertiesUtils.class);
	private Properties prop;

	private String getPathname() {
        String path = System.getProperty("user.dir") + "/src/main/resources/application.properties";
		return path;
	}
	
	public PropertiesUtils()  {
		try {
			FileInputStream fis = new FileInputStream(new File(getPathname()));

			prop = new Properties();
			prop.load(fis);

		} catch (FileNotFoundException e) {
			log.error("Erro {}", e);
		} catch (IOException e) {
			log.error("Erro {}", e);
		}
	}
	
	public String getUrl() {
		return prop.getProperty("spring.datasource.url");
	}

	public String getUsername() {
		return prop.getProperty("spring.datasource.username");
	}

	public String getPassword() {
		return prop.getProperty("spring.datasource.password");
	}


}
