package net.buttology.collada.internal;

public class CMaterial {

	private String id;
	private String name;
	private CInstanceEffect instance_effect;
	
	public CMaterial(String id, String name, CInstanceEffect instance_effect) {
		this.id = id;
		this.name = name;
		this.instance_effect = instance_effect;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public CInstanceEffect getInstanceEffect() {
		return instance_effect;
	}
	
}
