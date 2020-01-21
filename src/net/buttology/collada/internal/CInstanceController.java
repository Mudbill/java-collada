package net.buttology.collada.internal;

public class CInstanceController {

	private String url;
	private String skeleton;
	private CInstanceMaterial instanceMaterial;
	
	public CInstanceController(String url, String skeleton, CInstanceMaterial instanceMaterial) {
		this.url = url;
		this.skeleton = skeleton;
		this.instanceMaterial = instanceMaterial;
	}

	public String getUrl() {
		return url;
	}

	public String getSkeleton() {
		return skeleton;
	}

	public CInstanceMaterial getInstanceMaterial() {
		return instanceMaterial;
	}
	
}
