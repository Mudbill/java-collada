package net.buttology.collada.internal;

public class CTechnique {

	private String id;
	private String sid;
	private CBlinn blinn;
	
	public CTechnique(String id, String sid, CBlinn blinn) {
		this.id = id;
		this.sid = sid;
		this.blinn = blinn;
	}

	public String getId() {
		return id;
	}

	public String getSid() {
		return sid;
	}

	public CBlinn getBlinn() {
		return blinn;
	}
}
