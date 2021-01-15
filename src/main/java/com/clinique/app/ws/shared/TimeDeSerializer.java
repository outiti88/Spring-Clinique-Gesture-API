package com.clinique.app.ws.shared;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class TimeDeSerializer extends StdDeserializer<Date> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4366009785822274862L;

	public TimeDeSerializer() {
        super(Date.class);
    }

    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt)
        throws IOException, JsonProcessingException {
        String value = p.readValueAs(String.class);
        try {
            return new SimpleDateFormat("HH:mm").parse(value);
        } catch (DateTimeParseException | ParseException e) {
            //throw an error
        }
		return null;
    }

}