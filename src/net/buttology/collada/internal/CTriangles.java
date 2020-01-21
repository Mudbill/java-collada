package net.buttology.collada.internal;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CTriangles {

	private String material;
	private int count;
	private Map<String, CInput> inputs;
	private int[] pArray;
	
	public CTriangles(String material, int count, List<CInput> inputs, int[] pArray) {
		this.material = material;
		this.count = count;
		this.inputs = new HashMap<String, CInput>();
		for(CInput c : inputs) this.inputs.put(c.getSemantic(), c);
		this.pArray = pArray;
	}

	public String getMaterial() {
		return material;
	}

	public int getCount() {
		return count;
	}

	public Collection<CInput> getInputs() {
		return inputs.values();
	}
	
	public CInput getInput(String semantic) {
		return inputs.get(semantic);
	}

	public int[] getPrimitiveArray() {
		return pArray;
	}
	
}
