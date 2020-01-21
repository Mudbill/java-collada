package net.buttology.collada.internal;

import java.util.List;

public class CGeometry {

	private String id;
	private List<CSource> sources;
	private CVertices vertices;
	private CTriangles triangles;
	
	public CGeometry(String id, List<CSource> sources, CVertices vertices, CTriangles triangles) {
		this.id = id;
		this.sources = sources;
		this.vertices = vertices;
		this.triangles = triangles;
	}

	public List<CSource> getSources() {
		return sources;
	}

	public CVertices getVertices() {
		return vertices;
	}

	public CTriangles getTriangles() {
		return triangles;
	}

	public String getId() {
		return id;
	}
	
}
