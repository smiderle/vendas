package br.com.vendas.helper;

import java.io.IOException;

import br.com.vendas.exception.ParseJsonException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperHelper {
	
	public <T> T readValue(String jsonStr, Class<T> valueType) throws ParseJsonException {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			return objectMapper.readValue(jsonStr, valueType);
		} catch (IOException e) {
			throw new ParseJsonException(e);
		}		
	}

}
