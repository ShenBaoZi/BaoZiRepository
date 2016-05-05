package moudle;

import java.awt.Graphics;
import java.awt.Image;

import util.Common;
import core.GameObject;

/**
 * сно╥╣ю╬ъ
 * 
 * @author BaoZi
 * 
 */
public class GameItem extends GameObject {
	private ItemType type;
	private static final int HIDDEN = 0;
	private static final int SHOW = 1;
	private static final int USED = 2;
	private int status;

	/**
	 * @param id
	 * @param image
	 * @param x
	 * @param y
	 */
	public GameItem(int id, Image image, int x, int y, ItemType type) {
		super(id, image, x, y);
		this.type = type;
		status = HIDDEN;
	}

	public void used() {
		this.status = USED;
	}

	public void born() {
		this.status = SHOW;
	}
	
	public boolean isUsed(){
		return this.status == USED;
	}

	/**
	 * @return type
	 */
	public ItemType getType() {
		return type;
	}

	@Override
	public void draw(Graphics g) {
		if (status == SHOW)
			g.drawImage(image, x * Common.IMAGE_SIZE, y * Common.IMAGE_SIZE,
					null);
	}

}
