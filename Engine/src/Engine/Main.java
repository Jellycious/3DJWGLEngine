package Engine;


import Engine.engine.GameEngine;
import Engine.engine.IGameLogic;
import Engine.game.Engine;

public class Main {

    public static void main(String[] args) {
        try {
            IGameLogic gameLogic = new Engine();
            GameEngine engine = new GameEngine("Engine\u2122", 400, 400, gameLogic);
            engine.start();
        }   catch (Exception exception){
            exception.printStackTrace();
            System.exit(-1);
        }
    }
}
