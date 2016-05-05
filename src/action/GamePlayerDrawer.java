package action;

import java.awt.Graphics;
import java.awt.Image;

import core.IDrawAction;

/**
 * Íæ¼Ò
 * 
 * @author BaoZi
 * 
 */
public class GamePlayerDrawer implements IDrawAction {

	@Override
	public void draw(Graphics g, Image image, int x, int y) {
		g.drawImage(image, x, y, null);
	}

	@Override
	public void draw(Graphics g, Image img, int dx1, int dy1, int dx2, int dy2,
			int sx1, int sy1, int sx2, int sy2) {
		g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
	}

}
