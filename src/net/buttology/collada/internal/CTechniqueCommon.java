package net.buttology.collada.internal;

public class CTechniqueCommon {

	private String source;
	private int count;
	private int stride;
	
	public CTechniqueCommon(String source, int count, int stride) {
		this.source = source;
		this.count = count;
		this.stride = stride;
	}

	public String getSource() {
		return source;
	}

	public int getCount() {
		return count;
	}

	public int getStride() {
		return stride;
	}
	
}
