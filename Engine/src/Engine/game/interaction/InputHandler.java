package Shitenka.game.interaction;

import Shitenka.engine.rendering.Window;
import org.joml.Matrix4f;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

public class InputHandler {

    private static final int MAX_CONTROLLERS = 4;
    private static final float MIN_AXIS_MOVEMENT = 0.1f;
    private FloatBuffer[] controllerAxes;
    private ByteBuffer[] controllerButtons;

    public InputHandler() {
        controllerAxes = new FloatBuffer[MAX_CONTROLLERS];
        controllerButtons = new ByteBuffer[MAX_CONTROLLERS];
    }
    public void handleInput(Window window){
        for (int i = 0; i < MAX_CONTROLLERS; i++){
            controllerAxes[i] = window.getControllerAxes(i);
            controllerButtons[i] = window.getControllerButtons(i);
        }
    }

    public static int getMaxControllers() {
        return MAX_CONTROLLERS;
    }

    public float getHorizontalLeftStick(int jid){
        return getFilteredAxisMovement(controllerAxes[0].get(0));
    }

    public float getVerticalLeftStick(int jid){
        return getFilteredAxisMovement(controllerAxes[0].get(1));
    }

    public float getHorizontalRightStick(int jid){
        return getFilteredAxisMovement(controllerAxes[0].get(2));
    }

    public float getVerticalRightStick(int jid){
        return getFilteredAxisMovement(controllerAxes[0].get(3));
    }

    public float getLeftTrigger(int jid) {return getFilteredAxisMovement(controllerAxes[0].get(4));}

    public float getRightTrigger(int jid) {return getFilteredAxisMovement(controllerAxes[0].get(5));}

    private float getFilteredAxisMovement(float axisOffset){
        float offset = 0.f;
        if (axisOffset > MIN_AXIS_MOVEMENT || axisOffset < -MIN_AXIS_MOVEMENT){
            offset = axisOffset;
        }
        return  offset;
    }
}
