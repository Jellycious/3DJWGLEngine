package Shitenka.engine.util;

//class with utilities concerning color
public class Color {

    public static float[] randomColor(){
        float r = (float) Math.random();
        float g = (float) Math.random();
        float b =  (float) Math.random();
        float[] color = {r, g, b};
        return color;
    }

}
