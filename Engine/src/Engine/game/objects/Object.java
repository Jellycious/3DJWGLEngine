package Shitenka.game.objects;

import Shitenka.engine.objects.GameItem;
import org.joml.Vector3f;

public class Object {

    private final GameItem item;

    public Object(GameItem item){
        this.item = item;
    }

    public GameItem getItem() {
        return item;
    }

    public void update(){

    }

    public void setPosition(float x, float y, float z){
        getItem().setPosition(x,y,z);
    }

    public Vector3f getPosition(){
        return getItem().getPosition();
    }

    public void setRotation(float x, float y, float z){
        getItem().setRotation(x,y,z);
    }

    public Vector3f getRotation(){
        return getItem().getRotation();
    }

    public void cleanUp(){
        getItem().cleanUp();
    }

    public void init(){
    }
}
