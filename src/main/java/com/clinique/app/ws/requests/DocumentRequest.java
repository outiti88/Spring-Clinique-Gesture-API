package com.clinique.app.ws.requests;


import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentRequest {

	private Set<DocMedRequest> docMedRequests;
	
}
