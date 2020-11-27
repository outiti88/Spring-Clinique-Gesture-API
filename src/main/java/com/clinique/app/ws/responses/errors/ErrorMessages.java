package com.clinique.app.ws.responses.errors;


public enum ErrorMessages {
	
	MISSING_REQUIRED_FIELD("Missing Required tes testField"),
	REQUIRED_ALREADY_EXISTS("Record Already Exists"),
	INTERNAL_SERVER_ERROR("Internal Server Erreur"),
	NO_RECORD_FOUND("Record with provided not found");
	
	 private String  errorMessage ;

	private ErrorMessages(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String erorMessage) {
		this.errorMessage = erorMessage;
	}
	 

}
