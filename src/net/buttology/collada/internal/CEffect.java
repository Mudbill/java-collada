package net.buttology.collada.internal;

import java.util.Map;

public class CEffect {

	private String id;
	private Map<String, CNewParam> newparam_map;
	private CTechnique technique;
	
	public CEffect(String id, Map<String, CNewParam> newparam_map, CTechnique technique) {
		this.id = id;
		this.newparam_map = newparam_map;
		this.technique = technique;
	}

	public String getId() {
		return id;
	}

	public Map<String, CNewParam> getNewparams() {
		return newparam_map;
	}
	
	public CNewParam getNewparam(String sid) {
		return newparam_map.get(sid);
	}

	public CTechnique getTechnique() {
		return technique;
	}
	
}
