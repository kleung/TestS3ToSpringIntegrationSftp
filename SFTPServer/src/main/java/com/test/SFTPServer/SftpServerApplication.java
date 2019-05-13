package com.test.SFTPServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(value= {
		"classpath:simple-integration.xml"
})
public class SftpServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SftpServerApplication.class, args);
	}

}
