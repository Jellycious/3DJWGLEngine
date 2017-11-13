package Shitenka.game.objects.environment;

import Shitenka.engine.objects.GameItem;
import Shitenka.engine.rendering.world.models.Mesh;
import Shitenka.game.objects.Object;
import Shitenka.game.resources.ResourceManager;

public class FloorBlock extends Object{

    private double state;
    private float normalY;
    public FloorBlock(double initialState) throws Exception{
        super(getGameItem());
        this.state = initialState;
        normalY = getPosition().y;
    }

    public void setPosition(float x, float y, float z){
        super.setPosition(x, y, z);
        normalY = y;
    }

    public void update(){
//        state += 0.02;
//        Vector3f curPos = getPosition();
//        float newY = normalY + (float) Math.sin(state) * 0.5f;
//        super.setPosition(curPos.x, newY,curPos.z);
    }

    private static GameItem getGameItem() throws Exception{
        Mesh mesh = ResourceManager.getInstance().getTexturedMesh("cube.obj", "grassblock.png");
        GameItem item = new GameItem(mesh);
        return item;
    }
}
