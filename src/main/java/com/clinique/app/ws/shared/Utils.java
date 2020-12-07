package com.clinique.app.ws.shared;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class Utils {
	
	private final Random random = new SecureRandom();
	private final String alphaNumS = "azertyuiopqsdfghjklmwxcvbnAZERTYUIOPQSDFGHJKLMWXCVBN0123456789";
	
	public String generateStringId(int length) {
		StringBuilder returnValue = new StringBuilder(length);
		
		for(int i=0;i<length;i++) returnValue.append(random.nextInt(alphaNumS.length()));
		
		return new String(returnValue);
	}
	
	
}
