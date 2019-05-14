package com.test.TestS3Integration.s3Resource.restController;

import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.util.IOUtils;
import com.test.TestS3Integration.s3Download.service.S3DownloadObjectService;
import com.test.TestS3Integration.s3List.service.S3ListFileService;

@RestController
@RequestMapping("/api")
public class S3ObjectRestController {
	
	@Autowired
	private S3ListFileService listS3FileService;
	
	@Autowired
	private S3DownloadObjectService s3DownloadObjectService;
	
	public S3ObjectRestController() {
		super();
	}
	
	@GetMapping(value="/{bucketName}/objects/{filenamePattern}",
					produces="application/json")
	public List<String> listS3Objects(@PathVariable("bucketName")String bucketName,
										@PathVariable("filenamePattern")String filenamePattern) throws Exception {
		List<String> result = this.listS3FileService.getS3ObjectListAsStringDescriptions(bucketName, filenamePattern);
		
		return result;
	}
	
	@GetMapping(value="/{bucketName}/files/{filename}")
	public byte[] getFile(@PathVariable("bucketName")String bucketName,
									@PathVariable("filename")String filename) throws Exception {
		byte[] resultFileContent = this.s3DownloadObjectService.downloadObject(bucketName, filename);
		
		return resultFileContent;
	}

}
