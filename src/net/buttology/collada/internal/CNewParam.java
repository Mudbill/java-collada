package net.buttology.collada.internal;


public class CNewParam {

	private String sid;
	private CSampler2D sampler2D;
	
	public CNewParam(String sid, CSampler2D sampler2D) {
		this.sid = sid;
		this.sampler2D = sampler2D;
	}

	public String getSid() {
		return sid;
	}

	public CSampler2D getSampler2D() {
		return sampler2D;
	}
	
}
