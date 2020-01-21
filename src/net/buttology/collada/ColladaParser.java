package net.buttology.collada;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.buttology.collada.internal.CBlinn;
import net.buttology.collada.internal.CEffect;
import net.buttology.collada.internal.CGeometry;
import net.buttology.collada.internal.CImage;
import net.buttology.collada.internal.CInput;
import net.buttology.collada.internal.CInstanceController;
import net.buttology.collada.internal.CInstanceGeometry;
import net.buttology.collada.internal.CInstanceMaterial;
import net.buttology.collada.internal.CMaterial;
import net.buttology.collada.internal.CNewParam;
import net.buttology.collada.internal.CNode;
import net.buttology.collada.internal.CSource;
import net.buttology.collada.internal.CTriangles;
import net.buttology.collada.internal.CVertices;
import net.buttology.collada.internal.CVisualScene;
import net.buttology.util.Log;
import net.buttology.util.Vec3;

public class ColladaParser {

	/** Enable to print debug messages to console. */
	public static boolean printDebug = false;
	
	/** Enable to print the debug messages from the XMLParser section */
	public static boolean printXmlDebug = false;
	
	/** Flick this switch to disable or enable inversion of the Y axis on the texcoords. */
	public static boolean invertY = true;
	
	/** Enable this to always calculate tangents and ignore included tangents (if present) */
	public static boolean forceCalculateTangents = false;
	
//	private static final int ERROR_NONE = 0x0;
//	private static final int ERROR_1 = 0x1;
	
	/** No constructor */
	private ColladaParser() {}
	
	/**
	 * Loads a collada file and constructs model data out from it.
	 * @param fileName - The absolute or relative path to the file.
	 * @return 
	 */
	public static ColladaModel loadModel(String fileName) {
		return loadModel(new File(fileName));
	}
	
	/**
	 * Loads a collada file and constructs model data out from it.
	 * @param file - The file to load.
	 * @return 
	 */
	public static ColladaModel loadModel(File file) {
		if(printDebug) 
		{
			Log.info("--------------------------------------");
			Log.info("----- COLLADA PARSER DEBUG START -----");
		}
		
		long start = System.currentTimeMillis();
		
		ColladaDocument document = ColladaLoader.loadFile(file);
		ColladaModel model = new ColladaModel();
		
		constructModel(document, model);
		
		long end = System.currentTimeMillis();
		if(printDebug)
		{
			Log.info("Time to load: %s ms", end-start);
			Log.info("------ COLLADA PARSER DEBUG END ------");
			Log.info("--------------------------------------");
		}
		return model;
	}
	
	/**
	 * Start processing data from the loaded document into an actual model.
	 * @param document
	 * @param model
	 */
	private static void constructModel(ColladaDocument document, ColladaModel model)
	{
		if(printDebug) Log.info("---- BEGIN CONSTRUCTION ----");
	
		boolean hasTangents = false;
		
		for(CGeometry cGeometry : document.getGeometries())
		{
			String id = cGeometry.getId();
			if(printDebug) Log.info("Creating mesh for geometry '%s'", id);
			CTriangles cTriangles = cGeometry.getTriangles();
			CVertices cVertices = cGeometry.getVertices();
			
			int[] pArray = cTriangles.getPrimitiveArray();
			
			CSource cPositionSource = null;
			CSource cTexcoordSource = null;
			CSource cNormalSource = null;
			CSource cTangentSource = null;
//			CSource cBinormalSource = null;
			
			CInput cVertexInput 	= cTriangles.getInput("VERTEX");
			CInput cTexcoordInput 	= cTriangles.getInput("TEXCOORD");
			CInput cNormalInput 	= cTriangles.getInput("NORMAL");
			CInput cTangentInput 	= cTriangles.getInput("TANGENT");
//			CInput cBinormalInput 	= cTriangles.getInput("BINORMAL");
			CInput cPositionInput 	= cVertices.getInput("POSITION");
			
			if(isNull(cVertexInput, true)) return;
			
			cPositionInput.updateOffsetAndSet(cVertexInput.getOffset(), cVertexInput.getSet());
			if(isNull(cTexcoordInput)) cTexcoordInput = cVertices.getInput("TEXCOORD").updateOffsetAndSet(cVertexInput.getOffset(), cVertexInput.getSet());
			if(isNull(cNormalInput)) cNormalInput = cVertices.getInput("NORMAL").updateOffsetAndSet(cVertexInput.getOffset(), cVertexInput.getSet());
			
			boolean updateOffsetAndSet = false;
			if(isNull(cTangentInput))// && isNull(cBinormalInput)) 
				updateOffsetAndSet = true;
			
			if(isNull(cTangentInput)) cTangentInput = cVertices.getInput("TANGENT");
//			if(isNull(cBinormalInput)) cBinormalInput = cVertices.getInput("BINORMAL");
			
			if(cTangentInput != null)// && cBinormalInput != null)
			{
				hasTangents = !forceCalculateTangents;
				if(printDebug) Log.info("Model has included tangents. Use = "+hasTangents);
				
				if(updateOffsetAndSet)
				{
					cTangentInput.updateOffsetAndSet(cVertexInput.getOffset(), cVertexInput.getSet());
//					cBinormalInput.updateOffsetAndSet(cVertexInput.getOffset(), cVertexInput.getSet());
				}
			}
			else
			{
				if(printDebug) Log.info("Model has no included tangents.");
			}
			
			if(printDebug) Log.info("Getting float arrays.");
			
			for(CSource cSource : cGeometry.getSources())
			{
				if(cSource.getId().equals(cPositionInput.getSource())) cPositionSource = cSource;
				if(cSource.getId().equals(cTexcoordInput.getSource())) cTexcoordSource = cSource;
				if(cSource.getId().equals(cNormalInput.getSource())) cNormalSource = cSource;
				if(hasTangents)
				{
					if(cSource.getId().equals(cTangentInput.getSource())) cTangentSource = cSource;
//					if(cSource.getId().equals(cBinormalInput.getSource())) cBinormalSource = cSource;
				}
			}
			
			List<Integer> positionIndices = new ArrayList<Integer>();
			List<Integer> texcoordIndices = new ArrayList<Integer>();
			List<Integer> normalIndices = new ArrayList<Integer>();
			List<Integer> tangentIndices = new ArrayList<Integer>();
//			List<Integer> binormalIndices = new ArrayList<Integer>();
			
			if(printDebug) Log.info("Assembling unique index lists.");
			int separator = 0;
			int highestOffset = getHighestOffset(cTriangles.getInputs());
			for(int index : pArray)
			{
				int offset = separator++ % highestOffset;
				
				if(offset == cPositionInput.getOffset()) positionIndices.add(index);
				if(offset == cTexcoordInput.getOffset()) texcoordIndices.add(index);
				if(offset == cNormalInput.getOffset()) normalIndices.add(index);
				
				if(hasTangents)
				{
					if(offset == cTangentInput.getOffset()) tangentIndices.add(index);
//					if(offset == cBinormalInput.getOffset()) binormalIndices.add(index);
				}
			}
						
			if(printDebug) Log.info("Converting sources to vector lists.");
			List<Vec3> rawPositions = convertSourceToVectorList(cPositionSource);
			List<Vec3> rawTexcoords = convertSourceToVectorList(cTexcoordSource);
			List<Vec3> rawNormals = convertSourceToVectorList(cNormalSource);
			List<Vec3> rawTangents = null;
//			List<Vector3f> rawBinormals = null;
			if(hasTangents)
			{
				rawTangents = convertSourceToVectorList(cTangentSource);
//				rawBinormals = convertSourceToVectorList(cBinormalSource);
			}
			else
			{
				rawTangents = new ArrayList<Vec3>();
//				rawBinormals = new ArrayList<Vector3f>();
			}
			
			if(printDebug) Log.info("Constructing vectors from index lists.");
			List<Vec3> inPositions = constructVectorListFromIndices(positionIndices, rawPositions);
			List<Vec3> inTexcoords = constructVectorListFromIndices(texcoordIndices, rawTexcoords);
			List<Vec3> inNormals = constructVectorListFromIndices(normalIndices, rawNormals);
			List<Vec3> inTangents;
//			List<Vec3> inBinormals = null;
			if(hasTangents)
			{
				inTangents = constructVectorListFromIndices(tangentIndices, rawTangents);
//				inBinormals = constructVectorListFromIndices(binormalIndices, rawBinormals);
			}
			else
			{
				inTangents = new ArrayList<Vec3>();
				
				if(printDebug) Log.info_nocr("Calculating tangents...");
				long ms = System.currentTimeMillis();
				
				for(int i = 0; i < inPositions.size(); i+=3)
				{
					Vec3 pos1 = inPositions.get(i);
					Vec3 pos2 = inPositions.get(i+1);
					Vec3 pos3 = inPositions.get(i+2);
					
					Vec3 tex1 = inTexcoords.get(i);
					Vec3 tex2 = inTexcoords.get(i+1);
					Vec3 tex3 = inTexcoords.get(i+2);
					
					calculateTangents(pos1, pos2, pos3, tex1, tex2, tex3, inTangents);
				}
				
				if(printDebug) Log.info_nof(" (%d ms)%n", System.currentTimeMillis() - ms);
			}
			
			List<Integer> outIndices = new ArrayList<Integer>();
			List<Vec3> outPositions = new ArrayList<Vec3>();
			List<Vec3> outTexcoords = new ArrayList<Vec3>();
			List<Vec3> outNormals = new ArrayList<Vec3>();
			List<Vec3> outTangents = new ArrayList<Vec3>();
//			List<Vec3> outBinormals = new ArrayList<Vec3>();
			
			indexVBO(inPositions, inTexcoords, inNormals, outIndices, outPositions, outTexcoords, outNormals, inTangents, outTangents);
						
			if(printDebug) Log.info("Initializing final arrays.");
			float[] positions = new float[outPositions.size() * 3];
			float[] texcoords = new float[outPositions.size() * 2];
			float[] normals = new float[outPositions.size() * 3];
			float[] tangents = new float[outTangents.size() * 3];
//			float[] binormals = new float[outBinormals.size() * 3];
			int[] indices = new int[outIndices.size()];
			
			for(int i = 0; i < outIndices.size(); i++)
				indices[i] = outIndices.get(i);
			
			if(printDebug) Log.info("Converting vector lists to primitive arrays.");
			positions = getPrimitiveArrayFromVectorList(positions, outPositions, 3, false);
			texcoords = getPrimitiveArrayFromVectorList(texcoords, outTexcoords, 2, invertY);
			normals = getPrimitiveArrayFromVectorList(normals, outNormals, 3, false);
			tangents = getPrimitiveArrayFromVectorList(tangents, outTangents, 3, false);
			
//			binormals = getPrimitiveArrayFromVectorList(binormals, outBinormals, 3, false);
			String material = cTriangles.getMaterial();
			String filePath = getTextureFilePath(document, material);
			
//			if(printDebug)
//			{
//				Log.info("IDX: "+Arrays.toString(indices));
//				Log.info("POS: "+Arrays.toString(positions));
//				Log.info("TEX: "+Arrays.toString(texcoords));
//				Log.info("NOR: "+Arrays.toString(normals));
//				Log.info("TAN: "+Arrays.toString(tangents));
//			}
			
			if(printDebug) Log.info("Adding mesh with %d indices, %d position floats, %d texcoord floats, %d normal floats, %d tangent floats, filePath='%s'", indices.length, positions.length, texcoords.length, normals.length, tangents.length, filePath);
			ColladaMesh mesh = new ColladaMesh(indices, positions, texcoords, normals, tangents, filePath);
			model.addMesh(id, mesh);
		}
		if(printDebug) Log.info("---- CONSTRUCTION COMPLETE ----");
	}
	
	/**
	 * Perform the steps necessary to fetch the final texture source file path from the COLLADA document.
	 * @param document - The COLLADA document.
	 * @param material - The Geometry material reference to use.
	 * @return the path OR empty if not exists
	 */
	private static String getTextureFilePath(ColladaDocument document, String material)
	{
		String file = "";
		
		for(CVisualScene cVisualScene : document.getVisualScenes())
		{
			for(CNode cNode : cVisualScene.getNodes())
			{
				file = processNode(document, cNode, material);
				if(!file.isEmpty()) break;
			}
			if(!file.isEmpty()) break;
		}
		
		if(printDebug && file.isEmpty()) Log.error("File not found in visual scene! Material='%s'", material);
		return file;
	}
	
	private static String processNode(ColladaDocument document, CNode node, String material)
	{
		if(node == null)
		{
			if(printDebug) Log.error("Encountered a null node! Material='%s'", material);
			return "";
		}
		
		CInstanceController cInstanceController = node.getInstanceController();
		if(cInstanceController != null)
		{
			CInstanceMaterial cInstanceMaterial = cInstanceController.getInstanceMaterial();
			if(cInstanceMaterial == null)
			{
				if(printDebug) Log.error("instance_geometry is null! Returning empty texture string.");
				return "";
			}
			
			if(cInstanceMaterial.getSymbol().equals(material)) 
			{
				if(printDebug) Log.info("Found Instance Material: '%s'", cInstanceMaterial.getTarget());
				return processMaterial(document, cInstanceMaterial.getTarget());
			}
		}
		
		CInstanceGeometry cInstanceGeometry = node.getInstanceGeometry();
		if(cInstanceGeometry != null)
		{
			CInstanceMaterial cInstanceMaterial = cInstanceGeometry.getInstanceMaterial();
			if(cInstanceMaterial == null)
			{
				if(printDebug) Log.error("instance_geometry is null! Returning empty texture string.");
				return "";
			}
			
			if(cInstanceMaterial.getSymbol().equals(material)) 
			{
				if(printDebug) Log.info("Found Instance Material: '%s'", cInstanceMaterial.getTarget());
				return processMaterial(document, cInstanceMaterial.getTarget());
			}
		}
		
		for(CNode cNode : node.getNodes())
		{
			String s = processNode(document, cNode, material);
			if(!s.isEmpty()) return s;
		}

		return "";
	}
	
	private static String processMaterial(ColladaDocument document, String target)
	{
		if(target.startsWith("#")) target = target.substring(1);		
		if(printDebug) Log.info("Trying material '%s'", target);
		
		CMaterial cMaterial = document.getMaterial(target);
		if(isNull(cMaterial)) return "";
		
		String url = cMaterial.getInstanceEffect().getUrl();
		if(url.startsWith("#")) url = url.substring(1);
		
		return processEffect(document, url);
	}

	private static String processEffect(ColladaDocument document, String url)
	{
		if(printDebug) Log.info("Trying effect '%s'", url);
		
		CEffect cEffect = document.getEffect(url);
		CBlinn cBlinn = cEffect.getTechnique().getBlinn();
		
		String sTexture = cBlinn.getDiffuse().getTexture();
		
		CNewParam cNewParam = cEffect.getNewparam(sTexture);
		String sSampler2DSource = cNewParam.getSampler2D().getSource();
		
		cNewParam = cEffect.getNewparam(sSampler2DSource);
		String sInitFrom = cNewParam.getSampler2D().getSource();
		
		return processImage(document, sInitFrom);
	}

	private static String processImage(ColladaDocument document, String sInitFrom)
	{
		if(printDebug) Log.info("Trying image '%s'", sInitFrom);
		CImage cImage = document.getImage(sInitFrom);
		if(printDebug) Log.info("Returning file '%s'", cImage.getInitFrom());
		return cImage.getInitFrom();
	}

	/**
	 * Convert a vector list into a primitive array.
	 * @param primitive
	 * @param list
	 * @param size
	 * @return
	 */
	private static float[] getPrimitiveArrayFromVectorList(float[] primitive, List<Vec3> list, int size, boolean invertY)
	{
		int indexPointer = 0;
		for(Vec3 vector : list)
		{
			primitive[indexPointer++] = vector.x;
			primitive[indexPointer++] = (invertY ? 1.0f - vector.y : vector.y);
			if(size == 3) primitive[indexPointer++] = vector.z;
		}
		return primitive;
	}
	
	/**
	 * Calculates the tangents for this part of the mesh.
	 * @param pos1 - Vertex 1 position
	 * @param pos2 - Vertex 2 position
	 * @param pos3 - Vertex 3 position
	 * @param tex1 - Vertex 1 UV co-ordinates
	 * @param tex2 - Vertex 2 UV co-ordinates
	 * @param tex3 - Vertex 3 UV co-ordinates
	 */
	private static void calculateTangents(Vec3 pos1, Vec3 pos2, Vec3 pos3, Vec3 tex1, Vec3 tex2, Vec3 tex3, List<Vec3> out_tangents)//, List<Vector3f> out_binormals)
	{
		Vec3 edge1 = Vec3.sub(pos2, pos1);
		Vec3 edge2 = Vec3.sub(pos3, pos1);
		Vec3 deltaUV1 = Vec3.sub(tex2, tex1);
		Vec3 deltaUV2 = Vec3.sub(tex3, tex1);
		
		float f = 1.0f / (deltaUV1.x * deltaUV2.y - deltaUV2.x * deltaUV1.y);
		
		Vec3 tangent = new Vec3();
		tangent.x = f * (deltaUV2.y * edge1.x - deltaUV1.y * edge2.x);
		tangent.y = f * (deltaUV2.y * edge1.y - deltaUV1.y * edge2.y);
		tangent.z = f * (deltaUV2.y * edge1.z - deltaUV1.y * edge2.z);
		tangent = Vec3.normalize(tangent);
		
//		Vector3f binormal = new Vector3f();
//		binormal.x = f * (-deltaUV2.x * edge1.x + deltaUV1.x * edge2.x);
//		binormal.y = f * (-deltaUV2.x * edge1.y + deltaUV1.x * edge2.y);
//		binormal.z = f * (-deltaUV2.x * edge1.z + deltaUV1.x * edge2.z);
//		binormal = Utils.normalize(binormal);
		
		out_tangents.add(tangent);
		out_tangents.add(tangent);
		out_tangents.add(tangent);
//		out_binormals.add(binormal);
	}
		
	/**
	 * Go through all vertices and compare them for similar ones. If it finds one, reuse it in the index list, else add it.
	 * @param in_vertices
	 * @param in_textures
	 * @param in_normals
	 * @param out_indices
	 * @param out_vertices
	 * @param out_textures
	 * @param out_normals
	 */
	private static void indexVBO(List<Vec3> in_vertices, List<Vec3> in_textures, List<Vec3> in_normals, List<Integer> out_indices, List<Vec3> out_vertices, List<Vec3> out_textures, List<Vec3> out_normals, List<Vec3> in_tangents, List<Vec3> out_tangents)//, List<Vector3f> in_binormals, List<Vector3f> out_binormals) 
	{
		if(printDebug) Log.info("Starting indexing of the vector lists.");
		
		for(int i = 0; i < in_vertices.size(); i++)
		{
			int index = getSimilarVertexIndex(in_vertices.get(i), in_textures.get(i), in_normals.get(i), out_vertices, out_textures, out_normals);
			if(index != -1) out_indices.add(index);
			else 
			{
				out_vertices.add(in_vertices.get(i));
				out_textures.add(in_textures.get(i));
				out_normals.add(in_normals.get(i));
				if(in_tangents.size() > 0) out_tangents.add(in_tangents.get(i));
//				if(in_binormals.size() > 0) out_binormals.add(in_binormals.get(i));
				out_indices.add(out_vertices.size() - 1);
			}
		}
	}
	
	/**
	 * Check if all neighboring vertices/UVs/normals are considered equal. If they are, return the index.
	 * @param in_vertex
	 * @param in_texture
	 * @param in_normal
	 * @param out_vertices
	 * @param out_textures
	 * @param out_normals
	 * @return
	 */
	private static int getSimilarVertexIndex(Vec3 in_vertex, Vec3 in_texture, Vec3 in_normal, List<Vec3> out_vertices, List<Vec3> out_textures, List<Vec3> out_normals)
	{
		for(int i = 0; i < out_vertices.size(); i++) 
		{
			if( isNear(in_vertex.x 	, out_vertices.get(i).x) &&
				isNear(in_vertex.y	, out_vertices.get(i).y) &&
				isNear(in_vertex.z 	, out_vertices.get(i).z) &&
				isNear(in_texture.x , out_textures.get(i).x) &&
				isNear(in_texture.y , out_textures.get(i).y) &&
				isNear(in_texture.z , out_textures.get(i).z) &&
				isNear(in_normal.x 	, out_normals .get(i).x) &&
				isNear(in_normal.y 	, out_normals .get(i).y) &&
				isNear(in_normal.z 	, out_normals .get(i).z))
					return i;
		}
		return -1;
	}
	
	/** Returns true if v1 can be considered equal to v2 */
	private static boolean isNear(float v1, float v2) { return Math.abs(v1 - v2) < 0.0001f; }
	
	/**
	 * Constructs a new 3-float vector list from the linear list based on the indices.
	 * @param indices
	 * @param rawList
	 * @return
	 */
	private static List<Vec3> constructVectorListFromIndices(List<Integer> indices, List<Vec3> rawList)
	{
		List<Vec3> list = new ArrayList<Vec3>();
		for(int i = 0; i < indices.size(); i++)
		{
			//Log.info("Adding: Index: %d, Value: %d, Vec3: %s", i, indices.get(i), rawList.get(indices.get(i)));
			list.add(rawList.get(indices.get(i)));
		}
		return list;
	}
	
	/**
	 * Takes a COLLADA source object and converts it to a list of 3-float vectors. Supports 2- and 3-float sets.
	 * @param source
	 * @return
	 */
	private static List<Vec3> convertSourceToVectorList(CSource source)
	{
		List<Vec3> list = new ArrayList<Vec3>();
		float[] data = source.getFloatArray();
		int stride = source.getTechniqueCommon().getStride();
		for(int i = 0; i < data.length; i += stride)
			list.add(new Vec3(data[i], data[i+1], stride == 3 ? data[i+2] : 0));
		return list;
	}
	
	/**
	 * Get the highest offset value from all the given input tags.
	 * @param inputs
	 * @return
	 */
	private static int getHighestOffset(Collection<CInput> inputs)
	{
		int highest = 0;
		for(CInput cInput : inputs)
		{
			int offset = cInput.getOffset();
			if(offset >= highest) highest = offset + 1;
		}
		return highest;
	}

	/**
	 * Check if this object is null.
	 * @param o
	 * @return
	 */
	private static boolean isNull(Object o) { return isNull(o, false); }
	private static boolean isNull(Object o, boolean b) 
	{ 
		if(o == null)
		{
			if(b) if(printDebug) Log.error("Got an unwanted null value!");
			return true;
		}
		return false; 
	}
	
	//------------------------------------
	
//	public static void main(String[] a) {
//		loadModel("resources/servant_grunt.dae");
//	}
	
}
