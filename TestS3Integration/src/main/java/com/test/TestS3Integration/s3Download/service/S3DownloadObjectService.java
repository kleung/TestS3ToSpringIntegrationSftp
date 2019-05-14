package com.test.TestS3Integration.s3Download.service;

import java.io.IOException;
import java.io.InputStream;

import com.amazonaws.util.IOUtils;
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
	
	public byte[] downloadObject(String bucketName, String filename) throws IOException {
		String s3Url = String.format("s3://%s/%s", bucketName, filename);
		
		Resource targetS3Object = this.resourceLoader.getResource(s3Url);
		
		InputStream resultStream = (targetS3Object != null) ? targetS3Object.getInputStream() : null;
		byte[] result = null;

		try {
			result = IOUtils.toByteArray(resultStream);
		} finally {
			if(resultStream != null) {
				try {
					resultStream.close();
				} catch (IOException ioe) {
					//cannot handle stream close error, but should log
				}
			}
		}
		
		return result;
	}
	
}
