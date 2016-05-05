package core;

import moudle.GameWorld;

/**
 * 
 * @author BaoZi
 * 
 */
public class GameRunnabel implements Runnable {
	
	private GameWorld gameWorld;

	public GameRunnabel(GameWorld world) {
		this.gameWorld = world;
	}

	@Override
	public void run() {
		while (true) {
			try {
				gameWorld.start();
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
