package net.buttology.collada;

import java.io.Serializable;

import net.buttology.util.Vec3;

public class ColladaMesh implements Serializable {

	private static final long serialVersionUID = -4783093848099855230L;
	
	private int[] indices;
	private float[] vertices;
	private float[] texcoords;
	private float[] normals;
	private float[] tangents;
//	private float[] binormals;
	
	private Vec3		translation = new Vec3(0.0f, 0.0f, 0.0f);
	private float		rotationX = 0.0f;
	private float		rotationY = 0.0f;
	private float		rotationZ = 0.0f;
	private Vec3	scale = new Vec3(1.0f, 1.0f, 1.0f);
	
	private String materialFilePath;
	
	protected ColladaMesh(int[] indices, float[] vertices, float[] texcoords, float[] normals, float[] tangents, String materialFilePath) {
		this.indices = indices;
		this.vertices = vertices;
		this.texcoords = texcoords;
		this.normals = normals;
		this.tangents = tangents;
//		this.binormals = binormals;
		this.materialFilePath = materialFilePath;
	}
	
	public void setTranslation(float x, float y, float z) {
		this.translation.x = x;
		this.translation.y = y;
		this.translation.z = z;
	}
	
	public void setRotation(float x, float y, float z) {
		this.rotationX = x;
		this.rotationY = y;
		this.rotationZ = z;
	}
	
	public void setScale(float x, float y, float z) {
		this.scale.x = x;
		this.scale.y = y;
		this.scale.z = z;
	}
	
	public Vec3 getTranslation() {
		return this.translation;
	}
	
	public float getRotationX() {
		return this.rotationX;
	}
	
	public float getRotationY() {
		return this.rotationY;
	}
	
	public float getRotationZ() {
		return this.rotationZ;
	}
	
	public Vec3 getScale() {
		return this.scale;
	}

	public int[] getIndices() {
		return indices;
	}

	public float[] getPositions() {
		return vertices;
	}

	public float[] getTexcoords() {
		return texcoords;
	}

	public float[] getNormals() {
		return normals;
	}

	public float[] getTangents() {
		return tangents;
	}

//	public float[] getBinormals() {
//		return binormals;
//	}

	public String getFilePath() {
		return materialFilePath;
	}
	
}
