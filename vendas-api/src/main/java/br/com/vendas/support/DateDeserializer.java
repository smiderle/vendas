package br.com.vendas.support;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class DateDeserializer extends JsonDeserializer<Date>{

	private static Logger LOG = LoggerFactory.getLogger(DateDeserializer.class);
	
	@Override
	public Date deserialize(JsonParser json, DeserializationContext context) throws IOException, JsonProcessingException {	
		
		try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            return df.parse(json.getText());
        } catch (ParseException e) {
        	//If that date format didnt work, try another...
        	try {
	        	DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	            return df.parse(json.getText());
        	} catch (Exception e1){
        		LOG.warn("Could not deserialize: " + json.getText() );
        		//proceed...
        		return null;
        	}
        }
	}

}
