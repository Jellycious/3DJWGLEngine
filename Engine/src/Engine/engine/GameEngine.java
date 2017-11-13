package Shitenka.engine;

import Shitenka.engine.util.Timer;
import Shitenka.engine.rendering.Window;

public class GameEngine implements Runnable{

    public static final int TARGET_FPS = 144;
    public static final int TARGET_UPS = 30;

    private final Thread gameLoopThread;
    private final Window window;
    private final IGameLogic gamelogic;
    private final Timer timer;


    public GameEngine(String windowTitle, int width, int height, IGameLogic gamelogic) {
        gameLoopThread = new Thread(this, "GAME_LOOP_THREAD");
        window = new Window(windowTitle, width, height, false);
        this.gamelogic = gamelogic;
        timer = new Timer();
    }

    public void run(){
        try {
            init();
            gameLoop();
        } catch (Exception exception){
            exception.printStackTrace();
        } finally {
            cleanup();
        }
    }

    public void start(){
        String osName = System.getProperty("os.name");
        if ( osName.contains("Mac") ) {
            gameLoopThread.run();
        } else {
            gameLoopThread.start();
        }
    }

    protected void init() throws Exception{
        window.init();
        timer.init();
        gamelogic.init(window);
    }

    protected void input() {
        gamelogic.input(window);
    }

    protected void update(float interval){
        gamelogic.update(interval);
    }

    protected void render() {
        gamelogic.render(window);
        window.update();
    }

    public void cleanup(){
        gamelogic.cleanup();
    }


    protected void gameLoop() {
        float elapsedTime;
        float accumulator = 0f;
        float interval = 1f / TARGET_UPS;
        boolean running = true;
        while (running && !window.windowShouldClose()){
            elapsedTime = timer.getElapsedTime();
            accumulator += elapsedTime;
            input();

            while (accumulator >= interval) {
                update(interval);
                accumulator -= interval;
            }
            render();
            timer.getElapsedTime();
            if (!window.isvSync()){
                sync();
            }
        }
    }

    private void sync(){
        float loopSlot = 1f / TARGET_FPS;
        double endTime = timer.getLastLoopTime() + loopSlot;
        while (timer.getTime() < endTime) {
            try {
                Thread.sleep(1);
            }catch (InterruptedException ie){
            }
        }
    }

}
