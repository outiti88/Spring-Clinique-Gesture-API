package com.clinique.app.ws.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SoinRequest {
	
	private String typeSoin;
	private String prix;
	private String medecinId;
	
}
