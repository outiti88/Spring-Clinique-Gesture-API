package com.clinique.app.ws.requests;

import java.util.Set;

public class DossierRequest {

	private Set<DosMedRequest> dosMedRequests;
	private Set<String> scannersIds;
	private Set<String> soinsIds;

	public Set<DosMedRequest> getDosMedRequests() {
		return dosMedRequests;
	}

	public void setDosMedRequests(Set<DosMedRequest> dosMedRequests) {
		this.dosMedRequests = dosMedRequests;
	}

	public Set<String> getScannersIds() {
		return scannersIds;
	}

	public void setScannersIds(Set<String> scannersIds) {
		this.scannersIds = scannersIds;
	}

	public Set<String> getSoinsIds() {
		return soinsIds;
	}

	public void setSoinsIds(Set<String> soinsIds) {
		this.soinsIds = soinsIds;
	}
}
