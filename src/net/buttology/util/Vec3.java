package net.buttology.util;

public class Vec3 {

	public float x = 0.0f, y = 0.0f, z = 0.0f;
	
	public Vec3() {}
	
	public Vec3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public static Vec3 sub(Vec3 a, Vec3 b) {
		return new Vec3(a.x - b.x, a.y - b.y, a.z - b.z);
	}
	
	public static Vec3 normalize(Vec3 vector) {
		float length_of_vector = length(vector);
		return new Vec3(vector.x / length_of_vector, vector.y / length_of_vector, vector.z / length_of_vector);
	}
	
	public static float length(Vec3 vector) {
		return (float) Math.sqrt((vector.x * vector.x) + (vector.y * vector.y) + (vector.z * vector.z));
	}
}
