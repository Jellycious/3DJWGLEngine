package Shitenka.game.rendering;

import Shitenka.engine.objects.GameItem;
import Shitenka.engine.rendering.shading.lighting.PointLight;
import Shitenka.engine.rendering.world.Camera;
import Shitenka.engine.rendering.world.TransformationMatrices;
import Shitenka.engine.rendering.Window;
import Shitenka.engine.rendering.shading.ShaderProgram;
import Shitenka.engine.rendering.world.models.Mesh;
import Shitenka.engine.util.Utils;
import Shitenka.game.objects.Object;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.system.windows.POINTL;

import java.awt.*;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;


public class Renderer {

    private ShaderProgram shaderProgam;

    private final TransformationMatrices transformation;


    private static final float FOV = (float) Math.toRadians(90.0f);
    private static final float Z_NEAR = 0.01f;
    private static final float Z_FAR = 1000.0f;

    private Window window;

    private Camera camera;

    private float specularPower;
    public Renderer() {
        transformation = new TransformationMatrices();
        specularPower = 10f;
    }

    public void init(Window window) throws Exception {
        this.window = window;

        shaderProgam = new ShaderProgram();
        shaderProgam.createVertexShader(Utils.loadResource("/resources/educational/vertexshaders/light_shader.vs"));
        shaderProgam.createFragmentShader(Utils.loadResource("/resources/educational/framentshaders/phong_shader.fs"));
        shaderProgam.link();
        shaderProgam.createUniform("projectionMatrix");
        shaderProgam.createUniform("modelViewMatrix");
        shaderProgam.createUniform("texture_sampler");

        shaderProgam.createMaterialUniform("material");

        shaderProgam.createUniform("specularPower");
        shaderProgam.createUniform("ambientLight");
        shaderProgam.createPointLightUniform("pointLight");
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void render(List<Object> objects, Camera camera, Vector3f ambientLight, PointLight pointLight) {
        clear();
        if ( window.isResized() ) {
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(false);
        }

        shaderProgam.bind();
        //update projection Matrix;
        Matrix4f projectionMatrix = transformation.getProjectionMatrix(FOV, window.getWidth(), window.getHeight(), Z_NEAR, Z_FAR);
        shaderProgam.setUniform("projectionMatrix", projectionMatrix);
        //update view Matrix;
        Matrix4f viewMatrix = transformation.getViewMatrix(camera);
        //set texture sampler
        shaderProgam.setUniform("texture_sampler", 0);
        //update light Uniforms
        shaderProgam.setUniform("ambientLight", ambientLight);
        shaderProgam.setUniform("specularPower", specularPower);
        //get a copy of the light object and transform its position to view coordinates
        PointLight currPointLight = new PointLight(pointLight);
        Vector3f lightPos = currPointLight.getPosition();
        Vector4f aux = new Vector4f(lightPos, 1);
        aux.mul(viewMatrix);
        lightPos.x = aux.x;
        lightPos.y = aux.y;
        lightPos.z = aux.z;
        shaderProgam.setUniform("pointLight", currPointLight);

        for (Object object : objects){
            GameItem item = object.getItem();
            Mesh itemMesh = item.getMesh();
            Matrix4f modelViewMatrix = transformation.getModelViewMatrix(item, viewMatrix);
            shaderProgam.setUniform("modelViewMatrix", modelViewMatrix);

            shaderProgam.setUniform("material", object.getItem().getMesh().getMaterial());
            itemMesh.render();

        }
        shaderProgam.unbind();
    }


    public void cleanup() {
        if (shaderProgam != null) {
            shaderProgam.cleanup();
        }

    }
}