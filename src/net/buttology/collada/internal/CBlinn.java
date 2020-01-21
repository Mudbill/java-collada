package net.buttology.collada.internal;


public class CBlinn {

//	private Vector4f 	emission;
//	private Vector4f 	ambient;
	private CTexture 	diffuse;
//	private Vector4f 	specular;
//	private float 		shininess;
//	private Vector4f 	reflectivity;
//	private Vector4f 	opacity;
//	private float 		transparency;

	public CBlinn(CTexture diffuse) {
		this.diffuse = diffuse;
	}

	public CTexture getDiffuse() {
		return diffuse;
	}
}
