package Shitenka.game.resources;

import Shitenka.engine.rendering.world.models.Mesh;
import Shitenka.engine.rendering.world.models.OBJLoader;
import Shitenka.engine.rendering.world.models.Texture;

import java.util.HashMap;
import java.util.Map;

public class ResourceManager {
    private static ResourceManager ourInstance = new ResourceManager();

    public static ResourceManager getInstance() {
        return ourInstance;
    }

    private Map<String, Mesh> meshMap;
    private Map<String, Texture> textureMap;
    private static final String MESH_PATH = "/resources/models/";
    private static final String TEXTURE_PATH = "/textures/";

    private ResourceManager() {
        meshMap = new HashMap<>();
        textureMap = new HashMap<>();
    }

    public Mesh getMesh(String filePath) throws Exception{
        if (!meshMap.containsKey(filePath)){
            loadMesh(filePath);
        }
        return meshMap.get(filePath);
    }

    public Mesh getTexturedMesh(String filePath, String textureFilePath) throws Exception{
        if (!meshMap.containsKey(filePath+textureFilePath)){
            loadTexturedMesh(filePath, textureFilePath);
        }
        return meshMap.get(filePath + textureFilePath);
    }

    private void loadMesh(String filePath) throws Exception{
            Mesh mesh = OBJLoader.loadMesh(MESH_PATH + filePath);
            meshMap.put(filePath, mesh);
    }

    private void loadTexturedMesh(String filePath, String textureFilePath) throws Exception {
        Mesh mesh = OBJLoader.loadMesh(MESH_PATH + filePath);
        Texture texture;
        if (!textureMap.containsKey(textureFilePath)){
            texture = new Texture(TEXTURE_PATH + textureFilePath);
            textureMap.put(textureFilePath, texture);
        }else {
            texture = textureMap.get(textureFilePath);
        }
        //mesh.setTexture(texture);
        meshMap.put(filePath+textureFilePath, mesh);
    }
}
