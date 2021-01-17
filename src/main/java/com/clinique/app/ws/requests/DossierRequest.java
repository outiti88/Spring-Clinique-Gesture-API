package com.clinique.app.ws.requests;

import java.util.Set;

public class DossierRequest {

	private Set<DosMedRequest> dosMedRequests;

	public Set<DosMedRequest> getDosMedRequests() {
		return dosMedRequests;
	}

	public void setDosMedRequests(Set<DosMedRequest> dosMedRequests) {
		this.dosMedRequests = dosMedRequests;
	}
	
	
}
