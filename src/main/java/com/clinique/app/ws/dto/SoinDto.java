package com.clinique.app.ws.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SoinDto {

	@SuppressWarnings("unused")
	private String soinId;
	@SuppressWarnings("unused")
	private String typeSoin;
	@SuppressWarnings("unused")
	private String prix;
	@SuppressWarnings("unused")
	private UserDto medecin;
	
}
