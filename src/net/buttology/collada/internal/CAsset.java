package net.buttology.collada.internal;

public class CAsset {
	
	private String up_axis;
	private float unitMeter;
	
	public CAsset(String up_axis, float unitMeter) {
		this.up_axis = up_axis;
		this.unitMeter = unitMeter;
	}

	public String getUpAxis() {
		return up_axis;
	}

	public float getUnitMeter() {
		return unitMeter;
	}
	
}
