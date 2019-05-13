package com.test.TestS3Integration.s3List.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.core.io.s3.PathMatchingSimpleStorageResourcePatternResolver;
import org.springframework.cloud.aws.core.io.s3.SimpleStorageResource;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;

@Service
public class S3ListFileService {
	
	private ResourcePatternResolver resourcePatternResolver;
	
	public S3ListFileService() {
		super();
	}

 	@Autowired
    public void setupResolver(ApplicationContext applicationContext, AmazonS3 amazonS3){
        this.resourcePatternResolver = new PathMatchingSimpleStorageResourcePatternResolver(amazonS3, applicationContext);
    }
	
	public Resource[] getS3ObjectList(String bucketName, String filenamePattern) throws IOException {
			String s3UrlPattern = String.format("s3://%s/%s", bucketName, filenamePattern);
			
			Resource[] result = this.resourcePatternResolver.getResources(s3UrlPattern);
			
			return result;
	}
	
	public List<String> getS3ObjectListAsStringDescriptions(String bucketName, String filenamePattern) throws IOException {
		List<String> result = new ArrayList<String>();
		
		Resource[] s3Objects = this.getS3ObjectList(bucketName, filenamePattern);
		
		for(Resource metadata : s3Objects) {
			if(metadata instanceof SimpleStorageResource ) {
				SimpleStorageResource s3Metadata = (SimpleStorageResource )metadata;
			
				String filename = s3Metadata.getFilename();
				String url = s3Metadata.getURL().toString();
				String description = s3Metadata.getDescription();
					
				result.add(String.format("filename: %s, url: %s, description: %s", filename, url, description));
			}
		}
		
		return result;
	}
}
