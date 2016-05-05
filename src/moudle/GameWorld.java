package moudle;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicInteger;

import music.MusicManager;
import scene.GameStartScene;
import scene.SceneManager;
import core.GameRunnabel;

/**
 * ”Œœ∑ ¿ΩÁ
 * 
 * @author BaoZi
 * 
 */
public class GameWorld extends Canvas implements KeyListener {

	private static final long serialVersionUID = 1;
	private static AtomicInteger id = new AtomicInteger(0);
	private static GameWorld instance;
	private final int WIDTH = 720;
	private final int HEIGHT = 748;
	private long gameTime;
	private long startTime;
	private BufferedImage screen;
	private Graphics g;
	private Thread gameThread;

	private GameWorld() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		addKeyListener(this);
		startTime = System.currentTimeMillis();
		screen = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		g = screen.getGraphics();
		gameThread = new Thread(new GameRunnabel(this));
		gameThread.start();
		MusicManager.getInstance().run();
	}

	/**
	 * 
	 * @return GameWorld instance
	 */
	public static GameWorld getInstance() {
		if (instance == null) {
			instance = new GameWorld();
		}
		return instance;
	}

	public void start() {
		gameTime = System.currentTimeMillis();
		SceneManager.getInstance().tick(g);
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(screen, 0, 0, this);
		g.dispose();
	}

	@Override
	public void update(Graphics g) {
		paint(g);
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		GameStartScene starScene = SceneManager.getInstance().getStarScene();
		GamePlayer player = SceneManager.getInstance().getPlayer();

		if (player != null && !player.isStateDaed()) {
			player.keyPressed(e, g);
		}

		if (starScene != null) {
			starScene.keyPressed(e, g);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	/**
	 * 
	 * @return gameTime
	 */
	public long getGameTime() {
		return gameTime;
	}

	/**
	 * 
	 * @return id
	 */
	public int getId() {
		return id.incrementAndGet();
	}

}
