package moudle;

import java.awt.Graphics;
import java.awt.Image;

import music.MusicManager;
import music.MusicType;
import scene.SceneManager;
import util.Common;
import util.Util;
import action.GameEnemyDrawer;
import action.GameEnemyMover;
import core.GameObject;

/**
 * µ–»À¿‡
 * 
 * @author BaoZi
 * 
 */
public class GameEnemy extends GameObject {
	private static final int DIR_LEFT = 0;
	private static final int DIR_RIGHT = 1;
	private static final int DIR_UP = 2;
	private static final int DIR_DOWN = 3;
	private static final int CREATE = 0;
	private static final int RUN = 1;
	private static final int BOOM = 2;
	private static final int DEAD = 3;
	private int status;
	private Image boomImage;
	private final int imageSize = 48;
	private int direction;

	/**
	 * @param g
	 * @param image
	 * @param x
	 * @param y
	 * @param mover
	 * @param drawer
	 */
	public GameEnemy(int x, int y) {
		super(GameWorld.getInstance().getId(), Util.getInstance().createImage(
				"foemans1.png"), x, y, new GameEnemyMover(), new GameEnemyDrawer());
		boomImage = Util.getInstance().createImage("boom.png");
		this.status = CREATE;
		direction = (int) (Math.random() * 4);
	}

	public void setStateRun() {
		this.status = RUN;
	}
	
	public void setStateDead() {
		this.status = DEAD;
	}

	public boolean isStateDaed() {
		return this.status == DEAD;
	}

	public void setStateBOOM() {
		this.status = BOOM;
	}

	public boolean isStateBoom() {
		return this.status == BOOM;
	}

	@Override
	public void move(Graphics g) {
		this.status = RUN;
		switch (direction) {
		case DIR_LEFT:
			if (SceneManager.getInstance().getGamemission().getCurrentMap()
					.isAllowMapMove(x - 1, y)
					&& SceneManager.getInstance().getPlayer()
							.isAllowMoveWhenHaveBoom(x - 1, y))
				x--;
			break;
		case DIR_RIGHT:
			if (SceneManager.getInstance().getGamemission().getCurrentMap()
					.isAllowMapMove(x + 1, y)
					&& SceneManager.getInstance().getPlayer()
							.isAllowMoveWhenHaveBoom(x + 1, y))
				x++;
			break;
		case DIR_UP:
			if (SceneManager.getInstance().getGamemission().getCurrentMap()
					.isAllowMapMove(x, y - 1)
					&& SceneManager.getInstance().getPlayer()
							.isAllowMoveWhenHaveBoom(x, y - 1))
				y--;
			break;
		case DIR_DOWN:
			if (SceneManager.getInstance().getGamemission().getCurrentMap()
					.isAllowMapMove(x, y + 1)
					&& SceneManager.getInstance().getPlayer()
							.isAllowMoveWhenHaveBoom(x, y + 1))
				y++;
			break;
		default:
			break;
		}

		draw(g);
	}

	@Override
	public void draw(Graphics g) {
		if (this.status == CREATE || this.status == RUN) {
			drawer.draw(g, image, x * Common.IMAGE_SIZE, y * Common.IMAGE_SIZE);
		} else if (this.status == BOOM) {
			drawer.draw(g, boomImage, x * Common.IMAGE_SIZE, y
					* Common.IMAGE_SIZE);
			setStateDead();
			MusicManager.getInstance().playerMusic(MusicType.ENEMY_BOOM);
		}
	}

	@Override
	public void tick(Graphics g) {
		direction = (int) (Math.random() * 4);
		move(g);
	}

}
