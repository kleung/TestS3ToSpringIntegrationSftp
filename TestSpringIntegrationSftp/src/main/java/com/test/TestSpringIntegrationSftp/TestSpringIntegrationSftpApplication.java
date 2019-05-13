package com.test.TestSpringIntegrationSftp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(value= {
		"classpath:sftp-integration.xml"
})
public class TestSpringIntegrationSftpApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestSpringIntegrationSftpApplication.class, args);
	}

}
