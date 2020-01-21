package net.buttology.collada.internal;

public class CInstanceMaterial {

	private String symbol;
	private String target;
	private String semantic;
	private String input_semantic;
	private int input_set;
	
	public CInstanceMaterial(String symbol, String target, String semantic, String input_semantic, int input_set) {
		this.symbol = symbol;
		this.target = target;
		this.semantic = semantic;
		this.input_semantic = input_semantic;
		this.input_set = input_set;
	}

	public String getSymbol() {
		return symbol;
	}

	public String getTarget() {
		return target;
	}

	public String getSemantic() {
		return semantic;
	}

	public String getInput_semantic() {
		return input_semantic;
	}

	public int getInput_set() {
		return input_set;
	}
	
}
