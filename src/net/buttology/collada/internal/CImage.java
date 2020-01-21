package net.buttology.collada.internal;

public class CImage {
	
	private String id;
	private String name;
	private String format;
	private int height;
	private int width;
	private int depth;
	
	private String init_from;
	
	public CImage() {}

	public CImage(String id, String name, String format, int height, int width, int depth) {
		this.id = id;
		this.name = name;
		this.format = format;
		this.height = height;
		this.width = width;
		this.depth = depth;
	}
	
	public String getInitFrom() {
		return init_from;
	}

	public void setInit_from(String init_from) {
		this.init_from = init_from;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}
	
}
