package net.buttology.collada.internal;

public class CTexture {

	private String texture;
	private String texcoord;
	
	public CTexture(String texture, String texcoord) {
		this.texture = texture;
		this.texcoord = texcoord;
	}

	public String getTexture() {
		return texture;
	}

	public String getTexcoord() {
		return texcoord;
	}
	
}
