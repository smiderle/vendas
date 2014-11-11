package br.com.vendas.services.image;

import java.io.IOException;
import java.io.InputStream;

import br.com.vendas.services.support.ServiceResponse;


public interface ImageService {
	
	/**
	 * Faz upload de uma imagem para o aws S3
	 * @param bucketName
	 * @param stream
	 * @param contentType
	 * @param contentLength
	 * @return
	 * @throws IOException 
	 */
	ServiceResponse<String> upload(String bucketName, InputStream stream, String contentType, Long contentLength) throws IOException;

}
