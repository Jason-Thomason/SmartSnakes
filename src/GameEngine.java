import java.awt.*;
import java.awt.image.VolatileImage;

public class GameEngine implements Runnable {

	World world;
	Window window = new Window(500, 500);

	VolatileImage image;
	Graphics g;

	public static int timer = 0;
	public static boolean GAME_IS_RUNNING;

	private int loops;
	private static int FPS = 30, MAX_FRAME_SKIP = 2, msPerUpdate = 1000 / FPS;
	private long CURRENT_TIME = System.currentTimeMillis();

	static GameEngine engine;

	private static int POPULATIONS = 5, POPULATIONSIZE = 500;

	public static void main(String[] args) {
		engine = new GameEngine();
	}

	public GameEngine() {
		newGame();
	}

	public void update() {
		timer++;
		world.update();
	}

	public void render(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, Window.width, Window.height);
		world.render(g);

		g = window.getGraphics();
		g.drawImage(image, 0, 0, null);
	}

	public void run() {
		System.out.println("Run Start");
		image = window.createVolatileImage(Window.width, Window.height);
		g = image.getGraphics();
		GAME_IS_RUNNING = true;

		try {
			while (GAME_IS_RUNNING) {

				loops = 0;
				// Calculate data as long as we are behind schedule and we
				// havn't skipped display too much
				while (System.currentTimeMillis() > CURRENT_TIME
						&& loops < MAX_FRAME_SKIP) {
					update();

					CURRENT_TIME += msPerUpdate;
					loops++;
				}
				render(g);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void newGame() {
		world = new World(POPULATIONSIZE);
		Thread t1 = new Thread(this);
		t1.start();
	}
}
