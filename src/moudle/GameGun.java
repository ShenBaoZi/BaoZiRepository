package moudle;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;

import music.MusicManager;
import music.MusicType;
import scene.SceneManager;
import util.Common;
import util.Util;
import action.GameWeaponDrawer;
import core.IDrawAction;

/**
 * 枪
 * 
 * @author BaoZi
 * 
 */
public class GameGun extends GameWeapon {
	private int scope = 2;
	private long setTime;
	private Image boomImage;
	private ArrayList<Integer[]> boomScope;
	private int status;
	private final int CREATE = 0;
	private final int SET = 1;
	private final int BOOM = 2;
	private final int DEAD = 3;

	/**
	 * @param g
	 * @param image
	 * @param x
	 * @param y
	 * @param drawer
	 */
	public GameGun(int x, int y, IDrawAction drawer) {
		super(GameWorld.getInstance().getId(), Util.getInstance().createImage(
				"bomb.png"), x, y, new GameWeaponDrawer());
		boomScope = new ArrayList<Integer[]>();
		boomImage = Util.getInstance().createImage("bullet.png");
		status = CREATE;
	}
	
	public void setBomb(long time, int x, int y) {
		this.x = x;
		this.y = y;
		setTime = time;
		this.status = SET;
	}

	public void clearBoomScope() {
		boomScope.clear();
	}

	/**
	 * @return scope
	 */
	public int getScope() {
		return scope;
	}

	public void addScopeLevel() {
		this.scope++;
	}

	@Override
	public void draw(Graphics g) {
		if (this.status == SET) {
			
		} else if (this.status == BOOM) {
			clearBoomScope();

			// 分别画上下左右四个方向
			for (int index = 1; index <= scope; index++) {
				if (SceneManager.getInstance().getGamemission().getCurrentMap()
						.isAllowBoom(x + index, y)) {
					drawer.draw(g, boomImage, (x + index) * Common.IMAGE_SIZE,
							y * Common.IMAGE_SIZE);

					boomScope.add(new Integer[] { x + index, y });

					if (SceneManager.getInstance().getGamemission()
							.getCurrentMap().isRock(x + index, y))
						break;
				} else {
					break;
				}

			}

			for (int index = 1; index <= scope; index++) {
				if (SceneManager.getInstance().getGamemission().getCurrentMap()
						.isAllowBoom(x - index, y)) {
					drawer.draw(g, boomImage, (x - index) * Common.IMAGE_SIZE,
							y * Common.IMAGE_SIZE);
					boomScope.add(new Integer[] { x - index, y });

					if (SceneManager.getInstance().getGamemission()
							.getCurrentMap().isRock(x - index, y))
						break;
				} else {
					break;
				}
			}

			for (int index = 1; index <= scope; index++) {
				if (SceneManager.getInstance().getGamemission().getCurrentMap()
						.isAllowBoom(x, y + index)) {
					drawer.draw(g, boomImage, x * Common.IMAGE_SIZE,
							(y + index) * Common.IMAGE_SIZE);
					boomScope.add(new Integer[] { x, y + index });
					if (SceneManager.getInstance().getGamemission()
							.getCurrentMap().isRock(x, y + index))
						break;
				} else {
					break;
				}
			}

			for (int index = 1; index <= scope; index++) {
				if (SceneManager.getInstance().getGamemission().getCurrentMap()
						.isAllowBoom(x, y - index)) {
					drawer.draw(g, boomImage, x * Common.IMAGE_SIZE,
							(y - index) * Common.IMAGE_SIZE);
					boomScope.add(new Integer[] { x, y - index });
					if (SceneManager.getInstance().getGamemission()
							.getCurrentMap().isRock(x, y - index))
						break;
				} else {
					break;
				}
			}

			// 更新状态
			this.status = DEAD;
		}
	}

	public boolean isBobmSetStatus() {
		return this.status == SET;
	}

	public boolean isBombCreateStatus() {
		return this.status == CREATE;
	}

	public boolean isBombDeadState() {
		return this.status == DEAD;
	}

	public boolean isAllowMove(int x, int y) {
		if (x == this.x && this.y == y)
			return true;
		return false;
	}

	@Override
	public void tick(Graphics g) {
		if (this.status == DEAD) {
			int tempX;
			int tempY;
			GamePlayer player = SceneManager.getInstance().getPlayer();
			GameMap currentMap = SceneManager.getInstance().getGamemission()
					.getCurrentMap();

			for (Iterator<Integer[]> it = this.boomScope.iterator(); it
					.hasNext();) {
				// 与玩家的碰撞检测
				Integer[] next = it.next();
				tempX = next[0];
				tempY = next[1];
				if (!player.isStateDaed() && tempX == player.x
						&& tempY == player.y) {
					player.setStateBOOM();
					player.draw(g);
				}
				// 与地图的碰撞检测
				if (currentMap.isRock(tempX, tempY)) {
					GameItem dropItem = currentMap.getDropItem(tempX, tempY);
					// 这个位置是否有物品
					if (dropItem != null) {
						dropItem.born();
					}
					currentMap.setMapElementToSward(tempX, tempY);
				}
				// 与怪物的碰撞检测
				for (Iterator<GameEnemy> itge = SceneManager.getInstance()
						.getGamemission().getCurrentEnems(); itge.hasNext();) {
					GameEnemy enemy = itge.next();
					if (!enemy.isStateDaed() && enemy.x == tempX
							&& enemy.y == tempY) {
						enemy.setStateBOOM();
						enemy.draw(g);
					}
				}

			}

			this.status = CREATE;
		}

		if (this.status == SET
				&& GameWorld.getInstance().getGameTime() - this.setTime >= Common.GUN_LEFT) {
			this.status = BOOM;
			MusicManager.getInstance().playerMusic(MusicType.BOOM);
		}
	}

}
