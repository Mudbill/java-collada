package net.buttology.collada.internal;

import java.util.List;

import net.buttology.util.Vec3;
import net.buttology.util.Vec4;

public class CNode {

	private String id;
	private String name;
	private String sid;
	private String type;
	private Vec3 translate;
	private List<Vec4> rotate;
	private Vec3 scale;
	private List<CNode> children;
	private CInstanceController instanceController;
	private CInstanceGeometry instanceGeometry;
	
	public CNode(String id, String name, String sid, String type, 
			Vec3 translate, List<Vec4> rotate, Vec3 scale, 
			List<CNode> children, CInstanceController cInstanceController, CInstanceGeometry cInstanceGeometry) {
		this.id = id;
		this.name = name;
		this.sid = sid;
		this.type = type;
		this.translate = translate;
		this.rotate = rotate;
		this.scale = scale;
		this.children = children;
		this.instanceController = cInstanceController;
		this.instanceGeometry = cInstanceGeometry;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getSid() {
		return sid;
	}

	public String getType() {
		return type;
	}

	public Vec3 getTranslate() {
		return translate;
	}

	public List<Vec4> getRotate() {
		return rotate;
	}

	public Vec3 getScale() {
		return scale;
	}

	public List<CNode> getNodes() {
		return children;
	}
	
	public CInstanceController getInstanceController() {
		return instanceController;
	}
	
	public CInstanceGeometry getInstanceGeometry() {
		return instanceGeometry;
	}
	
}
