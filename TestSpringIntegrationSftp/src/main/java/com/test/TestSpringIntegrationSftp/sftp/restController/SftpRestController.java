package com.test.TestSpringIntegrationSftp.sftp.restController;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

import com.test.TestSpringIntegrationSftp.s3List.service.S3ListFileService;
import com.test.TestSpringIntegrationSftp.sftpWrite.Service.SftpFileWriteService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api")
public class SftpRestController {
	
	@Autowired
	private S3ListFileService listS3FileService;
	
	@Autowired
	private SftpFileWriteService sftpFileWriteService;
	
	@GetMapping(value="/{bucketName}/copy/{filenamePattern}",
					produces="text/plain")
	public String triggerSftp(@PathVariable("bucketName")String bucketName,
										@PathVariable("filenamePattern")String filenamePattern) throws IOException {
		
		Resource[] resources = this.listS3FileService.getS3ObjectList(bucketName, filenamePattern);
		
		this.sftpFileWriteService.writeFiles(resources);
		
		return "OK";
	}
	
}
