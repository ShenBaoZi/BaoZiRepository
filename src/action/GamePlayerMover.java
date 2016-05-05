package action;

import java.awt.Graphics;
import java.awt.Image;

import core.IMoveAction;

/**
 * Íæ¼ÒÒÆ¶¯
 * 
 * @author BaoZi
 * 
 */
public class GamePlayerMover implements IMoveAction {

	@Override
	public void move(Graphics g, Image image, int x, int y) {

	}

	@Override
	public void move(Graphics g, Image img, int dx1, int dy1, int dx2, int dy2,
			int sx1, int sy1, int sx2, int sy2) {
		g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);

	}

}
