package Engine.game;

import Engine.engine.IGameLogic;
import Engine.engine.objects.GameItem;
import Engine.engine.rendering.Window;
import Engine.engine.rendering.shading.lighting.Material;
import Engine.engine.rendering.shading.lighting.PointLight;
import Engine.engine.rendering.world.Camera;
import Engine.engine.rendering.world.models.Mesh;
import Engine.engine.rendering.world.models.OBJLoader;
import Engine.engine.rendering.world.models.Texture;
import Engine.game.interaction.InputHandler;
import Engine.game.objects.Object;
import Engine.game.rendering.Renderer;
import Engine.game.resources.ResourceManager;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

public class Engine implements IGameLogic{

    private final Renderer renderer;
    private final InputHandler inputHandler;
    private final List<Object> objectList;
    private final ResourceManager resourceManager;

    private final Camera camera;

    private Vector3f ambientLight;
    private PointLight pointLight;

    private int frames = 0;
    double lastLoop = 0;
    private int curFps = 0;

    public Engine(){
        renderer = new Renderer();
        inputHandler = new InputHandler();
        objectList = new ArrayList<>();
        resourceManager = ResourceManager.getInstance();
        Vector3f camPos = new Vector3f(0, 2.5f, 5);
        Vector3f camRot = new Vector3f(0, 0, 0);
        Camera camera = new Camera(camPos, camRot);
        this.camera = camera;
        lastLoop = System.nanoTime();
    }


    @Override
    public void init(Window window) throws Exception {
        renderer.init(window);
        //ambientLight
        ambientLight = new Vector3f(0.3f, 0.3f, 0.3f);
        //lightpoint
        Vector3f lightColour = new Vector3f(1,1,1);
        Vector3f lightPosition = new Vector3f(0,2, .5f);
        float lightIntensity = 1.0f;
        pointLight = new PointLight(lightColour, lightPosition, lightIntensity);
        PointLight.Attenuation att = new PointLight.Attenuation(0,0.0f,1f);
        pointLight.setAttenuation(att);
        //test environment
        createTestEnvironment();
    }

    @Override
    public void input(Window window) {
        inputHandler.handleInput(window);
        //camera movement

        float sensitivity = 1.5f;
        float movementSpeed = 0.02f;

        float lHorizontal = inputHandler.getHorizontalLeftStick(0);
        float lVertical = inputHandler.getVerticalLeftStick(0);
        float rVertical = inputHandler.getVerticalRightStick(0);
        float rHorizontal = inputHandler.getHorizontalRightStick(0);
        float rTrigger = inputHandler.getRightTrigger(0) + 1.f;
        float lTrigger = inputHandler.getLeftTrigger(0) + 1.f;
        float verticalMovement = rTrigger - lTrigger;

        camera.movePosition(lHorizontal * movementSpeed,verticalMovement * movementSpeed,-lVertical * movementSpeed);
        camera.moveRotation(-rVertical * sensitivity, rHorizontal * sensitivity,0);
    }

    @Override
    public void update(float interval) {
            for (Object object : objectList){
                object.update();
            }

        }

    public void cleanup(){
        renderer.cleanup();
        for (Object object : objectList){
                object.cleanUp();
        }
    }

    @Override
    public void render(Window window) {
        renderer.render(objectList, camera,ambientLight, pointLight);
        //fps counter
        frames++;
        if (System.nanoTime() - lastLoop > 1_000_000_000){
            curFps = frames;
            lastLoop = System.nanoTime();
            frames = 0;
        }
    }

    public void createTestEnvironment(){
        try {
            Mesh mesh = OBJLoader.loadMesh("/resources/models/cube.obj");
            Texture texture = new Texture("/textures/abstract.png");
            Material mat = new Material(texture);
            mesh.setMaterial(mat);
            GameItem item;
            Object object;
            for (int i = 0; i < 7; i++){
                item = new GameItem(mesh);
                item.setScale(.5f);
                object = new Object(item);
                object.setPosition(-3 +  i, 0,0);
                objectList.add(object);
            }


            mesh = OBJLoader.loadMesh("/resources/models/text.obj");
            mat = new Material(new Vector4f(1, 0.85f, 0f,0f),0f);
            mesh.setMaterial(mat);
            item = new GameItem(mesh);
            object = new Object(item);
            object.setPosition(0, .75f,0);
            objectList.add(object);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}