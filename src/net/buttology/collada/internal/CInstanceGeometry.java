package net.buttology.collada.internal;

public class CInstanceGeometry {

	private String url;
	private CInstanceMaterial instanceMaterial;
	
	public CInstanceGeometry(String url, CInstanceMaterial instanceMaterial) {
		this.url = url;
		this.instanceMaterial = instanceMaterial;
	}

	public String getUrl() {
		return url;
	}

	public CInstanceMaterial getInstanceMaterial() {
		return instanceMaterial;
	}

}
