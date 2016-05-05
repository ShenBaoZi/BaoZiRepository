package moudle;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import music.MusicManager;
import music.MusicType;
import scene.SceneManager;
import util.Common;
import util.Util;
import action.GamePlayerDrawer;
import action.GamePlayerMover;
import action.GameWeaponDrawer;
import core.GameObject;

/**
 * 玩家类
 * 
 * @author BaoZi
 * 
 */
public class GamePlayer extends GameObject {

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
	private int direction;
	private int walkCount;
	private int speed = 0;
	private long lastSetTime;
	private List<GameWeapon> weapons;
	private List<GameWeapon> boomBomb;

	public GamePlayer(int x, int y) {
		super(GameWorld.getInstance().getId(), Util.getInstance().createImage(
				"role.png"), x, y, new GamePlayerMover(),
				new GamePlayerDrawer());
		boomImage = Util.getInstance().createImage("play_o.png");
		direction = DIR_DOWN;
		this.status = CREATE;
		walkCount = 0;
		weapons = new ArrayList<GameWeapon>();
		boomBomb = new ArrayList<GameWeapon>();
		// 默认设置玩家武器
		weapons.add(new GameFist(x, y, new GameWeaponDrawer()));
	}

	public void setBomb(long gameTime, int x, int y) {
		for (GameWeapon gw : weapons) {
			if (gw != null) {
				if (gw.isBombCreateStatus()
						&& gameTime - lastSetTime > Common.THREAD_SLEEP_TIME) {
					MusicManager.getInstance().playerMusic(MusicType.SET_BOMB);
					gw.setBomb(gameTime, x, y);
					boomBomb.add(gw);
					lastSetTime = gameTime;
					break;
				}
			}
		}
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

	public boolean isAllowMoveWhenHaveBoom(int x, int y) {
		for (GameWeapon weapon : this.boomBomb) {
			if (weapon != null && weapon.isBobmSetStatus()) {
				if (weapon.isAllowMove(x, y))
					return false;
			}
		}
		return true;
	}

	public void useItem(GameItem item) {
		MusicManager.getInstance().playerMusic(MusicType.GET_ITEM);
		switch (item.getType()) {
		case ITEM_BOMB:
			weapons.clear();
			weapons.add(new GameBomb(x, y, new GameWeaponDrawer()));
			break;
		case ITEM_GUN:
			weapons.clear();
			weapons.add(new GameGun(x, y, new GameWeaponDrawer()));
			break;
		default:
			break;
		}
		item.used();
	}

	@Override
	public void draw(Graphics g) {
		if (this.status == BOOM) {
			drawer.draw(g, boomImage, x * Common.IMAGE_SIZE, y
					* Common.IMAGE_SIZE);
			this.status = DEAD;
		} else if (this.status == CREATE || this.status == RUN) {
			drawer.draw(g, image, x * Common.IMAGE_SIZE, y * Common.IMAGE_SIZE,
					x * Common.IMAGE_SIZE + Common.IMAGE_SIZE, y
							* Common.IMAGE_SIZE + Common.IMAGE_SIZE, walkCount
							* Common.IMAGE_SIZE, direction * Common.IMAGE_SIZE,
					walkCount * Common.IMAGE_SIZE + Common.IMAGE_SIZE,
					direction * Common.IMAGE_SIZE + Common.IMAGE_SIZE);
		}

		if (this.status != DEAD) {
			for (GameWeapon gw : boomBomb) {
				if (gw != null) {
					gw.draw(g);
				}
			}
		}
	}

	@Override
	public void move(Graphics g) {
		this.status = RUN;
		MusicManager.getInstance().playerMusic(MusicType.MOVE);
		switch (direction) {
		case DIR_LEFT:
			if (SceneManager.getInstance().getGamemission().getCurrentMap()
					.isAllowMapMove(x - 1, y)
					&& isAllowMoveWhenHaveBoom(x - 1, y))
				x--;
			break;
		case DIR_RIGHT:
			if (SceneManager.getInstance().getGamemission().getCurrentMap()
					.isAllowMapMove(x + 1, y)
					&& isAllowMoveWhenHaveBoom(x + 1, y))
				x++;
			break;
		case DIR_UP:
			if (SceneManager.getInstance().getGamemission().getCurrentMap()
					.isAllowMapMove(x, y - 1)
					&& isAllowMoveWhenHaveBoom(x, y - 1))
				y--;
			break;
		case DIR_DOWN:
			if (SceneManager.getInstance().getGamemission().getCurrentMap()
					.isAllowMapMove(x, y + 1)
					&& isAllowMoveWhenHaveBoom(x, y + 1))
				y++;
			break;
		default:
			break;

		}

		// 使用物品
		GameItem dropItem = SceneManager.getInstance().getGamemission()
				.getCurrentMap().getDropItem(x, y);
		if (dropItem != null && !dropItem.isUsed()) {
			this.useItem(dropItem);
		}

		mover.move(g, image, x * Common.IMAGE_SIZE, y * Common.IMAGE_SIZE, x
				* Common.IMAGE_SIZE + Common.IMAGE_SIZE, y * Common.IMAGE_SIZE
				+ Common.IMAGE_SIZE, walkCount * Common.IMAGE_SIZE, direction
				* Common.IMAGE_SIZE, walkCount * Common.IMAGE_SIZE
				+ Common.IMAGE_SIZE, direction * Common.IMAGE_SIZE
				+ Common.IMAGE_SIZE);
	}

	@Override
	public void keyPressed(KeyEvent event, Graphics g) {

		if (event.getKeyCode() == KeyEvent.VK_LEFT) {
			direction = DIR_LEFT;
			move(g);
		} else if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
			direction = DIR_RIGHT;
			move(g);
		} else if (event.getKeyCode() == KeyEvent.VK_UP) {
			direction = DIR_UP;
			move(g);
		} else if (event.getKeyCode() == KeyEvent.VK_DOWN) {
			direction = DIR_DOWN;
			move(g);
		} else if (event.getKeyCode() == KeyEvent.VK_SPACE) {
			setBomb(GameWorld.getInstance().getGameTime(), x, y);
		}
	}

	@Override
	public void tick(Graphics g) {

		if (this.isStateDaed()) {
			SceneManager.getInstance().setSceneStep(
					SceneManager.SCENE_GAME_OVER);
			return;
		}

		boolean enemyDeadAll = SceneManager.getInstance().getGamemission()
				.isEnemyDeadAll();
		if (enemyDeadAll) {
			SceneManager.getInstance().setSceneStep(SceneManager.SCENE_NEXT_STAGE);
		}

		// 先画怪物
		for (Iterator<GameEnemy> currentEnems = SceneManager.getInstance()
				.getGamemission().getCurrentEnems(); currentEnems.hasNext();) {
			GameEnemy ge = currentEnems.next();
			if (ge.isStateDaed() || ge.isStateBoom())
				continue;
			ge.tick(g);
		}

		if (walkCount == 0) {
			walkCount = 1;
		} else if (walkCount == 1) {
			walkCount = 0;
		}

		// 画玩家
		draw(g);

		// 画炸弹
		for (GameWeapon gw : boomBomb) {
			if (gw != null)
				if (gw.isBobmSetStatus() || gw.isBombDeadState())
					gw.draw(g);
		}

		// 玩家与怪物的碰撞
		for (Iterator<GameEnemy> it = SceneManager.getInstance()
				.getGamemission().getCurrentEnems(); it.hasNext();) {
			GameEnemy e = it.next();
			if (!e.isStateDaed() && e.x == this.x && e.y == this.y) {
				this.setStateDead();
				// 重画玩家
				this.draw(g);
			}
		}

		// 地图 ，玩家，怪物与炸弹的碰撞
		for (GameWeapon gw : boomBomb) {
			if (gw != null) {
				if (gw.isBobmSetStatus() || gw.isBombDeadState()) {
					gw.tick(g);
				}
			}
		}
	}

}
