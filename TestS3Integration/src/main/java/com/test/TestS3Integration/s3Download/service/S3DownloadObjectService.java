package com.test.TestS3Integration.s3Download.service;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Service
public class S3DownloadObjectService {

	@Autowired
	protected ResourceLoader resourceLoader;
	
	public S3DownloadObjectService() {
		super();
	}
	
	public InputStream downloadObject(String bucketName, String filename) throws IOException {
		String s3Url = String.format("s3://%s/%s", bucketName, filename);
		
		Resource targetS3Object = this.resourceLoader.getResource(s3Url);
		
		InputStream result = (targetS3Object != null) ? targetS3Object.getInputStream() : null;
		
		return result;
	}
	
}
