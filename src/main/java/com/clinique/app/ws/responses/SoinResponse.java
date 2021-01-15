package com.clinique.app.ws.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SoinResponse {

	
	@SuppressWarnings("unused")
	private String soinId;
	@SuppressWarnings("unused")
	private String typeSoin;
	@SuppressWarnings("unused")
	private String prix;
	@SuppressWarnings("unused")
	private UserResponse medecin;
	
}
