package net.buttology.collada;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.buttology.collada.internal.CAsset;
import net.buttology.collada.internal.CEffect;
import net.buttology.collada.internal.CGeometry;
import net.buttology.collada.internal.CImage;
import net.buttology.collada.internal.CMaterial;
import net.buttology.collada.internal.CScene;
import net.buttology.collada.internal.CVisualScene;

public class ColladaDocument {

	private Map<String, CImage> 		images = new HashMap<String, CImage>();
	private Map<String, CMaterial> 		materials = new HashMap<String, CMaterial>();
	private Map<String, CEffect> 		effects = new HashMap<String, CEffect>();
	private Map<String, CGeometry> 		geometries = new HashMap<String, CGeometry>();
	private Map<String, CVisualScene>	visualScenes = new HashMap<String, CVisualScene>();
	private CScene scene;
	private CAsset asset;
	
	protected ColladaDocument() {}
	
	protected void addColladaImage(CImage cImage) {
		if(cImage == null) return;
		images.put(cImage.getId(), cImage);
	}
	
	protected void addColladaMaterial(CMaterial cMaterial) {
		if(cMaterial == null) return;
		materials.put(cMaterial.getId(), cMaterial);
	}
	
	protected void addColladaEffect(CEffect cEffect) {
		if(cEffect == null) return;
		effects.put(cEffect.getId(), cEffect);
	}
	
	protected void addColladaGeometry(CGeometry cGeometry) {
		if(cGeometry == null) return;
		geometries.put(cGeometry.getId(), cGeometry);
	}
	
	protected void addColladaVisualScene(CVisualScene cVisualScene) {
		if(cVisualScene == null) return;
		visualScenes.put(cVisualScene.getId(), cVisualScene);
	}
	
	protected void setColladaScene(CScene cScene) {
		this.scene = cScene;
	}
	
	protected void setColladaAsset(CAsset cAsset) {
		this.asset = cAsset;
	}
	
	//-----------------------------
	
	protected Collection<CImage> getImages() {
		return images.values();
	}
	
	protected CImage getImage(String id) {
		return images.get(id);
	}

	protected Collection<CMaterial> getMaterials() {
		return materials.values();
	}
	
	protected CMaterial getMaterial(String id) {
		return materials.get(id);
	}

	protected Collection<CEffect> getEffects() {
		return effects.values();
	}

	protected CEffect getEffect(String id) {
		return effects.get(id);
	}
	
	protected Collection<CGeometry> getGeometries() {
		return geometries.values();
	}

	protected CGeometry getGeometry(String id) {
		return geometries.get(id);
	}
	
	protected Collection<CVisualScene> getVisualScenes() {
		return visualScenes.values();
	}
	
	protected CVisualScene getVisualScene(String id) {
		return visualScenes.get(id);
	}

	protected CScene getScene() {
		return scene;
	}
	
	protected CAsset getAsset() {
		return asset;
	}
	
}
