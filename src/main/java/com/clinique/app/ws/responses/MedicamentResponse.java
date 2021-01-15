package com.clinique.app.ws.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicamentResponse {
	
	private String medicamentId;
	private String name;
	private String type;
	private String category;
	private String price;
	
}
