package br.com.vendas.aws.s3.picture;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class ImageManager {
	private static final String BASE_URL = "https://s3-sa-east-1.amazonaws.com/";
	
	public String upload(String bucketName,InputStream stream, String contentType, Long contentLength) throws IOException {
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType(contentType);
		metadata.setContentLength(contentLength);
				
		AWSCredentials awsCredentials = new PropertiesCredentials(ImageManager.class.getResourceAsStream("/AwsCredentials.properties"));
		AmazonS3 s3 = new AmazonS3Client(awsCredentials);
		
		String key = UUID.randomUUID().toString();
		PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, stream, metadata);

		s3.putObject(putObjectRequest);
		
		return BASE_URL + bucketName+ "/"+key;
		
	}
}