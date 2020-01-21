package net.buttology.collada.internal;

public class CInstanceEffect {

	private String sid;
	private String name;
	private String url;
	
	public CInstanceEffect(String sid, String name, String url) {
		this.sid = sid;
		this.name = name;
		this.url = url;
	}

	public String getSid() {
		return sid;
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}
	
}
