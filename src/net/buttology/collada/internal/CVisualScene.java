package net.buttology.collada.internal;

import java.util.List;

public class CVisualScene {

	private String id;
	private String name;
	private List<CNode> nodes;
	
	public CVisualScene(String id, String name, List<CNode> nodes) {
		this.id = id;
		this.name = name;
		this.nodes = nodes;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<CNode> getNodes() {
		return nodes;
	}
	
}
