package net.buttology.collada.internal;

public class CInput {

	private String semantic;
	private String source;
	private int offset;
	private int set;
	
	public CInput(String semantic, String source, int offset, int set) {
		this.semantic = semantic;
		this.source = source;
		this.offset = offset;
		this.set = set;
	}

	public String getSemantic() {
		return semantic;
	}

	public String getSource() {
		return source;
	}

	public int getOffset() {
		return offset;
	}

	public int getSet() {
		return set;
	}
	
	public CInput updateOffsetAndSet(int offset, int set) {
		this.offset = offset;
		this.set = set;
		return this;
	}
	
}
