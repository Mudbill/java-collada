package net.buttology.collada.internal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CVertices {

	private String id;
	private Map<String, CInput> inputs;
	
	public CVertices(String id, List<CInput> inputs) {
		this.id = id;
		this.inputs = new HashMap<String, CInput>();
		for(CInput c : inputs) this.inputs.put(c.getSemantic(), c);
	}

	public String getId() {
		return id;
	}
	
	public CInput getInput(String semantic) {
		CInput c = inputs.get(semantic);
		return c;
	}
	
}
