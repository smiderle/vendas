package br.com.vendas.services.image;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Service;

import br.com.vendas.aws.s3.picture.Bucket;
import br.com.vendas.aws.s3.picture.ImageManager;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.services.support.ServiceResponseFactory;

@Service
public class ImageServiceImpl implements ImageService {
	
	@Override
	public ServiceResponse<String> upload(String bucketName, InputStream stream, String contentType, Long contentLength) throws IOException {
		ImageManager im = new ImageManager();
		
		return ServiceResponseFactory.create(im.upload(Bucket.PICTURES_PRODUCT,  stream, contentType, contentLength));
		
	}

}
