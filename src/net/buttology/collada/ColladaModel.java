package net.buttology.collada;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ColladaModel implements Serializable {

	private static final long serialVersionUID = 3189022524891558320L;
	
	private Map<String, ColladaMesh> meshes;
	
	public ColladaModel() {
		meshes = new HashMap<String, ColladaMesh>();
	}
	
	protected void addMesh(String id, ColladaMesh mesh) {
		meshes.put(id, mesh);
	}
	
	protected ColladaMesh getMesh(String id) {
		return meshes.get(id);
	}
	
	public ColladaMesh[] getMeshes() {
		ColladaMesh[] cm = new ColladaMesh[meshes.size()];
		int idx = 0;
		for(ColladaMesh m : meshes.values()) cm[idx++] = m;
		return cm;
	}
	
}
