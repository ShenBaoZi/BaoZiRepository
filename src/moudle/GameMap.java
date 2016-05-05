package moudle;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import util.Util;
import core.GameObject;
import core.IDrawAction;

/**
 * 游戏地图
 * 
 * @author BaoZi
 * 
 */
public class GameMap extends GameObject {
	private int row;
	private int col;
	private int imageSize;
	private int[][] map;
	private int stage;
	/**
	 * 掉落道具
	 */
	private List<GameItem> items = new ArrayList<GameItem>();
	private Image img_floor;
	private Image img_sward;
	private Image img_block;

	/**
	 * @param row
	 * @param col
	 * @param imageSize
	 * @param map
	 * @param g
	 * @param img_floor
	 * @param img_sward
	 * @param img_block
	 * @param drawAction
	 */
	public GameMap(int stage, int x, int y, int row, int col, int imageSize,
			int[][] map, Image img_floor, Image img_sward, Image img_block,
			IDrawAction drawAction) {
		super(GameWorld.getInstance().getId(), img_floor, x, y, drawAction);
		this.stage = stage;
		this.row = row;
		this.col = col;
		this.imageSize = imageSize;
		this.map = map;
		this.img_floor = img_floor;
		this.img_sward = img_sward;
		this.img_block = img_block;
		initDropItem();
	}

	public boolean crossTheBorder(int x, int y) {
		if (x >= GameMission.row || y >= GameMission.col) {
			return true;
		}
		if (x < 0 || y < 0) {
			return true;
		}
		return false;
	}

	public boolean isAllowMapMove(int x, int y) {
		if (crossTheBorder(x, y)) {
			return false;
		}
		if (map[x][y] == 0) {
			return true;
		}
		return false;
	}

	public boolean isAllowBoom(int x, int y) {
		if (crossTheBorder(x, y)) {
			return false;
		}
		if (map[x][y] != 2) {
			return true;
		}
		return false;
	}

	public void setMapElementToSward(int x, int y) {
		map[x][y] = 0;
	}

	/**
	 * 判断是否可摧毁
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isRock(int x, int y) {
		if (map[x][y] == 1) {
			return true;
		}
		return false;
	}

	/**
	 * 获取掉落物品信息
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public GameItem getDropItem(int x, int y) {
		Iterator<GameItem> iterator = this.items.iterator();
		while (iterator.hasNext()) {
			GameItem item = iterator.next();
			if (item.x == x && item.y == y)
				return item;
		}
		return null;
	}

	/**
	 * 创建掉落物品
	 */
	public void initDropItem() {
		items.add(new GameItem(GameWorld.getInstance().getId(), Util
				.getInstance().createImage("item_bom.png"), 8, 3,
				ItemType.ITEM_BOMB));
		items.add(new GameItem(GameWorld.getInstance().getId(), Util
				.getInstance().createImage("item_gun.png"), 8, 4,
				ItemType.ITEM_GUN));
	}

	@Override
	public void draw(Graphics g) {
		for (int row = 0; row < map.length; row++) {
			for (int col = 0; col < map[row].length; col++) {
				switch (map[row][col]) {
				case 0:
					drawer.draw(g, img_floor, row * imageSize, col * imageSize);
					break;
				case 1:
					drawer.draw(g, img_sward, row * imageSize, col * imageSize);
					break;
				case 2:
					drawer.draw(g, img_block, row * imageSize, col * imageSize);
					break;
				default:
					System.out.println("[" + map[row][col] + "]"
							+ "not found such map type");
					break;
				}
			}
		}

		Iterator<GameItem> iterator = this.items.iterator();

		while (iterator.hasNext()) {
			GameItem item = iterator.next();
			item.draw(g);
		}
	}

}
