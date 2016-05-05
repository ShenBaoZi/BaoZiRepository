package core;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

/**
 * ”Œœ∑ª˘¿‡
 * 
 * @author BaoZi
 * 
 */
public abstract class GameObject {
	public int id;
	public int x, y;
	public Image image;
	public IMoveAction mover;
	public IDrawAction drawer;

	public GameObject(int id, Image image, int x, int y) {
		this.id = id;
		this.image = image;
		this.x = x;
		this.y = y;
	}

	public GameObject(int id, Image image, int x, int y, IDrawAction drawer) {
		this(id, image, x, y);
		this.drawer = drawer;
	}

	public GameObject(int id, Image image, int x, int y, IMoveAction mover,
			IDrawAction drawer) {
		this(id, image, x, y, drawer);
		this.mover = mover;
	}

	public void tick(Graphics g) {

	}

	public void move(Graphics g) {
		mover.move(g, image, x, y);
	}

	public void draw(Graphics g) {
		drawer.draw(g, image, x, y);
	}

	public void keyPressed(KeyEvent event, Graphics g) {

	}

	public void destroy() {

	}
}
