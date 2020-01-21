package net.buttology.collada.internal;

public class CSource {

	private String id;
	private int count;
	private float[] float_array;
	private CTechniqueCommon technique_common;
	
	public CSource(String id, int count, float[] float_array, CTechniqueCommon technique_common) {
		this.id = id;
		this.count = count;
		this.float_array = float_array;
		this.technique_common = technique_common;
	}

	public String getId() {
		return id;
	}

	public int getCount() {
		return count;
	}

	public float[] getFloatArray() {
		return float_array;
	}

	public CTechniqueCommon getTechniqueCommon() {
		return technique_common;
	}
	
}
