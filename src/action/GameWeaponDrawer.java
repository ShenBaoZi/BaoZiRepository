package action;

import java.awt.Graphics;
import java.awt.Image;

import core.IDrawAction;

/**
 * ÎäÆ÷
 * 
 * @author BaoZi
 * 
 */
public class GameWeaponDrawer implements IDrawAction {

	@Override
	public void draw(Graphics g, Image image, int x, int y) {
		g.drawImage(image, x, y, null);
	}

	@Override
	public void draw(Graphics g, Image img, int dx1, int dy1, int dx2, int dy2,
			int sx1, int sy1, int sx2, int sy2) {

	}

}
