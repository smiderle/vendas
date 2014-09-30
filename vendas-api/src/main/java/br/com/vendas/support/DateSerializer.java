package br.com.vendas.support;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class DateSerializer extends JsonSerializer<Date> {
    @Override
    public void serialize(Date aDate, JsonGenerator generator, SerializerProvider aProvider) throws IOException, JsonProcessingException {      

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy"); 
        String formattedDate = formatter.format(aDate);
        
        generator.writeString(formattedDate);
    }
}

